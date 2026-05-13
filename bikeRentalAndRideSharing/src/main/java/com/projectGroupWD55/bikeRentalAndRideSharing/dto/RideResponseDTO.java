package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.RideStatus;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
//id, posterName, pickup, destination, rideTime, seatsAvailable, status, passengerCount
public class RideResponseDTO {
    private Long id;
    private String posterName;
    private String email;
    private String pickupLocation;
    private String destinationLocation;
    private LocalDateTime rideTime;
    private int seatsAvailable;
    private RideStatus status;
    private int passengerCount;
}
