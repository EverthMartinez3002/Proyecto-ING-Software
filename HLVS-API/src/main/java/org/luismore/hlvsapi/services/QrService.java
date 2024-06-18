package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.QR;
import org.luismore.hlvsapi.domain.entities.User;

import java.util.UUID;

public interface QrService {
    QR getQrByUserId(UUID userId);
    QR generateQrToken(User user, CreateQrDTO createQrDTO);
    void updateQrExpiration(int duration);
}
