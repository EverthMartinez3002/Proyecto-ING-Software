package org.luismore.hlvsapi.controllers;

import jakarta.validation.Valid;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.State;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.services.RequestService;
import org.luismore.hlvsapi.services.StateService;
import org.luismore.hlvsapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;
    private final StateService stateService;

    public RequestController(RequestService requestService, UserService userService, StateService stateService) {
        this.requestService = requestService;
        this.userService = userService;
        this.stateService = stateService;
    }

    @GetMapping("/home/{homeId}")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> getRequestsByHomeId(@PathVariable UUID homeId) {
        List<Request> requests = requestService.getRequestsByHomeIdAndStatus(homeId, "PEND");
        if (requests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Pending requests Can(not) be found for the specified home.");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, requests);
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_admin') or hasAuthority('ROLE_resident')")
    public ResponseEntity<GeneralResponse> createRequest(@RequestBody @Valid CreateRequestDTO createRequestDTO, @AuthenticationPrincipal User user) {
        Request request = requestService.createRequest(createRequestDTO, user);
        return GeneralResponse.getResponse(HttpStatus.CREATED, request);
    }

    @GetMapping("/history")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_resident')")
    public ResponseEntity<GeneralResponse> getRequestHistory(@AuthenticationPrincipal User user) {
        List<Request> requests = requestService.getRequestHistoryByUserHouse(user);
        if (requests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Request history Can(not) be found for the user's house.");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, requests);
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_main') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> updateRequest(@RequestBody @Valid UpdateRequestDTO updateRequestDTO) {
        Optional<Request> requestOptional = requestService.getRequestById(updateRequestDTO.getRequestId());
        if (requestOptional.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Request Can(not) be found.");
        }
        Request request = requestOptional.get();
        State state = stateService.getStateById(updateRequestDTO.getState())
                .orElseThrow(() -> new IllegalArgumentException("Invalid state id"));
        request.setState(state);
        requestService.save(request);
        return GeneralResponse.getResponse(HttpStatus.OK, "Request updated successfully.");
    }
}
