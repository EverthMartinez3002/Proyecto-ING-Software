package org.luismore.hlvsapi.services.impls;

import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.CreateSingleRequestDTO;
import org.luismore.hlvsapi.domain.dtos.CreateMultipleRequestDTO;
import org.luismore.hlvsapi.domain.dtos.RequestDTO;
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
        String stateId = getStateIdBasedOnUserRole(user);

        State state = stateRepository.findById(stateId)
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
        return requestRepository.save(request);
    }

    @Override
    @Transactional
    public List<Request> createMultipleRequests(CreateMultipleRequestDTO createRequestDTO, User user) {
        LimitTime limitTime = limitTimeRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("Invalid limit time id"));
        String stateId = getStateIdBasedOnUserRole(user);

        State state = stateRepository.findById(stateId)
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
}
