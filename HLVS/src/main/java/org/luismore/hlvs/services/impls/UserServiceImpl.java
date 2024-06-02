package org.luismore.hlvs.services.impls;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.luismore.hlvs.dtos.UserRegisterDto;
import org.luismore.hlvs.entities.Token;
import org.luismore.hlvs.entities.User;
import org.luismore.hlvs.repositories.TokenRepository;
import org.luismore.hlvs.repositories.UserRepository;
import org.luismore.hlvs.services.UserService;
import org.luismore.hlvs.utils.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTools jwtTools;

    private final GoogleIdTokenVerifier verifier;

    @Autowired
    public UserServiceImpl(GoogleIdTokenVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public void create(UserRegisterDto info) {
        User user = new User();
        user.setUsername(info.getUsername());
        user.setEmail(info.getEmail());
        user.setPassword(passwordEncoder.encode(info.getPassword()));
        user.setRole(info.getRole());
        userRepository.save(user);
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public Token registerToken(User user) {
        String tokenStr = jwtTools.generateToken(user);
        Token token = new Token();
        token.setToken(tokenStr);
        token.setUser(user);
        return tokenRepository.save(token);
    }

    @Override
    public User findByIdentifier(String identifier) {
        return userRepository.findByUsernameOrEmail(identifier, identifier);
    }

    @Override
    public boolean isTokenValid(User user, String token) {
        return tokenRepository.findByTokenAndUser(token, user).isPresent();
    }

    @Override
    public Token loginWithGoogle(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                String email = payload.getEmail();

                User user = userRepository.findByEmail(email);
                if (user == null) {
                    user = new User();
                    user.setEmail(email);
                    user.setUsername(email);
                    user.setRole("Resident");
                    userRepository.save(user);
                }

                return registerToken(user);
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
