package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.ReviewRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewWebController {

    private final ReviewService reviewService;

    public ReviewWebController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/bike/{bikeId}")
    public String viewBikeReviews(@PathVariable Long bikeId, Model model) {
        model.addAttribute("reviews", reviewService.getReviewsByBike(bikeId));
        model.addAttribute("averageRating", reviewService.getAverageRating(bikeId));
        model.addAttribute("bikeId", bikeId);

        ReviewRequestDTO reviewRequest = new ReviewRequestDTO();
        reviewRequest.setBikeId(bikeId);
        model.addAttribute("reviewRequest", reviewRequest);

        return "bike-reviews";
    }

    @PostMapping("/submit")
    public String submitReview(@ModelAttribute("reviewRequest") ReviewRequestDTO reviewRequest) {
        try {
            reviewService.submitReview(reviewRequest);
            return "redirect:/reviews/bike/" + reviewRequest.getBikeId();
        } catch (Exception e) {
            return "redirect:/reviews/bike/" + reviewRequest.getBikeId() + "?error=true";
        }
    }
}
