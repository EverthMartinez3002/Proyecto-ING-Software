package org.luismore.hlvsapi.controllers;

import org.luismore.hlvsapi.domain.dtos.CreateQrDTO;
import org.luismore.hlvsapi.domain.dtos.CreateQrForRoleDTO;
import org.luismore.hlvsapi.domain.dtos.CreateQrForUserDTO;
import org.luismore.hlvsapi.domain.dtos.GeneralResponse;
import org.luismore.hlvsapi.domain.entities.QR;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.services.QrService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    private final QrService qrService;

    public QrController(QrService qrService) {
        this.qrService = qrService;
    }

    @PostMapping("/generate")
    public ResponseEntity<QR> generateQrToken(@RequestBody CreateQrDTO createQrDTO) {
        QR qr = qrService.generateQrToken(createQrDTO);
        return ResponseEntity.ok(qr);
    }

    @PostMapping("/generate-for-role")
    public ResponseEntity<QR> generateQrTokenForRole(@AuthenticationPrincipal User user, @RequestBody CreateQrForRoleDTO createQrForRoleDTO) {
        QR qr = qrService.generateQrTokenForRole(user, createQrForRoleDTO);
        return ResponseEntity.ok(qr);
    }

    @GetMapping("/scan")
    public ResponseEntity<QR> scanQrToken(@RequestParam String token, @RequestParam String serialNumber) {
        QR qr = qrService.scanQrToken(token, serialNumber);
        return ResponseEntity.ok(qr);
    }

    @PutMapping("/expiration")
    public ResponseEntity<GeneralResponse> updateQrExpiration(@RequestParam int duration) {
        qrService.updateQrExpiration(duration);
        return GeneralResponse.getResponse(HttpStatus.OK, "Limit time updated successfully.");
    }

    @PostMapping("/generate-by-user")
    public ResponseEntity<QR> generateQrTokenByUser(@AuthenticationPrincipal User user, @RequestBody CreateQrForUserDTO createQrDTO) {
        QR qr = qrService.generateQrTokenByUser(user, createQrDTO);
        return ResponseEntity.ok(qr);
    }

}
