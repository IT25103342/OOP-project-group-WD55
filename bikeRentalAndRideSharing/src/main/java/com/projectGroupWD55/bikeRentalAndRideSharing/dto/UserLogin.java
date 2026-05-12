package com.projectGroupWD55.bikeRentalAndRideSharing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin {
    private String username;
    private String password;
    private String email;
    private String role;
}
