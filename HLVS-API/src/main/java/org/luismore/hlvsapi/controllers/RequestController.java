package org.luismore.hlvsapi.controllers;

import jakarta.validation.Valid;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.User;
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

    public RequestController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping("/home/{homeId}")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> getRequestsByHomeId(@PathVariable UUID homeId) {
        List<Request> requests = requestService.getRequestsByHomeIdAndStatus(homeId, "PEND");
        if (requests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Pending requests not found for the specified home.");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, requests);
    }

    @PostMapping("/create/single")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_admin') or hasAuthority('ROLE_resident')")
    public ResponseEntity<GeneralResponse> createSingleRequest(@RequestBody @Valid CreateSingleRequestDTO createRequestDTO, @AuthenticationPrincipal User user) {
        Request request = requestService.createSingleRequest(createRequestDTO, user);
        RequestDTO requestDTO = requestService.convertToDTO(request); // Utilizando el método del servicio
        return GeneralResponse.getResponse(HttpStatus.CREATED, requestDTO);
    }

    @PostMapping("/create/multiple")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_admin') or hasAuthority('ROLE_resident')")
    public ResponseEntity<GeneralResponse> createMultipleRequests(@RequestBody @Valid CreateMultipleRequestDTO createRequestDTO, @AuthenticationPrincipal User user) {
        List<Request> requests = requestService.createMultipleRequests(createRequestDTO, user);
        List<RequestDTO> requestDTOs = requests.stream().map(requestService::convertToDTO).collect(Collectors.toList()); // Utilizando el método del servicio
        return GeneralResponse.getResponse(HttpStatus.CREATED, requestDTOs);
    }

    @GetMapping("/history")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_resident')")
    public ResponseEntity<GeneralResponse> getRequestHistory(@AuthenticationPrincipal User user) {
        List<Request> requests = requestService.getRequestHistoryByUserHouse(user);
        if (requests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Request history not found for the user's house.");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, requests);
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> updateRequest(@RequestBody @Valid UpdateRequestDTO updateRequestDTO) {
        Optional<Request> requestOptional = requestService.getRequestById(updateRequestDTO.getRequestId());
        if (requestOptional.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Request not found.");
        }
        Request request = requestOptional.get();
        requestService.updateRequestState(request, updateRequestDTO.getState());
        return GeneralResponse.getResponse(HttpStatus.OK, "Request updated successfully.");
    }
}
