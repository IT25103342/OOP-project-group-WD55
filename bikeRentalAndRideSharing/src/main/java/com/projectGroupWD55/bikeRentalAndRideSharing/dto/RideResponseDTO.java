package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.RideStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
    private List<String> passengerNames;
}
