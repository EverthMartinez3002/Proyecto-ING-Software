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

//    @Override
//    @Transactional
//    public void addFamilyMember(UUID houseId, List<AddFamilyMemberDTO> addFamilyMemberDTOList) {
//        House house = houseRepository.findById(houseId)
//                .orElseThrow(() -> new EntityNotFoundException("House Can(not) be found with id: " + houseId));
//
//        for (AddFamilyMemberDTO addFamilyMemberDTO : addFamilyMemberDTOList) {
//            User user = userRepository.findByEmail(addFamilyMemberDTO.getEmail())
//                    .orElseThrow(() -> new IllegalArgumentException("User Can(not) be found with email: " + addFamilyMemberDTO.getEmail()));
//
//            if (user.getHouse() != null && user.getHouse().getId().equals(houseId)) {
//                throw new IllegalArgumentException("User already exists in the same house");
//            }
//
//            user.setHouse(house);
//            user.setDui(addFamilyMemberDTO.getDui());
//
//            Role residentRole = roleRepository.findById("RESI")
//                    .orElseThrow(() -> new IllegalArgumentException("Resident role Can(not) be found"));
//            user.getRoles().add(residentRole);
//
//            userRepository.save(user);
//        }
//
//        houseRepository.save(house);
//    }

    @Override
    @Transactional
    public void addFamilyMember(UUID houseId, List<AddFamilyMemberDTO> addFamilyMemberDTOList) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException("House not found with id: " + houseId));

        int currentResidents = house.getResidents().size();
        int newResidents = addFamilyMemberDTOList.size();
        int maxResidents = Integer.parseInt(house.getResidentNumber());

        if (currentResidents + newResidents > maxResidents) {
            throw new IllegalArgumentException("Adding these residents would exceed the maximum number of residents allowed for this house");
        }

        for (AddFamilyMemberDTO addFamilyMemberDTO : addFamilyMemberDTOList) {
            User user = userRepository.findByEmail(addFamilyMemberDTO.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + addFamilyMemberDTO.getEmail()));

            if (user.getHouse() != null && user.getHouse().getId().equals(houseId)) {
                throw new IllegalArgumentException("User already exists in the same house");
            }

            Role defaultRole = roleRepository.findById("RESI")
                    .orElseThrow(() -> new IllegalArgumentException("Resident role not found"));

            user.getRoles().add(defaultRole);
            user.setHouse(house);
            user.setDui(addFamilyMemberDTO.getDui());
            userRepository.save(user);
        }
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
        House house = houseRepository.findById(houseId).orElseThrow(() -> new IllegalArgumentException("House Can(not) be found"));
        return house.getResidents().size() >= Integer.parseInt(house.getResidentNumber());
    }
}