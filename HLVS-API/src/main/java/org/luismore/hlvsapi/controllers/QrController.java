package org.luismore.hlvsapi.controllers;

import jakarta.validation.Valid;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.QR;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.services.QrService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    private final QrService qrService;

    public QrController(QrService qrService) {
        this.qrService = qrService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_resident') or hasAuthority('ROLE_visitant')")
    public ResponseEntity<GeneralResponse> getQrToken(@AuthenticationPrincipal User user) {
        QR qr = qrService.getQrByUserId(user.getId());
        if (qr == null) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "No QR token found for the user.");
        }
        return GeneralResponse.getResponse(HttpStatus.OK, qr);
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('ROLE_resident') or hasAuthority('ROLE_visitant')")
    public ResponseEntity<GeneralResponse> generateQrToken(@AuthenticationPrincipal User user, @RequestBody @Valid CreateQrDTO createQrDTO) {
        QR qr = qrService.generateQrToken(user, createQrDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, qr);
    }

    @PatchMapping("/update-expiration")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> updateQrExpiration(@RequestBody @Valid UpdateQrExpirationDTO updateQrExpirationDTO) {
        qrService.updateQrExpiration(updateQrExpirationDTO.getDuration());
        return GeneralResponse.getResponse(HttpStatus.OK, "QR expiration time updated successfully.");
    }
}
