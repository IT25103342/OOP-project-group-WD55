package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.LoginDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UpdateProfileDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String USERS_FILE = "users.txt";

    public User registerUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getRole());
        User savedUser = userRepository.save(user);

        appendToTextFile(savedUser);

        return savedUser;
    }

    public Optional<User> login(LoginDTO dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(dto.getPassword())) {
            return userOpt;
        }

        return Optional.empty();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateProfile(Long id, UpdateProfileDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null) user.setPassword(dto.getPassword());

        User updatedUser = userRepository.save(user);

        rewriteTextFile();

        return updatedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private void appendToTextFile(User user) {
        try {
            FileWriter fw = new FileWriter(USERS_FILE, true);
            fw.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getRole() + "," + user.getCreatedAt() + "\n");
            fw.close();
        } catch (IOException e) {
            System.err.println("Error writing to users.txt: " + e.getMessage());
        }
    }

    private void rewriteTextFile() {
        List<User> users = userRepository.findAll();
        try {
            FileWriter fw = new FileWriter(USERS_FILE, false);
            for (User user : users) {
                fw.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getRole() + "," + user.getCreatedAt() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.err.println("Error rewriting users.txt: " + e.getMessage());
        }
    }
}
