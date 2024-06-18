//package org.luismore.hlvsapi.services.impls;
//
//import jakarta.transaction.Transactional;
//import org.luismore.hlvsapi.domain.dtos.*;
//import org.luismore.hlvsapi.domain.entities.House;
//import org.luismore.hlvsapi.domain.entities.User;
//import org.luismore.hlvsapi.repositories.HouseRepository;
//import org.luismore.hlvsapi.repositories.UserRepository;
//import org.luismore.hlvsapi.services.HouseService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class HouseServiceImpl implements HouseService {
//
//    private final HouseRepository houseRepository;
//    private final UserRepository userRepository;
//
//    public HouseServiceImpl(HouseRepository houseRepository, UserRepository userRepository) {
//        this.houseRepository = houseRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public List<House> getAllHouses() {
//        return houseRepository.findAll();
//    }
//
//    @Override
//    @Transactional
//    public void createHouse(CreateHouseDTO createHouseDTO) {
//        House house = new House();
//        house.setHouseNumber(createHouseDTO.getHouseNumber());
//        house.setAddress(createHouseDTO.getAddress());
//        house.setResidentNumber(createHouseDTO.getResidentNumber());
//        houseRepository.save(house);
//    }
//
//    @Override
//    @Transactional
//    public void updateHouse(UpdateHouseDTO updateHouseDTO) {
//        House house = houseRepository.findById(updateHouseDTO.getId())
//                .orElseThrow(() -> new IllegalArgumentException("House not found"));
//        house.setAddress(updateHouseDTO.getAddress());
//        house.setResidentNumber(updateHouseDTO.getResidentNumber());
//        houseRepository.save(house);
//    }
//
//    @Override
//    @Transactional
//    public void updateResident(UpdateResidentDTO updateResidentDTO) {
//        User user = userRepository.findById(updateResidentDTO.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        House house = houseRepository.findById(updateResidentDTO.getHouseId())
//                .orElseThrow(() -> new IllegalArgumentException("House not found"));
//        user.setHouse(house);
//        userRepository.save(user);
//    }
//
//    @Override
//    public List<User> getResidentsByHouseId(UUID houseId) {
//        return userRepository.findByHouseId(houseId);
//    }
//
//    @Override
//    public List<User> getFamilyMembers(UUID houseId) {
//        return userRepository.findByHouseId(houseId);
//    }
//
//    @Override
//    public boolean isHouseFull(UUID houseId) {
//        House house = houseRepository.findById(houseId).orElseThrow(() -> new IllegalArgumentException("House not found"));
//        return house.getResidents().size() >= Integer.parseInt(house.getResidentNumber());
//    }
//}

