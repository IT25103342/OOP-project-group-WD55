package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.RideDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Ride;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {
    @Autowired
    private RideService rideService;

    @PostMapping
    public Ride createRide(@RequestBody RideDTO dto) {
        return rideService.createRide(dto);
    }

    @GetMapping("/api/active")
    public List<Ride> getActiveRides(){
        return rideService.getActiveRides();
    }
    @DeleteMapping("/api/{id}")
    public void deleteRide(@PathVariable Long id){
        rideService.deleteRide(id);
    }
}
