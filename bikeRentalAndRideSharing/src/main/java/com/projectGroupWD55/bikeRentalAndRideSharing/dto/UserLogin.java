package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin{
    private String username;
    private String password;
    private String email;
    private String role;
}
