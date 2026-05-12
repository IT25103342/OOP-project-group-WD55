package com.projectGroupWD55.bikeRentalAndRideSharing.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User1 {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String Role;

    public User1(){}
    public User1(String username, String password, String email, String Role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.Role = Role;
    }
    public String getEmail(){
        return email;
    }

}
