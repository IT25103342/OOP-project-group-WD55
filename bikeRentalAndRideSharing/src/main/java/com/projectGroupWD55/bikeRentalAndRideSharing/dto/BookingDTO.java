package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {
    private LocalDateTime bookingTime;
    private Long bookingId;
    private double price;
    private double distance;
}
