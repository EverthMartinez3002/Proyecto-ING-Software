//package org.luismore.hlvsapi.services;
//
//import org.luismore.hlvsapi.domain.dtos.*;
//import org.luismore.hlvsapi.domain.entities.House;
//import org.luismore.hlvsapi.domain.entities.User;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface HouseService {
//    List<House> getAllHouses();
//    void createHouse(CreateHouseDTO createHouseDTO);
//    void updateHouse(UpdateHouseDTO updateHouseDTO);
//    void updateResident(UpdateResidentDTO updateResidentDTO);
//    List<User> getResidentsByHouseId(UUID houseId);
//    List<User> getFamilyMembers(UUID houseId);
//    boolean isHouseFull(UUID houseId);
//}

//package org.luismore.hlvsapi.services;
//
//import org.luismore.hlvsapi.domain.dtos.CreateHouseDTO;
//import org.luismore.hlvsapi.domain.entities.House;
//import org.luismore.hlvsapi.domain.entities.User;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface HouseService {
//    List<House> getAllHouses();
//    void createHouse(CreateHouseDTO createHouseDTO, User mainResident);
//    void updateHouse(UUID id, CreateHouseDTO updateHouseDTO);
//    List<User> getResidentsByHouseId(UUID houseId);
//    void updateResident(UUID houseId, UUID userId, CreateHouseDTO updateResidentDTO);
//}

//package org.luismore.hlvsapi.services;
//
//import org.luismore.hlvsapi.domain.dtos.CreateHouseDTO;
//import org.luismore.hlvsapi.domain.dtos.UpdateHouseDTO;
//import org.luismore.hlvsapi.domain.dtos.UpdateResidentDTO;
//import org.luismore.hlvsapi.domain.dtos.UserDTO;
//import org.luismore.hlvsapi.domain.entities.House;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface HouseService {
//    List<House> getAllHouses();
//    void createHouse(CreateHouseDTO createHouseDTO);
//    void updateHouse(UpdateHouseDTO updateHouseDTO);
//    void updateResident(UpdateResidentDTO updateResidentDTO);
//    List<UserDTO> getResidentsByHouseId(UUID houseId);
//    void assignLeader(UUID houseId, String email);
//}

package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.CreateHouseDTO;
import org.luismore.hlvsapi.domain.dtos.HouseDTO;
import org.luismore.hlvsapi.domain.dtos.UpdateHouseDTO;
import org.luismore.hlvsapi.domain.dtos.UpdateResidentDTO;
import org.luismore.hlvsapi.domain.dtos.UserDTO;

import java.util.List;
import java.util.UUID;

public interface HouseService {
    List<HouseDTO> getAllHouses();
    void createHouse(CreateHouseDTO createHouseDTO);
    void updateHouse(UpdateHouseDTO updateHouseDTO);
    void updateResident(UpdateResidentDTO updateResidentDTO);
    List<UserDTO> getResidentsByHouseId(UUID houseId);
    void assignLeader(UUID houseId, String email);
}



