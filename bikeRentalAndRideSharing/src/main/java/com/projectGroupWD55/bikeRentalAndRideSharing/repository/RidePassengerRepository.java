package com.projectGroupWD55.bikeRentalAndRideSharing.repository;


import com.projectGroupWD55.bikeRentalAndRideSharing.entity.RidePassenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RidePassengerRepository extends JpaRepository<RidePassenger, Long> {
    List<RidePassenger> findByRideId(Long rideId);
    boolean existsByRideIdAndPassengerId(Long rideId, Long passengerId);
}
