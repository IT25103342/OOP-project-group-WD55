package com.projectGroupWD55.bikeRentalAndRideSharing.repository;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Ride;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByStatus(RideStatus status);
    List<Ride> findByPosterId(Long userId);
    List<Ride> findByPickupAndDestination(String pickup, String destination);

}
