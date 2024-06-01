package org.luismore.hlvs.controllers;

import org.luismore.hlvs.dtos.HouseDto;
import org.luismore.hlvs.entities.House;
import org.luismore.hlvs.entities.Resident;
import org.luismore.hlvs.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/")
    public ResponseEntity<GeneralResponse> getAllHouses() {
        List<House> houses = houseService.getAllHouses();
        return GeneralResponse.getResponse(houses, "Houses fetched successfully");
    }

    @PostMapping("/")
    public ResponseEntity<GeneralResponse> createHouse(@RequestBody House house) {
        House createdHouse = houseService.createHouse(house);
        return GeneralResponse.getResponse(createdHouse, "House created successfully");
    }

    @PostMapping("/{houseId}/residents")
    public ResponseEntity<GeneralResponse> createResidents(@PathVariable Long houseId, @RequestBody List<User> residents) {
        List<User> createdResidents = houseService.createResidents(houseId, residents);
        return GeneralResponse.getResponse(createdResidents, "Residents created successfully");
    }

    @PutMapping("/{houseId}")
    public ResponseEntity<GeneralResponse> updateHouse(@PathVariable Long houseId, @RequestBody House house) {
        House updatedHouse = houseService.updateHouse(houseId, house);
        return GeneralResponse.getResponse(updatedHouse, "House updated successfully");
    }

    @PutMapping("/{houseId}/residents")
    public ResponseEntity<GeneralResponse> updateResidents(@PathVariable Long houseId, @RequestBody List<User> residents) {
        List<User> updatedResidents = houseService.updateResidents(houseId, residents);
        return GeneralResponse.getResponse(updatedResidents, "Residents updated successfully");
    }

    @GetMapping("/{houseId}/residents")
    public ResponseEntity<GeneralResponse> getResidentsByHouseId(@PathVariable Long houseId) {
        List<User> residents = houseService.getResidentsByHouseId(houseId);
        return GeneralResponse.getResponse(residents, "Residents fetched successfully");
    }
}