package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.RideDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Ride;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {
    @Autowired
    private RideService rideService;

    @PostMapping
    public Ride createRide(@RequestBody RideDTO dto) {
        System.out.println("OBJECT: " + dto.toString());
        return rideService.createRide(dto);
    }

    @GetMapping("/active")
    public List<Ride> getActiveRides(){
        return rideService.getActiveRides();
    }
    @DeleteMapping("/{id}")
    public void deleteRide(@PathVariable Long id){
        rideService.deleteRide(id);
    }
}
