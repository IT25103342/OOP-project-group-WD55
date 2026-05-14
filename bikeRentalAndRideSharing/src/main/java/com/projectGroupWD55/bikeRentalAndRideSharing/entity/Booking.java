package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "bookings")
public class Booking {
    @ManyToOne
    @JoinColumn(name="user_id")
    private User1 user;

    private LocalDateTime  startTime;
    private LocalDateTime endTime;
    private Long durationHours;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name ="bike_id")
    private Bike bike;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
