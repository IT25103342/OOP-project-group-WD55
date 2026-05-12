package com.projectGroupWD55.bikeRentalAndRideSharing.repository;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User1, Long> {

    Optional<User1> findByEmail(String email);
}