//package org.luismore.hlvsapi.services.impls;
//
//import jakarta.transaction.Transactional;
//import org.luismore.hlvsapi.domain.dtos.CreateHouseDTO;
//import org.luismore.hlvsapi.domain.entities.House;
//import org.luismore.hlvsapi.domain.entities.Role;
//import org.luismore.hlvsapi.domain.entities.User;
//import org.luismore.hlvsapi.repositories.HouseRepository;
//import org.luismore.hlvsapi.repositories.RoleRepository;
//import org.luismore.hlvsapi.repositories.UserRepository;
//import org.luismore.hlvsapi.services.HouseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class HouseServiceImpl implements HouseService {
//
//    private final HouseRepository houseRepository;
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//
//    @Autowired
//    public HouseServiceImpl(HouseRepository houseRepository, UserRepository userRepository, RoleRepository roleRepository) {
//        this.houseRepository = houseRepository;
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    public List<House> getAllHouses() {
//        return houseRepository.findAll();
//    }
//
//    @Override
//    @Transactional
//    public void createHouse(CreateHouseDTO createHouseDTO, User mainResident) {
//        House house = new House();
//        house.setHouseNumber(createHouseDTO.getHouseNumber());
//        house.setAddress(createHouseDTO.getAddress());
//        house.setResidentNumber(createHouseDTO.getResidentNumber());
//
//        houseRepository.save(house);
//
//        mainResident.setHouse(house);
//        Role mainResidentRole = roleRepository.findById("MAIN")
//                .orElseThrow(() -> new IllegalArgumentException("Main resident role not found"));
//        mainResident.setRoles(List.of(mainResidentRole));
//        userRepository.save(mainResident);
//    }
//
//    @Override
//    @Transactional
//    public void updateHouse(UUID id, CreateHouseDTO updateHouseDTO) {
//        House house = houseRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("House not found"));
//        house.setAddress(updateHouseDTO.getAddress());
//        house.setResidentNumber(updateHouseDTO.getResidentNumber());
//        houseRepository.save(house);
//    }
//
//    @Override
//    public List<User> getResidentsByHouseId(UUID houseId) {
//        return userRepository.findByHouseId(houseId);
//    }
//
//    @Override
//    @Transactional
//    public void updateResident(UUID houseId, UUID userId, CreateHouseDTO updateResidentDTO) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        House house = houseRepository.findById(houseId)
//                .orElseThrow(() -> new IllegalArgumentException("House not found"));
//        user.setHouse(house);
//        userRepository.save(user);
//    }
//}

//package org.luismore.hlvsapi.services.impls;
//
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import org.luismore.hlvsapi.domain.dtos.*;
//import org.luismore.hlvsapi.domain.entities.House;
//import org.luismore.hlvsapi.domain.entities.User;
//import org.luismore.hlvsapi.repositories.HouseRepository;
//import org.luismore.hlvsapi.repositories.UserRepository;
//import org.luismore.hlvsapi.services.HouseService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class HouseServiceImpl implements HouseService {
//
//    private final HouseRepository houseRepository;
//    private final UserRepository userRepository;
//
//    public HouseServiceImpl(HouseRepository houseRepository, UserRepository userRepository) {
//        this.houseRepository = houseRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public List<House> getAllHouses() {
//        return houseRepository.findAll();
//    }
//
//    @Override
//    @Transactional
//    public void createHouse(CreateHouseDTO createHouseDTO) {
//        House house = new House();
//        house.setHouseNumber(createHouseDTO.getHouseNumber());
//        house.setAddress(createHouseDTO.getAddress());
//        house.setResidentNumber(createHouseDTO.getResidentNumber());
//        houseRepository.save(house);
//    }
//
//    @Override
//    @Transactional
//    public void updateHouse(UpdateHouseDTO updateHouseDTO) {
//        House house = houseRepository.findById(updateHouseDTO.getId())
//                .orElseThrow(() -> new IllegalArgumentException("House not found"));
//        house.setAddress(updateHouseDTO.getAddress());
//        house.setResidentNumber(updateHouseDTO.getResidentNumber());
//        houseRepository.save(house);
//    }
//
//    @Override
//    @Transactional
//    public void updateResident(UpdateResidentDTO updateResidentDTO) {
//        User user = userRepository.findById(updateResidentDTO.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        House house = houseRepository.findById(updateResidentDTO.getHouseId())
//                .orElseThrow(() -> new IllegalArgumentException("House not found"));
//        user.setHouse(house);
//        userRepository.save(user);
//    }
//
//    @Override
//    public List<UserDTO> getResidentsByHouseId(UUID houseId) {
//        List<User> residents = userRepository.findByHouseId(houseId);
//        return residents.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    private UserDTO convertToDTO(User user) {
//        UserDTO dto = new UserDTO();
//        dto.setId(user.getId());
//        dto.setUsername(user.getName());
//        dto.setEmail(user.getEmail());
//        return dto;
//    }
//
//    @Override
//    @Transactional
//    public void assignLeader(UUID houseId, String email) {
//        House house = houseRepository.findById(houseId)
//                .orElseThrow(() -> new EntityNotFoundException("House not found with id: " + houseId));
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
//
//        if (!user.getHouse().getId().equals(houseId)) {
//            throw new IllegalArgumentException("User does not belong to this house");
//        }
//
//        house.setLeader(user);
//        houseRepository.save(house);
//    }
//}

package org.luismore.hlvsapi.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.House;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.HouseRepository;
import org.luismore.hlvsapi.repositories.UserRepository;
import org.luismore.hlvsapi.services.HouseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public HouseServiceImpl(HouseRepository houseRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        return houses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createHouse(CreateHouseDTO createHouseDTO) {
        House house = new House();
        house.setHouseNumber(createHouseDTO.getHouseNumber());
        house.setAddress(createHouseDTO.getAddress());
        house.setResidentNumber(createHouseDTO.getResidentNumber());
        houseRepository.save(house);
    }

    @Override
    @Transactional
    public void updateHouse(UpdateHouseDTO updateHouseDTO) {
        House house = houseRepository.findById(updateHouseDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("House Can(not) be found"));
        house.setAddress(updateHouseDTO.getAddress());
        house.setResidentNumber(updateHouseDTO.getResidentNumber());
        houseRepository.save(house);
    }

    @Override
    @Transactional
    public void updateResident(UpdateResidentDTO updateResidentDTO) {
        User user = userRepository.findByEmail(updateResidentDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User Can(not) be found"));
        House house = houseRepository.findById(updateResidentDTO.getHouseId())
                .orElseThrow(() -> new IllegalArgumentException("House Can(not) be found"));
        if (updateResidentDTO.getName() != null) {
            user.setName(updateResidentDTO.getName());
        }
        if (updateResidentDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateResidentDTO.getPassword()));
        }
        user.setHouse(house);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getResidentsByHouseId(UUID houseId) {
        List<User> residents = userRepository.findByHouseId(houseId);
        return residents.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private HouseDTO convertToDTO(House house) {
        HouseDTO dto = new HouseDTO();
        dto.setId(house.getId());
        dto.setHouseNumber(house.getHouseNumber());
        dto.setAddress(house.getAddress());
        dto.setResidentNumber(house.getResidentNumber());
        dto.setLeader(house.getLeader() != null ? house.getLeader().getEmail() : null);
        dto.setResidents(house.getResidents().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return dto;
    }

    @Override
    @Transactional
    public void assignLeader(UUID houseId, String email) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException("House Can(not) be found with id: " + houseId));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User Can(not) be found with email: " + email));

        if (!user.getHouse().getId().equals(houseId)) {
            throw new IllegalArgumentException("User Can(not) belong to this house");
        }

        house.setLeader(user);
        houseRepository.save(house);
    }
}
