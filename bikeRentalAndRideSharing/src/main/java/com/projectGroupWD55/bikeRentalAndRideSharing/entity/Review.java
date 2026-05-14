package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// id, user (FK), bike (FK), booking (FK), rating, comment, createdAt, updatedAt
@Entity
@Getter
@Setter
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="users")
    private User1 user;

    @ManyToOne
    @JoinColumn(name="bike")
    private Bike bike;

    @ManyToOne
    @JoinColumn(name="bookings")
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
