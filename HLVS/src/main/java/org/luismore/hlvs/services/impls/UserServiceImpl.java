package org.luismore.hlvs.services.impls;

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

    @Override
    public void create(UserRegisterDto info) {
        User user = new User();
        user.setUsername(info.getUsername());
        user.setEmail(info.getEmail());
        user.setPassword(info.getPassword());
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
}
