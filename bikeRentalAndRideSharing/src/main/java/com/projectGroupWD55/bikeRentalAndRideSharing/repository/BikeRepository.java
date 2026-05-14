package com.projectGroupWD55.bikeRentalAndRideSharing.repository;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BikeResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Bike;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {
    List<Bike> findByStatus(BikeStatus status);
    List<Bike> findByLocation(String location);

}
