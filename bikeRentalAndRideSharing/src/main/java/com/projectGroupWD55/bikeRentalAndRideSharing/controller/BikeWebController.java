package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BikeResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bikes")
public class BikeWebController {

    private final BikeService bikeService;

    public BikeWebController(BikeService bikeService) {
        this.bikeService = bikeService;
    }
    @GetMapping
    public String listBikes(Model model, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<BikeResponseDTO> bikes = bikeService.getAllBikesAvailable();
        model.addAttribute("bikes", bikes);
        model.addAttribute("username", user.getUsername());
        return "bike-inventory";
    }
    @GetMapping("/{bikeId}")
    public String bikeDetails(@PathVariable Long bikeId, Model model, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        BikeResponseDTO bike = bikeService.getBikeById(bikeId);
        model.addAttribute("bike", bike);
        model.addAttribute("userId", user.getId());
        return "bike-details";
    }
}