package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Bookings")
public class Booking {
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private LocalDateTime  startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name ="bike_id")
    private Bike bike;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double distance;
    private double price;
    private String status;
}
