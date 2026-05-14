package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.RideRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.RideService;
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
        return "rides-list"; // This matches the HTML file name
    }

    @GetMapping("/new")
    public String showRideForm(Model model) {
        model.addAttribute("rideRequest", new RideRequestDTO());
        return "post-ride";
    }

    @PostMapping("/save")
    public String saveRide(@ModelAttribute("rideRequest") RideRequestDTO rideRequest) {
        rideService.postRide(rideRequest);
        return "redirect:/rides";
    }

    @PostMapping("/{id}/join")
    public String joinRide(@PathVariable Long id, @RequestParam Long passengerId) {
        rideService.joinRide(id, passengerId);
        return "redirect:/rides";
    }
}