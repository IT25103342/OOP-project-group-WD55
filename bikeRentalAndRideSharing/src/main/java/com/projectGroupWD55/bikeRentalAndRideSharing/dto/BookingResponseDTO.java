package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingResponseDTO {
    private Long id;
    private String bikeModel;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String username;
    private Long durationHours;
    private Double totalPrice;
    private BookingStatus bookingStatus;
}
//id, userName, bikeModel, startTime, endTime, durationHours, totalPrice, status