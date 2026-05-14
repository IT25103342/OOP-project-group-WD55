package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
//posterId, pickup, destination, rideTime, seatsAvailable
public class RideRequestDTO {
    private Long posterId;
    private String pickupLocation;
    private String destinationLocation;
    private LocalDateTime rideTime;
    private int seatsAvailable;
}
