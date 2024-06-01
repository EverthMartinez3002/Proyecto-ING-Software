package org.luismore.hlvs.controllers;

import org.luismore.hlvs.services.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    @Autowired
    private QrService qrService;

    @GetMapping("/token/{userId}")
    public ResponseEntity<GeneralResponse> getTokenByUserId(@PathVariable Long userId) {
        Token token = qrService.getTokenByUserId(userId);
        return GeneralResponse.getResponse(token, "Token fetched successfully");
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneralResponse> generateToken(@RequestBody Long requestId) {
        Token token = qrService.generateToken(requestId);
        return GeneralResponse.getResponse(token, "Token generated successfully");
    }

    @PostMapping("/{qrId}/expiration")
    public ResponseEntity<GeneralResponse> updateQrExpiration(@PathVariable Long qrId, @RequestBody Long newExpirationTime) {
        Token updatedToken = qrService.updateQrExpiration(qrId, newExpirationTime);
        return GeneralResponse.getResponse(updatedToken, "QR expiration updated successfully");
    }
}
