package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
public class RideRequest {
    @Setter
    @Getter
    private String pickup;
    @Getter
    @Setter
    private String destination;
    @Getter
    @Setter
    private LocalTime pickupTime;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private long userID;
    public RideRequest(String pickup, String destination, LocalTime pickupTime, String status, long userID) {
        this.pickup = pickup;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.status = status;
        this.userID = userID;
    }

}
