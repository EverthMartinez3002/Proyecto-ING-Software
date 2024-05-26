package org.luismore.services.impls;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findByToken(String token) {
        // Ni puta idea como hacerlo INVESTIGAR
        return null;
    }

    @Override
    public boolean isAdminOrMainResident(User user) {
        return user.getRole().equals("ADMIN") || user.getRole().equals("MAIN_RESIDENT");
    }

    @Override
    public boolean isMainResident(User user) {
        return user.getRole().equals("MAIN_RESIDENT");
    }

    @Override
    public List<User> findFamilyMembers(int houseId) {
        return userRepository.findByHouseId(houseId);
    }

    @Override
    public void addFamilyMember(UserDTO userDTO, int houseId) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setHouseId(houseId);
        userRepository.save(user);
    }
}