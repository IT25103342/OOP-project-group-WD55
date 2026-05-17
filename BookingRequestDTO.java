package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequestDTO {
    private Long userId;
    private Long bikeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
