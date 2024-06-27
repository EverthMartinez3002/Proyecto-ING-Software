package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.CreateQrDTO;
import org.luismore.hlvsapi.domain.dtos.CreateQrForRoleDTO;
import org.luismore.hlvsapi.domain.entities.QR;

public interface QrService {
    QR generateQrToken(CreateQrDTO createQrDTO);
    QR generateQrTokenForRole(CreateQrForRoleDTO createQrForRoleDTO);
    QR scanQrToken(String token, String serialNumber);
    void updateQrExpiration(int duration);
}
