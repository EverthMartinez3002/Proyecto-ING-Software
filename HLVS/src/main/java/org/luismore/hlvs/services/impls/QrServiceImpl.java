package org.luismore.hlvs.services.impls;

import org.luismore.hlvs.entities.Request;
import org.luismore.hlvs.repositories.RequestRepository;
import org.luismore.hlvs.repositories.UserRepository;
import org.luismore.hlvs.services.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QrServiceImpl implements QrService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getToken(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found")).getToken();
    }

    @Override
    public String generateToken(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        String token = UUID.randomUUID().toString();
        request.setToken(token);
        requestRepository.save(request);
        return token;
    }

    @Override
    public boolean updateQrDuration(Long newDuration) {
        try {
            qrRepository.updateQrDuration(newDuration);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
