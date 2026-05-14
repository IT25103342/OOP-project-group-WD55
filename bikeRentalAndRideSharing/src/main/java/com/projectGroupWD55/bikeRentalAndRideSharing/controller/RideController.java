package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.RideRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.RideResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.RideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {
    private RideService rideService;
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }
    @PostMapping
    public RideResponseDTO postRide(@RequestBody RideRequestDTO rideRequestDTO) {
        return rideService.postRide(rideRequestDTO);
    }
    @GetMapping("/active")
    public List<RideResponseDTO> getActiveRides() {
        return rideService.getActiveRides();
    }
    @GetMapping("/search")
    public List<RideResponseDTO> searchRides(@RequestParam String pickup, @RequestParam String destination) {
        return rideService.searchRides(pickup, destination);
    }
    @PostMapping("/{id}/join")
    public RideResponseDTO joinRide(@PathVariable Long id, @RequestParam Long passengerId) {
        return rideService.joinRide(id,passengerId);
    }
    @DeleteMapping("/{id}")
    public RideResponseDTO cancelRide(@PathVariable Long id, @RequestParam Long userId) {
        return rideService.cancelRide(id,userId);
    }
}