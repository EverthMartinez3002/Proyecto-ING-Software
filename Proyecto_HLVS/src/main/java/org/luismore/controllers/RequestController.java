package org.luismore.controllers;



import org.luismore.services.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;

    public RequestController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping("/byHome/{homeId}")
    public ResponseEntity<GeneralResponse> getRequestListByHomeId(@PathVariable int homeId) {
        List<Request> requests = requestService.findPendingRequestsByHomeId(homeId);
        if (requests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "No pending requests found");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, requests);
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createRequest(@RequestBody @Valid RequestDTO requestDTO) {
        User user = userService.findById(requestDTO.getUserId());
        if (user == null) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "User not found");
        }
        boolean isAdminOrMainResident = userService.isAdminOrMainResident(user);
        Request request = requestService.createRequest(requestDTO, isAdminOrMainResident);
        return GeneralResponse.getResponse(HttpStatus.CREATED, request);
    }

    @GetMapping("/history")
    public ResponseEntity<GeneralResponse> getRequestHistory() {
        List<Request> requests = requestService.findAll();
        if (requests.isEmpty()) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "No requests found");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, requests);
    }

    @PostMapping("/update")
    public ResponseEntity<GeneralResponse> updateRequest(@RequestBody @Valid UpdateRequestDTO updateRequestDTO, @RequestHeader("Authorization") String authHeader) {
        User user = userService.findByToken(authHeader);
        if (user == null || !userService.isAdminOrMainResident(user)) {
            return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Request request = requestService.updateRequestStatus(updateRequestDTO);
        return GeneralResponse.getResponse(HttpStatus.OK, request);
    }
}
