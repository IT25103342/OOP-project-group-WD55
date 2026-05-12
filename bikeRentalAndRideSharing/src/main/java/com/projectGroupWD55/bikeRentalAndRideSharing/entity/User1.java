package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;

    public User1(){}
    public User1(String username, String password, String email, String Role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role;
    }

}
