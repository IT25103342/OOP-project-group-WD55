package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BikeStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikeResponseDTO {
    private Long bikeId;
    private String brand;
    private String model;
    private BikeStatus status;
    private Double pricePerHour;
    private Double pricePerDay;
    private String location;
    private String imageURL;
}
