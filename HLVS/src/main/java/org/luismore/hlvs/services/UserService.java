package org.luismore.hlvs.services;

import org.luismore.hlvs.dtos.UserRegisterDto;
import org.luismore.hlvs.entities.Token;
import org.luismore.hlvs.entities.User;

public interface UserService {
    void create(UserRegisterDto info);
    User findByUsernameOrEmail(String username, String email);
    boolean checkPassword(User user, String password);
    Token registerToken(User user);
    User findByIdentifier(String identifier);
    boolean isTokenValid(User user, String token);
    Token loginWithGoogle(String idToken);
}
