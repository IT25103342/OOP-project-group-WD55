package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponseDTO {
    private Long id;
    private String userName;
    private String bikeModel;
    private Integer rating;
    private String comment;
    private LocalDateTime createdDate;

}
