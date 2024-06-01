package org.luismore.hlvs.services;

public interface QrService {
    String getToken(Long userId);
    String generateToken(Long requestId);
}
