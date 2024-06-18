////package org.luismore.hlvsapi.controllers;
////
////import jakarta.validation.Valid;
////import org.luismore.hlvsapi.domain.dtos.*;
////import org.luismore.hlvsapi.domain.entities.User;
////import org.luismore.hlvsapi.services.HouseService;
////import org.luismore.hlvsapi.services.UserService;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.security.core.annotation.AuthenticationPrincipal;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////import java.util.UUID;
////
////@RestController
////@RequestMapping("/api/family")
////public class FamilyController {
////
////    private final UserService userService;
////    private final HouseService houseService;
////
////    public FamilyController(UserService userService, HouseService houseService) {
////        this.userService = userService;
////        this.houseService = houseService;
////    }
////
////    @GetMapping("/members")
////    @PreAuthorize("hasAuthority('ROLE_main resident')")
////    public ResponseEntity<GeneralResponse> getFamilyMembers(@AuthenticationPrincipal User user) {
////        List<User> familyMembers = houseService.getFamilyMembers(user.getHouse().getId());
////        if (familyMembers.isEmpty()) {
////            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "No family members found for the main resident.");
////        }
////        return GeneralResponse.getResponse(HttpStatus.OK, familyMembers);
////    }
////
////    @PostMapping("/add")
////    @PreAuthorize("hasAuthority('ROLE_main resident')")
////    public ResponseEntity<GeneralResponse> addFamilyMember(@AuthenticationPrincipal User user, @RequestBody @Valid AddFamilyMemberDTO addFamilyMemberDTO) {
////        if (houseService.isHouseFull(user.getHouse().getId())) {
////            return GeneralResponse.getResponse(HttpStatus.CONFLICT, "Cannot add more members, house is full.");
////        }
////        userService.addFamilyMember(user.getHouse().getId(), addFamilyMemberDTO);
////        return GeneralResponse.getResponse(HttpStatus.CREATED, "Family member added successfully.");
////    }
////}
//
//package org.luismore.hlvsapi.controllers;
//
//import org.luismore.hlvsapi.domain.dtos.AddFamilyMemberDTO;
//import org.luismore.hlvsapi.domain.dtos.GeneralResponse;
//import org.luismore.hlvsapi.domain.dtos.UserDTO;
//import org.luismore.hlvsapi.domain.entities.User;
//import org.luismore.hlvsapi.services.FamilyService;
//import org.luismore.hlvsapi.services.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/family")
//public class FamilyController {
//
//    private final FamilyService familyService;
//    private final UserService userService;
//
//    public FamilyController(FamilyService familyService, UserService userService) {
//        this.familyService = familyService;
//        this.userService = userService;
//    }
//
//    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_main resident')")
//    public ResponseEntity<GeneralResponse> getFamilyMembers(@AuthenticationPrincipal UserDetails userDetails) {
//        User mainResident = userService.findByIdentifier(userDetails.getUsername());
//        List<UserDTO> familyMembers = familyService.getFamilyMembers(mainResident.getHouse().getId());
//        return GeneralResponse.getResponse(HttpStatus.OK, familyMembers);
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('ROLE_main resident')")
//    public ResponseEntity<GeneralResponse> addFamilyMember(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddFamilyMemberDTO addFamilyMemberDTO) {
//        User mainResident = userService.findByIdentifier(userDetails.getUsername());
//        UUID houseId = mainResident.getHouse().getId();
//        familyService.addFamilyMember(houseId, addFamilyMemberDTO);
//        return GeneralResponse.getResponse(HttpStatus.CREATED);
//    }
//}

package org.luismore.hlvsapi.controllers;

import org.luismore.hlvsapi.domain.dtos.AddFamilyMemberDTO;
import org.luismore.hlvsapi.domain.dtos.GeneralResponse;
import org.luismore.hlvsapi.domain.dtos.UserDTO;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.services.FamilyService;
import org.luismore.hlvsapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/family")
public class FamilyController {

    private final FamilyService familyService;
    private final UserService userService;

    public FamilyController(FamilyService familyService, UserService userService) {
        this.familyService = familyService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_main resident')")
    public ResponseEntity<GeneralResponse> getFamilyMembers(@AuthenticationPrincipal UserDetails userDetails) {
        User mainResident = userService.findByIdentifier(userDetails.getUsername());
        List<UserDTO> familyMembers = familyService.getFamilyMembers(mainResident.getHouse().getId());
        return GeneralResponse.getResponse(HttpStatus.OK, familyMembers);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_main resident')")
    public ResponseEntity<GeneralResponse> addFamilyMember(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddFamilyMemberDTO addFamilyMemberDTO) {
        User mainResident = userService.findByIdentifier(userDetails.getUsername());
        UUID houseId = mainResident.getHouse().getId();
        familyService.addFamilyMember(houseId, addFamilyMemberDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED);
    }
}
