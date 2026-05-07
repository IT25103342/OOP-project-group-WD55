package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RideDTO {

    private String pickup;
    private String destination;
    private String pickupTime;
    private Long userId;
}
