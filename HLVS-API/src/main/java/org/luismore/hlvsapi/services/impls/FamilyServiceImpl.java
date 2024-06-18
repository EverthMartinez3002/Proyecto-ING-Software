package org.luismore.hlvsapi.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.AddFamilyMemberDTO;
import org.luismore.hlvsapi.domain.dtos.UserDTO;
import org.luismore.hlvsapi.domain.entities.House;
import org.luismore.hlvsapi.domain.entities.Role;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.HouseRepository;
import org.luismore.hlvsapi.repositories.RoleRepository;
import org.luismore.hlvsapi.repositories.UserRepository;
import org.luismore.hlvsapi.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FamilyServiceImpl implements FamilyService {

    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FamilyServiceImpl(UserRepository userRepository, HouseRepository houseRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getFamilyMembers(UUID houseId) {
        List<User> familyMembers = userRepository.findByHouseId(houseId);
        return familyMembers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addFamilyMember(UUID houseId, AddFamilyMemberDTO addFamilyMemberDTO) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException("House not found with id: " + houseId));

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
                .orElseThrow(() -> new IllegalArgumentException("Resident role not found"));
        user.setRoles(List.of(defaultRole));

        userRepository.save(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public boolean isHouseFull(UUID houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new IllegalArgumentException("House not found"));
        return house.getResidents().size() >= Integer.parseInt(house.getResidentNumber());
    }
}
