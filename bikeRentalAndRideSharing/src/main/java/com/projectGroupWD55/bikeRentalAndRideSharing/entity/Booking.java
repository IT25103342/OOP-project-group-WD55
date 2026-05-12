package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bookings")
public class Booking {
    private LocalDateTime  startTime;
    private LocalDateTime endTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
