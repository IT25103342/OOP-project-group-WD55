package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.LoginDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UpdateProfileDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO dto) {
        try {
            User user = userService.registerUser(dto);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Optional<User> user = userService.login(dto);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid email or password"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody UpdateProfileDTO dto) {
        try {
            User updatedUser = userService.updateProfile(id, dto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
