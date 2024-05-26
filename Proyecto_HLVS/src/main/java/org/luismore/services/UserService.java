package org.luismore.services;

import org.luismore.entities.User;
import org.luismore.dto.UserDTO;

import java.util.List;

public interface UserService {
    User findById(int userId);
    User findByToken(String token);
    boolean isAdminOrMainResident(User user);
    boolean isMainResident(User user);
    List<User> findFamilyMembers(int houseId);
    void addFamilyMember(UserDTO userDTO, int houseId);
}