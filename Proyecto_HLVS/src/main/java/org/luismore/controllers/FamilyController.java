package org.luismore.controllers;

import org.luismore.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/family")
public class FamilyController {

    private final UserService userService;

    public FamilyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/members")
    public ResponseEntity<GeneralResponse> getFamilyMembers(@RequestHeader("Authorization") String authHeader) {
        User user = userService.findByToken(authHeader);
        if (user == null || !userService.isMainResident(user)) {
            return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        List<User> familyMembers = userService.findFamilyMembers(user.getHouseId());
        return GeneralResponse.getResponse(HttpStatus.OK, familyMembers);
    }

    @PostMapping("/add")
    public ResponseEntity<GeneralResponse> addFamilyMember(@RequestBody @Valid UserDTO userDTO, @RequestHeader("Authorization") String authHeader) {
        User user = userService.findByToken(authHeader);
        if (user == null || !userService.isMainResident(user)) {
            return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        userService.addFamilyMember(userDTO, user.getHouseId());
        return GeneralResponse.getResponse(HttpStatus.CREATED, "Family member added successfully");
    }
}