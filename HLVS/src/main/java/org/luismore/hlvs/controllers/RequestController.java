package org.luismore.hlvs.controllers;

import org.luismore.hlvs.dtos.GeneralResponse;
import org.luismore.hlvs.entities.Request;
import org.luismore.hlvs.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/home/{homeId}/pending")
    public ResponseEntity<GeneralResponse> getPendingRequests(@PathVariable Long homeId) {
        List<Request> requests = requestService.getPendingRequestsByHomeId(homeId);
        return GeneralResponse.getResponse(requests, "Pending requests fetched successfully");
    }

    @PostMapping("/")
    public ResponseEntity<GeneralResponse> createRequest(@RequestBody Request request) {
        Request createdRequest = requestService.createRequest(request);
        return GeneralResponse.getResponse(createdRequest, "Request created successfully");
    }

    @GetMapping("/history")
    public ResponseEntity<GeneralResponse> getRequestHistory() {
        List<Request> requestHistory = requestService.getRequestHistory();
        return GeneralResponse.getResponse(requestHistory, "Request history fetched successfully");
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<GeneralResponse> updateRequest(@PathVariable Long requestId, @RequestBody Request request) {
        Request updatedRequest = requestService.updateRequest(requestId, request);
        return GeneralResponse.getResponse(updatedRequest, "Request updated successfully");
    }
}
