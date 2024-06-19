//package org.luismore.hlvsapi.services;
//
//import org.luismore.hlvsapi.domain.dtos.AddFamilyMemberDTO;
//import org.luismore.hlvsapi.domain.entities.User;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface FamilyService {
//    List<User> getFamilyMembers(UUID houseId);
//    void addFamilyMember(UUID houseId, AddFamilyMemberDTO addFamilyMemberDTO);
//    boolean isHouseFull(UUID houseId);
//}

package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.AddFamilyMemberDTO;
import org.luismore.hlvsapi.domain.dtos.UserDTO;

import java.util.List;
import java.util.UUID;

public interface FamilyService {
    List<UserDTO> getFamilyMembers(UUID houseId);
    void addFamilyMember(UUID houseId, List<AddFamilyMemberDTO> addFamilyMemberDTOList);
    boolean isHouseFull(UUID houseId);
}
