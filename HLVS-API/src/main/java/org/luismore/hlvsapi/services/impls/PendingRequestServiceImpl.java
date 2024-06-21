//package org.luismore.hlvsapi.services.impls;
//
//import org.luismore.hlvsapi.domain.dtos.PendingRequestSummaryDTO;
//import org.luismore.hlvsapi.domain.entities.Request;
//import org.luismore.hlvsapi.repositories.RequestRepository;
//import org.luismore.hlvsapi.services.PendingRequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class PendingRequestServiceImpl implements PendingRequestService {
//
//    private final RequestRepository requestRepository;
//
//    @Autowired
//    public PendingRequestServiceImpl(RequestRepository requestRepository) {
//        this.requestRepository = requestRepository;
//    }
//
//    @Override
//    public List<PendingRequestSummaryDTO> getAllPendingRequestsByHouseId(UUID houseId) {
//        List<Request> uniqueRequests = requestRepository.findUniquePendingRequestsByHouseId(houseId, "PEND");
//        List<Request> multipleRequests = requestRepository.findMultiplePendingRequestsByHouseId(houseId, "PEND");
//
//        List<PendingRequestSummaryDTO> dtos = uniqueRequests.stream().map(req -> {
//            PendingRequestSummaryDTO dto = new PendingRequestSummaryDTO();
//            dto.setId(req.getId().toString());
//            dto.setEntryDate(req.getEntryDate().toString());
//            dto.setResident(req.getHouse().getLeader().getName());
//            dto.setVisitor(req.getVisitor().getName());
//            return dto;
//        }).collect(Collectors.toList());
//
//        Map<String, Long> multipleRequestCounts = multipleRequests.stream()
//                .collect(Collectors.groupingBy(req -> req.getVisitor().getEmail() + "-" + req.getHouse().getLeader().getEmail(), Collectors.counting()));
//
//        multipleRequestCounts.forEach((key, count) -> {
//            String[] emails = key.split("-");
//            PendingRequestSummaryDTO dto = new PendingRequestSummaryDTO();
//            dto.setId("multiple");
//            dto.setMultipleCount(count.intValue());
//            dto.setResident(emails[1]);
//            dto.setVisitor(emails[0]);
//            dtos.add(dto);
//        });
//
//        return dtos;
//    }
//}

package org.luismore.hlvsapi.services.impls;

import org.luismore.hlvsapi.domain.dtos.PendingRequestSummaryDTO;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.RequestRepository;
import org.luismore.hlvsapi.repositories.UserRepository;
import org.luismore.hlvsapi.services.PendingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PendingRequestServiceImpl implements PendingRequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Autowired
    public PendingRequestServiceImpl(RequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PendingRequestSummaryDTO> getAllPendingRequestsByHouseId(UUID houseId) {
        List<Request> allPendingRequests = requestRepository.findByHouseIdAndState(houseId, "PEND");

        Map<String, List<Request>> groupedRequests = allPendingRequests.stream()
                .collect(Collectors.groupingBy(req -> req.getCreator().getId().toString() + "-" + req.getVisitor().getId().toString()));

        List<PendingRequestSummaryDTO> dtos = new ArrayList<>();

        for (Map.Entry<String, List<Request>> entry : groupedRequests.entrySet()) {
            List<Request> requests = entry.getValue();

            // UNICAS
            requests.stream()
                    .filter(req -> req.getHour1() == null && req.getHour2() == null)
                    .forEach(req -> {
                        PendingRequestSummaryDTO singleDto = new PendingRequestSummaryDTO();
                        singleDto.setId(req.getId().toString());
                        singleDto.setEntryDate(req.getEntryDate());
                        singleDto.setResident(req.getCreator().getName());
                        singleDto.setVisitor(req.getVisitor().getName());
                        singleDto.setMultipleCount(null);
                        dtos.add(singleDto);
                    });

            // MULTIPLES
            long multipleCount = requests.stream().filter(req -> req.getEntryTime() == null).count();
            if (multipleCount > 0) {
                Request representativeRequest = requests.stream().filter(req -> req.getEntryTime() == null).findFirst().orElse(null);
                if (representativeRequest != null) {
                    PendingRequestSummaryDTO multipleDto = new PendingRequestSummaryDTO();
                    multipleDto.setId("multiple");
                    multipleDto.setEntryDate(null);
                    multipleDto.setResident(representativeRequest.getCreator().getName());
                    multipleDto.setVisitor(representativeRequest.getVisitor().getName());
                    multipleDto.setMultipleCount((int) multipleCount);
                    dtos.add(multipleDto);
                }
            }
        }

        return dtos;
    }

    @Override
    public List<PendingRequestSummaryDTO> getAllApprovedRequestsByHouseId(UUID houseId) {
        List<Request> allApprovedRequests = requestRepository.findByHouseIdAndState(houseId, "APPR");

        Map<String, List<Request>> groupedRequests = allApprovedRequests.stream()
                .collect(Collectors.groupingBy(req -> req.getCreator().getId().toString() + "-" + req.getVisitor().getId().toString()));

        List<PendingRequestSummaryDTO> dtos = new ArrayList<>();

        for (Map.Entry<String, List<Request>> entry : groupedRequests.entrySet()) {
            List<Request> requests = entry.getValue();

            // UNICAS
            requests.stream()
                    .filter(req -> req.getHour1() == null && req.getHour2() == null)
                    .forEach(req -> {
                        PendingRequestSummaryDTO singleDto = new PendingRequestSummaryDTO();
                        singleDto.setId(req.getId().toString());
                        singleDto.setEntryDate(req.getEntryDate());
                        singleDto.setResident(req.getCreator().getName());
                        singleDto.setVisitor(req.getVisitor().getName());
                        singleDto.setMultipleCount(null);
                        dtos.add(singleDto);
                    });

            // MULTIPLES
            long multipleCount = requests.stream().filter(req -> req.getEntryTime() == null).count();
            if (multipleCount > 0) {
                Request representativeRequest = requests.stream().filter(req -> req.getEntryTime() == null).findFirst().orElse(null);
                if (representativeRequest != null) {
                    PendingRequestSummaryDTO multipleDto = new PendingRequestSummaryDTO();
                    multipleDto.setId("multiple");
                    multipleDto.setEntryDate(null);
                    multipleDto.setResident(representativeRequest.getCreator().getName());
                    multipleDto.setVisitor(representativeRequest.getVisitor().getName());
                    multipleDto.setMultipleCount((int) multipleCount);
                    dtos.add(multipleDto);
                }
            }
        }

        return dtos;
    }


}
