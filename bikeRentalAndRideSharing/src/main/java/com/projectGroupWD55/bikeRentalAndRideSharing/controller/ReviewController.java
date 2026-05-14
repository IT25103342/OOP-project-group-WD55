package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.ReviewRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.ReviewResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponseDTO submitReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        return reviewService.submitReview(reviewRequestDTO);
    }
    @GetMapping("/bike/{bikeId}")
    public List<ReviewResponseDTO> findByBikeId(@PathVariable Long bikeId) {
        return reviewService.getReviewsByBike(bikeId);
    }
    @GetMapping("/bike/{bikeId}/rating")
    public Double getAverageRating(@PathVariable Long bikeId) {
        return reviewService.getAverageRating(bikeId);
    }
    @PutMapping("/{id}")
    public ReviewResponseDTO updateReview(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        return reviewService.editReview(id, reviewRequestDTO);
    }
    @DeleteMapping("/{id}")
    public ReviewResponseDTO deleteReview(@PathVariable Long id) {
        return reviewService.deleteReview(id);
    }
}
