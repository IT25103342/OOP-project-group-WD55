package com.projectGroupWD55.bikeRentalAndRideSharing.repository;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);
    List<Booking> findByStatus(String status);
}
