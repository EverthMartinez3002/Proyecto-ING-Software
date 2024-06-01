package org.luismore.hlvs.services;

import org.luismore.hlvs.dtos.HouseDto;
import org.luismore.hlvs.entities.House;
import org.luismore.hlvs.entities.Resident;

import java.util.List;

public interface HouseService {
    List<House> listAllHouses();
    House createHouse(HouseDto houseDto);
    Resident createResident(Long houseId, Resident resident);
    House updateHouse(Long houseId, HouseDto houseDto);
    Resident updateResident(Long houseId, Long residentId, Resident resident);
    List<Resident> listResidentsByHouseId(Long houseId);
}
