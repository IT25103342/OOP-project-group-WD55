package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class BikeRequestDTO {
    private Long bikeId;
    private String brand;
    private String model;
    private Double pricePerHour;
    private Double pricePerDay;
    private LocalDateTime createdDate;
    private String imageURL;
    private String location;
}
