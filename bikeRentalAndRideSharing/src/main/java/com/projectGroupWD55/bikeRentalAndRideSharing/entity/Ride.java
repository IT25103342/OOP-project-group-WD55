package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickup;
    private String destination;

    private String pickupTime;

    private String status;
    private Long userId;

    public Ride(){}

    public Ride(String pickup, String destination, String pickupTime, String status, Long userID) {
        this.pickup = pickup;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.status = status;
        this.userId = userID;
    }

}
