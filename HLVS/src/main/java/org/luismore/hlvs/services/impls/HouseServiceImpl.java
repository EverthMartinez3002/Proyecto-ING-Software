package org.luismore.hlvs.services.impls;

import org.luismore.hlvs.dtos.HouseDto;
import org.luismore.hlvs.entities.House;
import org.luismore.hlvs.entities.Resident;
import org.luismore.hlvs.repositories.HouseRepository;
import org.luismore.hlvs.repositories.ResidentRepository;
import org.luismore.hlvs.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public List<House> listAllHouses() {
        return houseRepository.findAll();
    }

    @Override
    public House createHouse(HouseDto houseDto) {
        House house = new House();
        house.setHouseNumber(houseDto.getHouseNumber());
        house.setAddress(houseDto.getAddress());
        house.setResidentNumber(houseDto.getResidentNumber());
        house.setResidentLimit(houseDto.getResidentLimit());
        return houseRepository.save(house);
    }

    @Override
    public Resident createResident(Long houseId, Resident resident) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House Can(not) be found"));
        if (house.getResidents().size() < house.getResidentLimit() && !house.hasMainResident()) {
            resident.setHouse(house);
            return residentRepository.save(resident);
        } else {
            throw new UnauthorizedException("You Can(not) add resident. The limit reached or main resident already exists.");
        }
    }

    @Override
    public House updateHouse(Long houseId, HouseDto houseDto) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House Can(not) be found"));
        house.setHouseNumber(houseDto.getHouseNumber());
        house.setAddress(houseDto.getAddress());
        house.setResidentNumber(houseDto.getResidentNumber());
        house.setResidentLimit(houseDto.getResidentLimit());
        return houseRepository.save(house);
    }

    @Override
    public Resident updateResident(Long houseId, Long residentId, Resident resident) {
        Resident existingResident = residentRepository.findById(residentId).orElseThrow(() -> new ResourceNotFoundException("Resident Can(not) be found"));
        existingResident.setName(resident.getName());
        existingResident.setRole(resident.getRole());
        return residentRepository.save(existingResident);
    }

    @Override
    public List<Resident> listResidentsByHouseId(Long houseId) {
        return residentRepository.findByHouseId(houseId);
    }
}
