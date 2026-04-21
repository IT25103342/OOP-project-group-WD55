package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

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

    /*@PostMapping
    public String test(@RequestBody String body) {
        System.out.println("RAW BODY: " + body);
        return body;
    }*/

    @PostMapping
    public Ride createRide(@RequestBody Ride ride) {
        System.out.println("OBJECT: " + ride);
        return rideService.createRide(ride);
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
