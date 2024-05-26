package org.luismore.services;

import java.util.List;

public interface HouseService {
    List<House> findAll();
    void createHouse(HouseDTO houseDTO);
    void createResident(UserDTO userDTO);
    void updateHouse(HouseDTO houseDTO);
    List<User> findResidentsByHouseId(int houseId);
    boolean canAddResident(int houseId);
}

