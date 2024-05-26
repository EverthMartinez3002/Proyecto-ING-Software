package org.luismore.services.impls;


import org.luismore.services.QrService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QrServiceImpl implements QrService {

    private final QrRepository qrRepository;

    public QrServiceImpl(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    @Override
    public String getTokenByUserId(int userId) {
        return qrRepository.findTokenByUserId(userId);
    }

    @Override
    public UUID generateToken(int requestId) {
        UUID token = UUID.randomUUID();
        qrRepository.saveToken(requestId, token);
        return token;
    }

    @Override
    public void updateQrExpiration(UpdateQrDTO updateQrDTO) {
        qrRepository.updateExpiration(updateQrDTO.getQrId(), updateQrDTO.getExpirationTime());
    }
}
