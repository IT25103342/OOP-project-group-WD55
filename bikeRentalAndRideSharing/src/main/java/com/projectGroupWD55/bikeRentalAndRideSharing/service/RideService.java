package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Ride;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {
    @Autowired
    private RideRepository riderepository;
    public Ride createRide(Ride ride){
        return riderepository.save(ride);
    }

    public List<Ride> getAllRides(){
        return riderepository.findAll();
    }
    public List<Ride> getActiveRides(){
        return riderepository.findByStatus("ACTIVE");
    }
    public void deleteRide(Long id){
        riderepository.deleteById(id);
    }
}
