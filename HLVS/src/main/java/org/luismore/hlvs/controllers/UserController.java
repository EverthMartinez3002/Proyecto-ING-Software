package org.luismore.hlvs.controllers;

import org.luismore.hlvs.dtos.GeneralResponse;
import org.luismore.hlvs.dtos.UserLoginDto;
import org.luismore.hlvs.dtos.UserRegisterDto;
import org.luismore.hlvs.entities.Token;
import org.luismore.hlvs.entities.User;
import org.luismore.hlvs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody UserRegisterDto userRegisterDto) {
        userService.create(userRegisterDto);
        return GeneralResponse.getResponse("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody UserLoginDto userLoginDto) {
        User user = userService.findByUsernameOrEmail(userLoginDto.getUsername(), userLoginDto.getUsername());
        if (user == null || !userService.checkPassword(user, userLoginDto.getPassword())) {
            return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        Token token = userService.registerToken(user);
        return GeneralResponse.getResponse(token, "Login successful");
    }
}
