package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.RideRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.RideService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rides")
public class RideWebController {

    private final RideService rideService;

    public RideWebController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping
    public String viewRides(Model model) {
        model.addAttribute("rides", rideService.getActiveRides());
        return "rides-list";
    }

    @GetMapping("/new")
    public String showRideForm(Model model, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("rideRequest", new RideRequestDTO());
        return "post-ride";
    }

    @PostMapping("/save")
    public String saveRide(@ModelAttribute RideRequestDTO rideRequest, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        rideRequest.setPosterId(user.getId());
        rideService.postRide(rideRequest);
        return "redirect:/rides";
    }

    @PostMapping("/{id}/join")
    public String joinRide(@PathVariable Long id, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        rideService.joinRide(id, user.getId());
        return "redirect:/rides";
    }
}