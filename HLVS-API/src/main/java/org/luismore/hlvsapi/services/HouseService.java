package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.*;

import java.util.List;
import java.util.UUID;

public interface HouseService {
    List<HouseDTO> getAllHouses();
    HouseDTO createHouse(CreateHouseDTO createHouseDTO);
    void updateHouse(UpdateHouseDTO updateHouseDTO);
    void updateResident(UpdateResidentDTO updateResidentDTO);
    List<UserDTO> getResidentsByHouseId(UUID houseId);
    void assignLeader(UUID houseId, String email);
    List<HouseDTO> getHouseByNumber(String houseNumber);
    List<HouseDTO> getHouseByUserEmail(String email);
    List<HouseDTO> getHouseByAddress(String address);
}
