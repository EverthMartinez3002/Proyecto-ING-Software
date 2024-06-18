//package org.luismore.hlvsapi.controllers;
//
//import jakarta.validation.Valid;
//import org.luismore.hlvsapi.domain.dtos.*;
//import org.luismore.hlvsapi.domain.entities.House;
//import org.luismore.hlvsapi.domain.entities.User;
//import org.luismore.hlvsapi.services.HouseService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/houses")
//public class HouseController {
//
//    private final HouseService houseService;
//
//    public HouseController(HouseService houseService) {
//        this.houseService = houseService;
//    }
//
//    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> getAllHouses() {
//        List<House> houses = houseService.getAllHouses();
//        return GeneralResponse.getResponse(HttpStatus.OK, houses);
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> createHouse(@RequestBody @Valid CreateHouseDTO createHouseDTO) {
//        houseService.createHouse(createHouseDTO);
//        return GeneralResponse.getResponse(HttpStatus.CREATED, "House created successfully.");
//    }
//
//    @PatchMapping("/update")
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> updateHouse(@RequestBody @Valid UpdateHouseDTO updateHouseDTO) {
//        houseService.updateHouse(updateHouseDTO);
//        return GeneralResponse.getResponse(HttpStatus.OK, "House updated successfully.");
//    }
//
//    @PatchMapping("/update-resident")
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> updateResident(@RequestBody @Valid UpdateResidentDTO updateResidentDTO) {
//        houseService.updateResident(updateResidentDTO);
//        return GeneralResponse.getResponse(HttpStatus.OK, "Resident updated successfully.");
//    }
//
//    @GetMapping("/residents/{houseId}")
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> getResidentsByHouseId(@PathVariable UUID houseId) {
//        List<User> residents = houseService.getResidentsByHouseId(houseId);
//        if (residents.isEmpty()) {
//            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "No residents found for the specified house.");
//        }
//        return GeneralResponse.getResponse(HttpStatus.OK, residents);
//    }
//}

//package org.luismore.hlvsapi.controllers;
//
//import org.luismore.hlvsapi.domain.dtos.CreateHouseDTO;
//import org.luismore.hlvsapi.domain.dtos.GeneralResponse;
//import org.luismore.hlvsapi.domain.entities.House;
//import org.luismore.hlvsapi.domain.entities.User;
//import org.luismore.hlvsapi.services.HouseService;
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
//@RequestMapping("/api/houses")
//public class HouseController {
//
//    private final HouseService houseService;
//    private final UserService userService;
//
//    public HouseController(HouseService houseService, UserService userService) {
//        this.houseService = houseService;
//        this.userService = userService;
//    }
//
//    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> getAllHouses() {
//        List<House> houses = houseService.getAllHouses();
//        return GeneralResponse.getResponse(HttpStatus.OK, houses);
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> createHouse(@RequestBody CreateHouseDTO createHouseDTO, @AuthenticationPrincipal UserDetails userDetails) {
//        User mainResident = userService.findByIdentifier(userDetails.getUsername());
//        houseService.createHouse(createHouseDTO, mainResident);
//        return GeneralResponse.getResponse(HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> updateHouse(@PathVariable UUID id, @RequestBody CreateHouseDTO updateHouseDTO) {
//        houseService.updateHouse(id, updateHouseDTO);
//        return GeneralResponse.getResponse(HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}/residents")
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> getResidentsByHouseId(@PathVariable UUID id) {
//        List<User> residents = houseService.getResidentsByHouseId(id);
//        return GeneralResponse.getResponse(HttpStatus.OK, residents);
//    }
//
//    @PutMapping("/{houseId}/residents/{userId}")
//    @PreAuthorize("hasAuthority('ROLE_admin')")
//    public ResponseEntity<GeneralResponse> updateResident(@PathVariable UUID houseId, @PathVariable UUID userId, @RequestBody CreateHouseDTO updateResidentDTO) {
//        houseService.updateResident(houseId, userId, updateResidentDTO);
//        return GeneralResponse.getResponse(HttpStatus.OK);
//    }
//}

package org.luismore.hlvsapi.controllers;

import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.services.HouseService;
import org.luismore.hlvsapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private final HouseService houseService;
    private final UserService userService;

    public HouseController(HouseService houseService, UserService userService) {
        this.houseService = houseService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllHouses() {
        List<HouseDTO> houses = houseService.getAllHouses();
        return GeneralResponse.getResponse(HttpStatus.OK, houses);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> createHouse(@RequestBody CreateHouseDTO createHouseDTO) {
        houseService.createHouse(createHouseDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> updateHouse(@PathVariable UUID id, @RequestBody UpdateHouseDTO updateHouseDTO) {
        updateHouseDTO.setId(id);
        houseService.updateHouse(updateHouseDTO);
        return GeneralResponse.getResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}/residents")
    @PreAuthorize("hasAuthority('ROLE_admin') or hasAuthority('ROLE_main resident')")
    public ResponseEntity<GeneralResponse> getResidentsByHouseId(@PathVariable UUID id) {
        List<UserDTO> residents = houseService.getResidentsByHouseId(id);
        return GeneralResponse.getResponse(HttpStatus.OK, residents);
    }

    @PutMapping("/{houseId}/residents")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> updateResident(@PathVariable UUID houseId, @RequestParam String email, @RequestBody UpdateResidentDTO updateResidentDTO) {
        updateResidentDTO.setHouseId(houseId);
        updateResidentDTO.setEmail(email);
        houseService.updateResident(updateResidentDTO);
        return GeneralResponse.getResponse(HttpStatus.OK);
    }

    @PutMapping("/{houseId}/leader")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> assignLeader(@PathVariable UUID houseId, @RequestParam String email) {
        houseService.assignLeader(houseId, email);
        return GeneralResponse.getResponse(HttpStatus.OK);
    }
}
