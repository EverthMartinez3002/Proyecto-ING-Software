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

    @GetMapping("/number/{houseNumber}")
    public ResponseEntity<GeneralResponse> getHouseByNumber(@PathVariable String houseNumber) {
        HouseDTO house = houseService.getHouseByNumber(houseNumber);
        return GeneralResponse.getResponse(HttpStatus.OK, house);
    }
}
