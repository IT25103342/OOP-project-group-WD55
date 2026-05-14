package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Users")
public class User1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdDate;
    private String role;

    public User1(){}
    public User1(String username, String password, String email, String Role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role;
    }

}
