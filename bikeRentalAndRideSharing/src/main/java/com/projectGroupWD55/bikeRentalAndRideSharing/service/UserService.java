package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserLogin;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserResponse registerUser(UserLogin userLogin) {
        User1 user = new User1();

        user.setUsername(userLogin.getUsername());
        user.setPassword(userLogin.getPassword());
        user.setEmail(userLogin.getEmail());
        user.setRole(userLogin.getRole());
        user.setCreatedDate(LocalDateTime.now());

        User1 savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();

        userResponse.setId(savedUser.getId());
        userResponse.setUsername(savedUser.getUsername());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setRole(savedUser.getRole());
        userResponse.setCreatedDate(savedUser.getCreatedDate());

        return userResponse;
    }
    public UserResponse verifyUser(UserLogin userLogin) {
        User1 user = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!user.getPassword().equals(userLogin.getPassword())) {
            throw new RuntimeException("Passwords don't match");
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setId(user.getId());

        return userResponse;
    }
    public User1 getuserById(Long id) {
        User1 user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return user;
    }
    public List<User1> getUserByDateCreated(LocalDateTime dateCreated) {
        List<User1> users = userRepository.findByCreatedDate(dateCreated);
        if(users.isEmpty()) {
            throw new RuntimeException("No users found");
        }
        return users;
    }
    public List<User1> findByRole(String role) {
        role = role.toUpperCase();
        return  userRepository.findByRole(role);
    }
    public List<User1> findAll() {
        return userRepository.findAll();
    }
    public UserResponse updateUser(Long id, UserLogin userLogin) {

        User1 user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userLogin.getUsername() != null) {
            user.setUsername(userLogin.getUsername());
        }
        if (userLogin.getEmail() != null) {
            user.setEmail(userLogin.getEmail());
        }
        if (userLogin.getPassword() != null) {
            user.setPassword(userLogin.getPassword());
        }
        if (userLogin.getRole() != null) {
            user.setRole(userLogin.getRole());
        }

        User1 savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setCreatedDate(savedUser.getCreatedDate());

        return response;
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
