package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name="Bikes")
@Getter
@Setter
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bikeId;

    private String brand;
    private String model;
    private String location;
    private String imageURL;
    @Enumerated(EnumType.STRING)
    private BikeStatus status;


    private LocalDateTime createdDate;

    private Double pricePerHour;
    private Double pricePerDay;

    public Bike() {}
    public Bike(String brand,String model,BikeStatus status,String location,String imageURL,Double pricePerHour,Double pricePerDay,LocalDateTime createdDate) {
        this.brand=brand;
        this.model=model;
        this.status=status;
        this.location =location;
        this.imageURL=imageURL;
        this.pricePerHour=pricePerHour;
        this.pricePerDay=pricePerDay;
        this.createdDate=createdDate;
    }
}
