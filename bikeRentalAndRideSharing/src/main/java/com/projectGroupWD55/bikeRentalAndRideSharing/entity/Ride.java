package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name="rides")
public class Ride {
    @Id              //this makes the spring booticus generate its own id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickup;
    private String destination;

    private LocalDateTime pickupTime;

    private String status;
    private Long userId;

    public Ride(){}

    public Ride(String pickup, String destination, LocalDateTime pickupTime, String status, Long userID) {
        this.pickup = pickup;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.status = status;
        this.userId = userID;
    }

}
