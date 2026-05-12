package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserLogin;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserLogin userLogin) {
        return userService.registerUser(userLogin);
    }
    @PostMapping
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLogin userLogin) {
        return ResponseEntity.ok(userService.verifyUser(userLogin));
    }
    @GetMapping("/{id}")
    public User1 getUserById(Long id) {
        return userService.getuserById(id);
    }
    @DeleteMapping
    public void deleteUser(@RequestBody User1 user) {
        userService.deleteUser(user.getId());
    }
}
