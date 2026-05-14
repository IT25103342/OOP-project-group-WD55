package com.projectGroupWD55.bikeRentalAndRideSharing.repository;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.bike.bikeId = :bikeId")
    Double findAverageRatingByBikeId(@Param("bikeId") Long bikeId);
    List<Review> findByBikeBikeId(Long bikeId);
    List<Review> findByUserId(Long userId);
    boolean existsByUserIdAndBookingId(Long userId, Long bookingId);
}
