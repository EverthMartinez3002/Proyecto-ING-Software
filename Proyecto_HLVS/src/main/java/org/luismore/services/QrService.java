package org.luismore.services;

import java.util.UUID;

public interface QrService {
    String getTokenByUserId(int userId);
    UUID generateToken(int requestId);
    void updateQrExpiration(UpdateQrDTO updateQrDTO);
}

