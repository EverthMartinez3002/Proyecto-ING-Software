package org.luismore.hlvsapi.controllers;

import jakarta.validation.Valid;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.services.PendingRequestService;
import org.luismore.hlvsapi.services.RequestService;
import org.luismore.hlvsapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;
    private final PendingRequestService pendingRequestService;

    public RequestController(RequestService requestService, UserService userService, PendingRequestService pendingRequestService) {
        this.requestService = requestService;
        this.userService = userService;
        this.pendingRequestService = pendingRequestService;
    }

    @GetMapping("/AllPending/{houseId}")
    @PreAuthorize("hasAuthority('ROLE_main resident') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> getAllPendingRequestsByHouseId(@PathVariable UUID houseId) {
        List<PendingRequestSummaryDTO> pendingRequests = pendingRequestService.getAllPendingRequestsByHouseId(houseId);
        if (pendingRequests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "No pending requests found for the specified house.");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, pendingRequests);
    }

    @GetMapping("/home/{homeId}")
    @PreAuthorize("hasAuthority('ROLE_main resident') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> getRequestsByHomeId(@PathVariable UUID homeId) {
        List<Request> requests = requestService.getRequestsByHomeIdAndStatus(homeId, "PEND");
        if (requests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Pending requests not found for the specified home.");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, requests);
    }

    @PostMapping("/create/single")
    @PreAuthorize("hasAuthority('ROLE_main resident') or hasAuthority('ROLE_admin') or hasAuthority('ROLE_resident')")
    public ResponseEntity<GeneralResponse> createSingleRequest(@RequestBody @Valid CreateSingleRequestDTO createRequestDTO, @AuthenticationPrincipal User user) {
        Request request = requestService.createSingleRequest(createRequestDTO, user);
        RequestDTO requestDTO = requestService.convertToDTO(request);
        return GeneralResponse.getResponse(HttpStatus.CREATED, requestDTO);
    }

    @PostMapping("/create/multiple")
    @PreAuthorize("hasAuthority('ROLE_main resident') or hasAuthority('ROLE_admin') or hasAuthority('ROLE_resident')")
    public ResponseEntity<GeneralResponse> createMultipleRequests(@RequestBody @Valid CreateMultipleRequestDTO createRequestDTO, @AuthenticationPrincipal User user) {
        List<Request> requests = requestService.createMultipleRequests(createRequestDTO, user);
        List<RequestDTO> requestDTOs = requests.stream().map(requestService::convertToDTO).collect(Collectors.toList());
        return GeneralResponse.getResponse(HttpStatus.CREATED, requestDTOs);
    }

    @GetMapping("/request/{id}")
    @PreAuthorize("hasAuthority('ROLE_main resident') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> getRequestDetails(
            @PathVariable String id,
            @RequestParam String residentName,
            @RequestParam String visitorName) {

        if (id.equals("multiple")) {
            List<Request> requests = requestService.getRequestsByResidentAndVisitorNames(residentName, visitorName);
            if (requests.isEmpty()) {
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "No multiple requests found for the specified resident and visitor.");
            }
            List<RequestDetailsDTO> requestDetails = requests.stream().map(request -> {
                RequestDetailsDTO dto = new RequestDetailsDTO();
                dto.setId(request.getId().toString());
                dto.setDUI(request.getDUI());
                dto.setEntryDate(request.getEntryDate());
                dto.setEntryTime(request.getEntryTime());
                dto.setBeforeTime(request.getBeforeTime());
                dto.setAfterTime(request.getAfterTime());
                dto.setHour1(request.getHour1());
                dto.setHour2(request.getHour2());
                dto.setResident(request.getCreator().getName());
                dto.setVisitor(request.getVisitor().getName());
                return dto;
            }).collect(Collectors.toList());
            return GeneralResponse.getResponse(HttpStatus.OK, requestDetails);
        } else {
            Optional<Request> requestOptional = requestService.getRequestById(UUID.fromString(id));
            if (requestOptional.isEmpty()) {
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Request not found.");
            }
            Request request = requestOptional.get();
            RequestDetailsDTO dto = new RequestDetailsDTO();
            dto.setResidentEmail(request.getCreator().getEmail());
            dto.setVisitorEmail(request.getVisitor().getEmail());
            dto.setDUI(request.getDUI());
            dto.setEntryDate(request.getEntryDate());
            dto.setEntryTime(request.getEntryTime());
            return GeneralResponse.getResponse(HttpStatus.OK, dto);
        }
    }


    @GetMapping("/history")
    @PreAuthorize("hasAuthority('ROLE_main resident') or hasAuthority('ROLE_resident')")
    public ResponseEntity<GeneralResponse> getRequestHistory(@AuthenticationPrincipal User user) {
        List<Request> requests = requestService.getRequestHistoryByUserHouse(user);
        if (requests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Request history not found for the user's house.");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, requests);
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_main resident') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> updateRequest(@RequestBody @Valid UpdateRequestDTO updateRequestDTO) {
        Optional<Request> requestOptional = requestService.getRequestById(updateRequestDTO.getRequestId());
        if (requestOptional.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Request not found.");
        }
        Request request = requestOptional.get();
        requestService.updateRequestState(request, updateRequestDTO.getState());
        return GeneralResponse.getResponse(HttpStatus.OK, "Request updated successfully.");
    }

    @PatchMapping("/request/{id}")
    @PreAuthorize("hasAuthority('ROLE_main resident') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> updateRequestState(
            @PathVariable String id,
            @RequestParam String residentName,
            @RequestParam String visitorName) {

        try {
            requestService.updateRequestState(id, residentName, visitorName);
            return GeneralResponse.getResponse(HttpStatus.OK, "Request(s) updated to Approved successfully.");
        } catch (IllegalArgumentException e) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
