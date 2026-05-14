package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RideDTO {

    private String pickup;
    private String destination;
    private LocalDateTime pickupTime;
    private Long userId;
}
