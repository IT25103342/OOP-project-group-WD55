package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.ReviewRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.ReviewResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.*;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.BikeRepository;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.BookingRepository;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.ReviewRepository;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BikeRepository bikeRepository;
    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository, UserRepository userRepository, BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }
    public ReviewResponseDTO submitReview(ReviewRequestDTO reviewRequestDTO) {

        Booking booking = bookingRepository.findById(reviewRequestDTO.getBookingId()).orElseThrow(()-> new RuntimeException("dont exist"));
        if(booking.getBookingStatus()!= BookingStatus.COMPLETED){
            throw new IllegalStateException("You can only review completed bookings");
        }
        if (reviewRepository.existsByUserIdAndBookingId(reviewRequestDTO.getUserId(), reviewRequestDTO.getBookingId())) {
            throw new RuntimeException("You have already reviewed this booking");
        }
        if (reviewRequestDTO.getRating() < 1 || reviewRequestDTO.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        User1 user=userRepository.findById(reviewRequestDTO.getUserId()).orElseThrow(()-> new RuntimeException("dont exist"));
        review.setUser(user);
        review.setBike(bikeRepository.findById(reviewRequestDTO.getBikeId()).orElseThrow(()-> new RuntimeException("no bike")));
        review.setBooking(booking);
        review.setComment(reviewRequestDTO.getComment());
        review.setCreatedAt(LocalDateTime.now());
        review.setRating(reviewRequestDTO.getRating());
        review.setStatus(ReviewStatus.ORIGINAL);

        return maptoReviewResponseDTO(reviewRepository.save(review));
    }

    public List<ReviewResponseDTO> getReviewsByBike(Long bikeID){
        List<ReviewResponseDTO> list = reviewRepository.findByBikeBikeId(bikeID).stream().map(this::maptoReviewResponseDTO).collect(Collectors.toList());
        return list;
    }

    public Double getAverageRating(Long bikeID){
        return reviewRepository.findAverageRatingByBikeId(bikeID);
    }

    public ReviewResponseDTO editReview(Long id, ReviewRequestDTO reviewRequestDTO){
        Review toChange = reviewRepository.findById(id).orElseThrow(()-> new RuntimeException("review dont exist"));
        if (reviewRequestDTO.getRating() != null) {
            if (reviewRequestDTO.getRating() < 1 || reviewRequestDTO.getRating() > 5) {
                throw new RuntimeException("Rating must be between 1 and 5");
            }
            toChange.setRating(reviewRequestDTO.getRating());
        }

        if (reviewRequestDTO.getComment() != null) {
            toChange.setComment(reviewRequestDTO.getComment());
        }
        toChange.setStatus(ReviewStatus.EDITED);
        toChange.setUpdatedAt(LocalDateTime.now());
        return maptoReviewResponseDTO(reviewRepository.save(toChange));
    }

    public ReviewResponseDTO deleteReview(Long id){
        Review deleted = reviewRepository.findById(id).orElseThrow(()-> new RuntimeException("review dont exist or deleted"));
        reviewRepository.delete(deleted);
        return maptoReviewResponseDTO(deleted);
    }



    private ReviewResponseDTO maptoReviewResponseDTO(Review review){
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setComment(review.getComment());
        reviewResponseDTO.setRating(review.getRating());
        reviewResponseDTO.setUserName(review.getUser().getUsername());
        reviewResponseDTO.setBikeModel(review.getBike().getModel());
        reviewResponseDTO.setCreatedDate(review.getCreatedAt());
        return reviewResponseDTO;
    }

}
