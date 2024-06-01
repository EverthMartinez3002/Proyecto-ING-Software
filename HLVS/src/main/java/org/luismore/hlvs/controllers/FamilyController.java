package org.luismore.hlvs.controllers;

import org.luismore.hlvs.dtos.FamilyMemberDto;
import org.luismore.hlvs.entities.FamilyMember;
import org.luismore.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @GetMapping("/members")
    public ResponseEntity<GeneralResponse> getFamilyMembers() {
        List<User> familyMembers = familyService.getFamilyMembers();
        return GeneralResponse.getResponse(familyMembers, "Family members fetched successfully");
    }

    @PostMapping("/add")
    public ResponseEntity<GeneralResponse> addFamilyMember(@RequestBody User user) {
        User addedMember = familyService.addFamilyMember(user);
        return GeneralResponse.getResponse(addedMember, "Family member added successfully");
    }
}