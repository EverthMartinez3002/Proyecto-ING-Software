package org.luismore.controllers;

import jakarta.validation.Valid;
import org.luismore.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    private final QrService qrService;
    private final UserService userService;

    public QrController(QrService qrService, UserService userService) {
        this.qrService = qrService;
        this.userService = userService;
    }

    @GetMapping("/token/{userId}")
    public ResponseEntity<GeneralResponse> getToken(@PathVariable int userId) {
        String token = qrService.getTokenByUserId(userId);
        if (token == null) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Token not found");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, token);
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneralResponse> generateToken(@RequestBody @Valid QrDTO qrDTO) {
        UUID token = qrService.generateToken(qrDTO.getRequestId());
        return GeneralResponse.getResponse(HttpStatus.CREATED, token.toString());
    }
}
