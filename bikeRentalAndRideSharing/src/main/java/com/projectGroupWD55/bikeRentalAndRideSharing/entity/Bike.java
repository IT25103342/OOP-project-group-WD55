package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name ="Bikes")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rideId;
    private String bikeType;
    private String status;


    public Bike() {}
    public Bike(int rideId, String bikeType, String status) {
        this.rideId = rideId;
        this.bikeType = bikeType;
        this.status = status;
    }
}
