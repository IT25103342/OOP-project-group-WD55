package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="passenger")
//id, ride (FK), passenger (FK→User), joinedAt
@Getter
@Setter
public class RidePassenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rides")
    private Ride ride;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="passengers")
    private User1 passenger;

    private LocalDateTime joinedAt;
}
