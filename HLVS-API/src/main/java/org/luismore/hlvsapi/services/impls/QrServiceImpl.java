package org.luismore.hlvsapi.services.impls;

import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.CreateQrDTO;
import org.luismore.hlvsapi.domain.entities.QR;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.QrRepository;
import org.luismore.hlvsapi.repositories.RequestRepository;
import org.luismore.hlvsapi.services.QrService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QrServiceImpl implements QrService {

    private final QrRepository qrRepository;
    private final RequestRepository requestRepository;

    public QrServiceImpl(QrRepository qrRepository, RequestRepository requestRepository) {
        this.qrRepository = qrRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public QR getQrByUserId(UUID userId) {
        return qrRepository.findByUserId(userId).orElse(null);
    }

    @Override
    @Transactional
    public QR generateQrToken(User user, CreateQrDTO createQrDTO) {
        QR qr = new QR();
        qr.setToken(createQrDTO.getToken());
        qr.setDuration(createQrDTO.getDuration());
        qr.setUser(user);
        Request request = requestRepository.findById(createQrDTO.getRequestId())
                .orElseThrow(() -> new IllegalArgumentException("Request Can(not) be found"));
        qr.setRequest(request);
        return qrRepository.save(qr);
    }

    @Override
    @Transactional
    public void updateQrExpiration(int duration) {
        qrRepository.updateQrExpiration(duration);
    }
}
