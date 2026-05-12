package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserLogin;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void registerUser(UserLogin userLogin) {
        User1 user = new User1();

        user.setUsername(userLogin.getUsername());
        user.setPassword(userLogin.getPassword());
        user.setEmail(userLogin.getEmail());
        user.setRole(userLogin.getRole());

        userRepository.save(user);
    }
    public UserResponse verifyUser(UserLogin userLogin) {
        User1 user = userRepository.findByEmail(userLogin.getEmail());
        if(!user.getEmail().equals(userLogin.getEmail())){
            throw new RuntimeException("User does not exist");
        }

        user.setUsername(userLogin.getUsername());
        user.setPassword(userLogin.getPassword());
        user.setEmail(userLogin.getEmail());
        user.setRole(userLogin.getRole());
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
        User1 user = userRepository.findById(id).get();
        return user;
    }
}
