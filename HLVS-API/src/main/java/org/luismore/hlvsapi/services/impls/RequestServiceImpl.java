package org.luismore.hlvsapi.services.impls;

import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.LimitTime;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.State;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.LimitTimeRepository;
import org.luismore.hlvsapi.repositories.RequestRepository;
import org.luismore.hlvsapi.repositories.StateRepository;
import org.luismore.hlvsapi.repositories.UserRepository;
import org.luismore.hlvsapi.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;
    private final LimitTimeRepository limitTimeRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, StateRepository stateRepository, UserRepository userRepository, LimitTimeRepository limitTimeRepository) {
        this.requestRepository = requestRepository;
        this.stateRepository = stateRepository;
        this.userRepository = userRepository;
        this.limitTimeRepository = limitTimeRepository;
    }

    @Override
    public List<Request> getRequestsByHomeIdAndStatus(UUID homeId, String status) {
        return requestRepository.findByHouseIdAndState(homeId, status);
    }

    @Override
    @Transactional
    public Request createSingleRequest(CreateSingleRequestDTO createRequestDTO, User user) {
        LimitTime limitTime = limitTimeRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("Invalid limit time id"));

        State state = stateRepository.findById(getStateIdBasedOnUserRole(user))
                .orElseThrow(() -> new IllegalArgumentException("Invalid state id"));

        User visitor = userRepository.findByDui(createRequestDTO.getDui())
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found"));

        Request request = new Request();
        request.setDUI(createRequestDTO.getDui());
        request.setEntryDate(createRequestDTO.getEntryDate());
        request.setEntryTime(createRequestDTO.getEntryTime());
        request.setBeforeTime(calculateBeforeTime(createRequestDTO.getEntryTime(), limitTime.getLimit()));
        request.setAfterTime(calculateAfterTime(createRequestDTO.getEntryTime(), limitTime.getLimit()));
        request.setLimitTime(limitTime);
        request.setState(state);
        request.setHouse(user.getHouse());
        request.setVisitor(visitor);
        request.setCreator(user);
        return requestRepository.save(request);
    }

    @Override
    @Transactional
    public List<Request> createMultipleRequests(CreateMultipleRequestDTO createRequestDTO, User user) {
        LimitTime limitTime = limitTimeRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("Invalid limit time id"));

        State state = stateRepository.findById(getStateIdBasedOnUserRole(user))
                .orElseThrow(() -> new IllegalArgumentException("Invalid state id"));

        User visitor = userRepository.findByDui(createRequestDTO.getDui())
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found"));

        return createRequestDTO.getEntryDates().stream().map(entryDate -> {
            Request request = new Request();
            request.setDUI(createRequestDTO.getDui());
            request.setEntryDate(entryDate);
            request.setHour1(createRequestDTO.getHour1());
            request.setHour2(createRequestDTO.getHour2());
            request.setBeforeTime(calculateBeforeTime(createRequestDTO.getHour1(), limitTime.getLimit()));
            request.setAfterTime(calculateAfterTime(createRequestDTO.getHour2(), limitTime.getLimit()));
            request.setLimitTime(limitTime);
            request.setState(state);
            request.setHouse(user.getHouse());
            request.setVisitor(visitor);
            request.setCreator(user);
            return requestRepository.save(request);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Request> getRequestHistoryByUserHouse(User user) {
        return requestRepository.findByHouseId(user.getHouse().getId());
    }

    @Override
    public Optional<Request> getRequestById(UUID requestId) {
        return requestRepository.findById(requestId);
    }

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

    @Override
    public void updateRequestState(Request request, String stateId) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid state id"));
        request.setState(state);
        requestRepository.save(request);
    }

    private LocalTime calculateBeforeTime(LocalTime entryTime, int limit) {
        return entryTime.minusMinutes(limit);
    }

    private LocalTime calculateAfterTime(LocalTime entryTime, int limit) {
        return entryTime.plusMinutes(limit);
    }

    public RequestDTO convertToDTO(Request request) {
        RequestDTO dto = new RequestDTO();
        dto.setId(request.getId());
        dto.setDUI(request.getDUI());
        dto.setEntryDate(request.getEntryDate());
        dto.setEntryTime(request.getEntryTime());
        dto.setBeforeTime(request.getBeforeTime());
        dto.setAfterTime(request.getAfterTime());
        dto.setHour1(request.getHour1());
        dto.setHour2(request.getHour2());
        dto.setHouseId(request.getHouse().getId().toString());
        dto.setStateId(request.getState().getId());
        dto.setVisitorId(request.getVisitor().getId().toString());
        return dto;
    }

    private String getStateIdBasedOnUserRole(User user) {
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_admin") || authority.getAuthority().equals("ROLE_main resident")) {
                return "APPR";
            }
        }
        return "PEND";
    }


    @Override
    @Transactional
    public List<PendingRequestDTO> getAllPendingRequestsForMainResident(User mainResident) {
        UUID houseId = mainResident.getHouse().getId();
        List<Request> pendingRequests = requestRepository.findByHouseIdAndState(houseId, "PEND");

        Map<User, List<Request>> groupedRequests = pendingRequests.stream()
                .collect(Collectors.groupingBy(Request::getCreator));

        List<PendingRequestDTO> dtos = new ArrayList<>();

        for (Map.Entry<User, List<Request>> entry : groupedRequests.entrySet()) {
            User creator = entry.getKey();
            List<Request> requests = entry.getValue();

            Optional<Request> multipleRequestOpt = requests.stream()
                    .filter(req -> req.getEntryTime() == null)
                    .findFirst();

            if (multipleRequestOpt.isPresent()) {
                Request firstMultipleRequest = multipleRequestOpt.get();
                PendingRequestDTO multipleDto = new PendingRequestDTO();
                multipleDto.setResidentName(creator.getName());
                multipleDto.setRequestDay(firstMultipleRequest.getEntryDate());
                multipleDto.setVisitorName(firstMultipleRequest.getVisitor().getName());
                multipleDto.setReq("Multiple");
                dtos.add(multipleDto);
            }

            requests.stream()
                    .filter(req -> req.getEntryTime() != null)
                    .forEach(req -> {
                        PendingRequestDTO singleRequestDto = new PendingRequestDTO();
                        singleRequestDto.setResidentName(creator.getName());
                        singleRequestDto.setRequestDay(req.getEntryDate());
                        singleRequestDto.setVisitorName(req.getVisitor().getName());
                        singleRequestDto.setReq(null);
                        dtos.add(singleRequestDto);
                    });
        }

        return dtos;
    }


    @Override
    public RequestDetailsDTO getSingleRequestDetails(UUID requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        RequestDetailsDTO dto = new RequestDetailsDTO();
        dto.setResidentEmail(request.getCreator().getEmail());
        dto.setVisitorEmail(request.getVisitor().getEmail());
        dto.setDUI(request.getDUI());
        dto.setEntryDate(request.getEntryDate());
        dto.setEntryTime(request.getEntryTime());

        return dto;
    }

    @Override
    public List<RequestDTO> getMultipleRequestsByResidentAndVisitor(String residentName, String visitorName) {
        List<Request> requests = requestRepository.findMultipleRequestsByResidentAndVisitor(residentName, visitorName, "PEND");
        return requests.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Request> getRequestsByResidentAndVisitorNames(String residentName, String visitorName) {
        return requestRepository.findByResidentAndVisitorNamesAndEntryTimeIsNullAndStatePending(residentName, visitorName);
    }

    @Override
    @Transactional
    public void updateRequestState(String id, String residentName, String visitorName, String status) {
        String stateId = getStateIdFromStatus(status);
        if (id.equals("multiple")) {
            updateMultipleRequestsState(residentName, visitorName, stateId);
        } else {
            Optional<Request> requestOptional = requestRepository.findById(UUID.fromString(id));
            if (requestOptional.isEmpty()) {
                throw new IllegalArgumentException("Request not found.");
            }
            Request request = requestOptional.get();
            State state = stateRepository.findById(stateId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid state id"));
            request.setState(state);
            requestRepository.save(request);
        }
    }

    @Override
    @Transactional
    public void updateMultipleRequestsState(String residentName, String visitorName, String stateId) {
        List<Request> requests = requestRepository.findByResidentAndVisitorNames(residentName, visitorName);
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid state id"));

        for (Request request : requests) {
            request.setState(state);
        }
        requestRepository.saveAll(requests);
    }

    private String getStateIdFromStatus(String status) {
        switch (status.toLowerCase()) {
            case "approved":
                return "APPR";
            case "rejected":
                return "REJE";
            case "pending":
                return "PEND";
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }




}
