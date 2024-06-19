package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.*;

import java.util.List;
import java.util.UUID;

public interface HouseService {
    List<HouseDTO> getAllHouses();
    void createHouse(CreateHouseDTO createHouseDTO);
    void updateHouse(UpdateHouseDTO updateHouseDTO);
    void updateResident(UpdateResidentDTO updateResidentDTO);
    void addFamilyMember(UUID houseId, List<AddFamilyMemberDTO> addFamilyMemberDTOList); // Modificado a lista
    List<UserDTO> getResidentsByHouseId(UUID houseId);
    void assignLeader(UUID houseId, String email);
    HouseDTO getHouseByNumber(String houseNumber);
}



