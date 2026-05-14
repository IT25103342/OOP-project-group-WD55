package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {
    private Long userId;
    private Long bookingId;
    private Long bikeId;
    private Integer rating;
    private String comment;
}
