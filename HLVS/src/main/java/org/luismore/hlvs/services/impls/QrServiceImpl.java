package org.luismore.hlvs.services.impls;

import org.luismore.hlvs.domain.entities.Request;
import org.luismore.hlvs.domain.entities.Token;
import org.luismore.hlvs.exceptions.ResourceNotFoundException;
import org.luismore.hlvs.repositories.RequestRepository;
import org.luismore.hlvs.repositories.TokenRepository;
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

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token getTokenByUserId(Long userId) {
        return tokenRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }


    @Override
    public Token generateToken(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        Token token = new Token();
        token.setContent(UUID.randomUUID().toString());
        token.setUser(request.getUser());
        tokenRepository.save(token);
        return token;
    }

    @Override
    public boolean updateQrDuration(Long newDuration) {
        try {
            requestRepository.updateQrDuration(newDuration);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
