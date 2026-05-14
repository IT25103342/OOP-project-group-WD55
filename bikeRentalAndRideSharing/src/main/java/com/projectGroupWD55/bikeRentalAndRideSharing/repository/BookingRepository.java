package com.projectGroupWD55.bikeRentalAndRideSharing.repository;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Booking;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByBikeIdAndStatus(Long bikeId, BookingStatus status);
    boolean existsByBikeIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
            Long bikeId, BookingStatus status, LocalDateTime end, LocalDateTime start);
}
