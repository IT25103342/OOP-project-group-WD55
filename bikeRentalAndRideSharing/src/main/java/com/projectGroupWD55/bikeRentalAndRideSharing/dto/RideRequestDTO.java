package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RideRequestDTO {
    private Long posterId;
    private String pickupLocation;
    private String destinationLocation;
    private LocalDateTime rideTime;
    private int seatsAvailable;
}
