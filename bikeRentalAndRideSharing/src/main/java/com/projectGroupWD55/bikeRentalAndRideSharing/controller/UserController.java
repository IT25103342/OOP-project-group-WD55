package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserLogin;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserLogin userLogin) {
        return ResponseEntity.ok(userService.registerUser(userLogin));
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLogin userLogin) {
        return ResponseEntity.ok(userService.verifyUser(userLogin));
    }
    @GetMapping("/{id}")
    public User1 getUserById(@PathVariable Long id) {
        return userService.getuserById(id);
    }
    @GetMapping("/role/{role}")
    public List<User1> getUserByRole(@PathVariable String role) {
        return userService.findByRole(role);
    }
    @GetMapping
    public ResponseEntity<List<User1>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping
    public ResponseEntity<List<User1>> getUsersByDateCreated(@PathVariable LocalDateTime dateCreated) {
        return ResponseEntity.ok(userService.getUserByDateCreated(dateCreated));
    }
    @DeleteMapping
    public void deleteUser(@RequestBody User1 user) {
        userService.deleteUser(user.getId());
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserLogin userLogin) {
        return ResponseEntity.ok(userService.updateUser(id, userLogin));
    }
}
