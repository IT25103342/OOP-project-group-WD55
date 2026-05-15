package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BikeRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BikeService;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.RideService;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    private final UserService userService;
    private final BikeService bikeService;
    private final RideService rideService;

    public AdminWebController(UserService userService, BikeService bikeService, RideService rideService) {
        this.userService = userService;
        this.bikeService = bikeService;
        this.rideService = rideService;
    }

    // helper to block non-admins
    private boolean isAdmin(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        return user != null && "ADMIN".equals(user.getRole());
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("userCount", userService.findAll().size());
        model.addAttribute("bikeCount", bikeService.getAllBike().size());
        model.addAttribute("rideCount", rideService.getActiveRides().size());
        return "admin-dashboard";
    }

    @GetMapping("/users")
    public String manageUsers(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("users", userService.findAll());
        return "admin-users";
    }

    @GetMapping("/bikes")
    public String manageBikes(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("bikes", bikeService.getAllBike());
        model.addAttribute("newBike", new BikeRequestDTO());
        return "admin-bikes";
    }

    @PostMapping("/bikes/add")
    public String addBike(@ModelAttribute BikeRequestDTO newBike, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bikeService.addBike(newBike);
        return "redirect:/admin/bikes";
    }

    @GetMapping("/bikes/archive/{id}")
    public String archiveBike(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bikeService.archiveBike(id);
        return "redirect:/admin/bikes";
    }

    @GetMapping("/rides")
    public String manageRides(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("rides", rideService.getActiveRides());
        return "admin-rides";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}