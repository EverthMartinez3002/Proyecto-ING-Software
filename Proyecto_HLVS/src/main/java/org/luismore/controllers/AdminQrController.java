package org.luismore.controllers;

import org.luismore.services.QrService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/qr")
public class AdminQrController {

    private final QrService qrService;

    public AdminQrController(QrService qrService) {
        this.qrService = qrService;
    }

    @PostMapping("/updateExpiration")
    public ResponseEntity<GeneralResponse> updateQrExpiration(@RequestBody @Valid UpdateQrDTO updateQrDTO) {
        qrService.updateQrExpiration(updateQrDTO);
        return GeneralResponse.getResponse(HttpStatus.OK, "QR expiration updated successfully");
    }
}

