package org.luismore.hlvsapi.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.House;
import org.luismore.hlvsapi.domain.entities.Role;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.HouseRepository;
import org.luismore.hlvsapi.repositories.RoleRepository;
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
    private final RoleRepository roleRepository;

    public HouseServiceImpl(HouseRepository houseRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        return houses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

//    @Override
//    @Transactional
//    public void createHouse(CreateHouseDTO createHouseDTO) {
//        House house = new House();
//        house.setHouseNumber(createHouseDTO.getHouseNumber());
//        house.setAddress(createHouseDTO.getAddress());
//        house.setResidentNumber(createHouseDTO.getResidentNumber());
//        houseRepository.save(house);
//    }

    @Override
    @Transactional
    public void createHouse(CreateHouseDTO createHouseDTO) {
        House house = new House();
        house.setHouseNumber(createHouseDTO.getHouseNumber());
        house.setAddress(createHouseDTO.getAddress());
        house.setResidentNumber(createHouseDTO.getResidentNumber());

        if (createHouseDTO.getLeaderEmail() != null) {
            User leader = userRepository.findByEmail(createHouseDTO.getLeaderEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Leader Can(not) be found with email: " + createHouseDTO.getLeaderEmail()));
            house.setLeader(leader);
        }

        houseRepository.save(house);
    }

    @Override
    @Transactional
    public void addFamilyMember(UUID houseId, List<AddFamilyMemberDTO> addFamilyMemberDTOList) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException("House Can(not) be found with id: " + houseId));

        for (AddFamilyMemberDTO addFamilyMemberDTO : addFamilyMemberDTOList) {
            List<User> existingUsers = userRepository.findByEmailIn(List.of(addFamilyMemberDTO.getEmail()));
            if (!existingUsers.isEmpty() && existingUsers.get(0).getHouse().getId().equals(houseId)) {
                throw new IllegalArgumentException("User already exists in the same house");
            }

            User user = new User();
            user.setName(addFamilyMemberDTO.getName());
            user.setEmail(addFamilyMemberDTO.getEmail());
            user.setPassword(passwordEncoder.encode(addFamilyMemberDTO.getPassword()));
            user.setHouse(house);

            Role defaultRole = roleRepository.findById("RESI")
                    .orElseThrow(() -> new IllegalArgumentException("Resident role Can(not) be found"));

            if (house.getLeader() == null) {
                user.setRoles(List.of(defaultRole, roleRepository.findById("MAIN").orElseThrow(() -> new IllegalArgumentException("Main resident role Can(not) be found"))));
                house.setLeader(user);
            } else {
                user.setRoles(List.of(defaultRole));
            }

            userRepository.save(user);
        }
        houseRepository.save(house);
    }

//    @Override
//    @Transactional
//    public void updateHouse(UpdateHouseDTO updateHouseDTO) {
//        House house = houseRepository.findById(updateHouseDTO.getId())
//                .orElseThrow(() -> new IllegalArgumentException("House Can(not) be found"));
//        house.setAddress(updateHouseDTO.getAddress());
//        house.setResidentNumber(updateHouseDTO.getResidentNumber());
//        houseRepository.save(house);
//    }

    @Override
    @Transactional
    public void updateHouse(UpdateHouseDTO updateHouseDTO) {
        House house = houseRepository.findById(updateHouseDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("House Can(not) be found"));

        if (updateHouseDTO.getHouseNumber() != null) {
            house.setHouseNumber(updateHouseDTO.getHouseNumber());
        }
        if (updateHouseDTO.getAddress() != null) {
            house.setAddress(updateHouseDTO.getAddress());
        }
        if (updateHouseDTO.getResidentNumber() != null) {
            house.setResidentNumber(updateHouseDTO.getResidentNumber());
        }
        if (updateHouseDTO.getResidents() != null && !updateHouseDTO.getResidents().isEmpty()) {
            List<User> residents = updateHouseDTO.getResidents().stream()
                    .map(userDTO -> {
                        User user = userRepository.findByEmail(userDTO.getEmail())
                                .orElseThrow(() -> new IllegalArgumentException("User Can(not) be found with email: " + userDTO.getEmail()));
                        user.setName(userDTO.getUsername() != null ? userDTO.getUsername() : user.getName());
                        if (userDTO.getPassword() != null) {
                            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                        }
                        user.setHouse(house);
                        return user;
                    }).collect(Collectors.toList());
            userRepository.saveAll(residents);
        }
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

    @Override
    public HouseDTO getHouseByNumber(String houseNumber) {
        House house = houseRepository.findByHouseNumber(houseNumber)
                .orElseThrow(() -> new EntityNotFoundException("House Can(not) be found with house number: " + houseNumber));
        return convertToDTO(house);
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
