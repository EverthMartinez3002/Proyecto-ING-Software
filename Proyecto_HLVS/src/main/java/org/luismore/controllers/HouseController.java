package org.luismore.controllers;


import org.luismore.services.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/all")
    public ResponseEntity<GeneralResponse> getAllHouses() {
        List<House> houses = houseService.findAll();
        return GeneralResponse.getResponse(HttpStatus.OK, houses);
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createHouse(@RequestBody @Valid HouseDTO houseDTO) {
        houseService.createHouse(houseDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, "House created successfully");
    }

    @PostMapping("/createResident")
    public ResponseEntity<GeneralResponse> createResident(@RequestBody @Valid UserDTO userDTO) {
        if (!houseService.canAddResident(userDTO.getHouseId())) {
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, "Can(not) add resident, limit exceeded");
        }
        houseService.createResident(userDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, "Resident created successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<GeneralResponse> updateHouse(@RequestBody @Valid HouseDTO houseDTO) {
        houseService.updateHouse(houseDTO);
        return GeneralResponse.getResponse(HttpStatus.OK, "House updated successfully");
    }

    @GetMapping("/residents/{houseId}")
    public ResponseEntity<GeneralResponse> getResidentsByHouseId(@PathVariable int houseId) {
        List<User> residents = houseService.findResidentsByHouseId(houseId);
        return GeneralResponse.getResponse(HttpStatus.OK, residents);
    }
}

