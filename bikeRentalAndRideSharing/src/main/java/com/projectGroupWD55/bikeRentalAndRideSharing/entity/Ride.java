package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="poster_id")
    private User1 poster;
    private String pickupLocation;
    private String destination;
    private LocalDateTime rideTime;
    private int seatsAvailable;
    private int passengerCount;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private LocalDateTime createdAt;
    private Double feePerSeat;



}
