package org.luismore.services.impls;

import org.luismore.services.HouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    public HouseServiceImpl(HouseRepository houseRepository, UserRepository userRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public void createHouse(HouseDTO houseDTO) {
        House house = new House();
        house.setHouseNumber(houseDTO.getHouseNumber());
        house.setAdress(houseDTO.getAdress());
        house.setResidentNumber(houseDTO.getResidentNumber());
        houseRepository.save(house);
    }

    @Override
    public void createResident(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setHouseId(userDTO.getHouseId());
        userRepository.save(user);
    }

    @Override
    public void updateHouse(HouseDTO houseDTO) {
        House house = houseRepository.findById(houseDTO.getId()).orElseThrow();
        house.setHouseNumber(houseDTO.getHouseNumber());
        house.setAdress(houseDTO.getAdress());
        house.setResidentNumber(houseDTO.getResidentNumber());
        houseRepository.save(house);
    }

    @Override
    public List<User> findResidentsByHouseId(int houseId) {
        return userRepository.findByHouseId(houseId);
    }

    @Override
    public boolean canAddResident(int houseId) {
        House house = houseRepository.findById(houseId).orElseThrow();
        return house.getResidentNumber() > userRepository.countByHouseId(houseId);
    }
}
