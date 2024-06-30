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

    @Override
    @Transactional
    public HouseDTO createHouse(CreateHouseDTO createHouseDTO) {
        House house = new House();
        house.setHouseNumber(createHouseDTO.getHouseNumber());
        house.setAddress(createHouseDTO.getAddress());
        house.setResidentNumber(createHouseDTO.getResidentNumber());

        if (createHouseDTO.getLeaderEmail() != null) {
            User leader = userRepository.findByEmail(createHouseDTO.getLeaderEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Leader Can(not) be found with email: " + createHouseDTO.getLeaderEmail()));
            Role mainResidentRole = roleRepository.findById("MAIN")
                    .orElseThrow(() -> new IllegalArgumentException("Main resident role Can(not) be found"));
            leader.getRoles().add(mainResidentRole);
            userRepository.save(leader);
            house.setLeader(leader);
        }

        house = houseRepository.save(house);
        return convertToDTO(house);
    }

    @Override
    @Transactional
    public void updateHouse(UpdateHouseDTO updateHouseDTO) {
        House house = houseRepository.findById(updateHouseDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("House not found"));

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
            int currentResidents = house.getResidents().size();
            int newResidents = updateHouseDTO.getResidents().size();
            int maxResidents = Integer.parseInt(house.getResidentNumber());

            if (currentResidents + newResidents > maxResidents) {
                throw new IllegalArgumentException("Adding these residents would exceed the maximum number of residents allowed for this house");
            }

            for (UpdateResidentDTO residentDTO : updateHouseDTO.getResidents()) {
                User user = userRepository.findByEmail(residentDTO.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + residentDTO.getEmail()));

                Role residentRole = roleRepository.findById("RESI")
                        .orElseThrow(() -> new IllegalArgumentException("Resident role not found"));

                user.getRoles().add(residentRole);
                user.setHouse(house);
                user.setDui(residentDTO.getDui());
                userRepository.save(user);
            }
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
        user.setDui(updateResidentDTO.getDui());
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
        dto.setDui(user.getDui());
        return dto;
    }

    private HouseDTO convertToDTO(House house) {
        HouseDTO dto = new HouseDTO();
        dto.setId(house.getId());
        dto.setHouseNumber(house.getHouseNumber());
        dto.setAddress(house.getAddress());
        dto.setResidentNumber(house.getResidentNumber());
        dto.setLeader(house.getLeader() != null ? house.getLeader().getEmail() : null);
        dto.setNameLeader(house.getLeader() != null ? house.getLeader().getName() : null); // Obtener el nombre del líder
        dto.setResidents(house.getResidents() != null ? house.getResidents().stream().map(this::convertToDTO).collect(Collectors.toList()) : null); // Verificar si la lista de residentes es null
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
