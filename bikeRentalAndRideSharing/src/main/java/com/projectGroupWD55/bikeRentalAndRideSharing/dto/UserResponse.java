package com.projectGroupWD55.bikeRentalAndRideSharing.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse{
    private String username;
    private String email;
    private String role;
    private Long id;
    private LocalDateTime createdDate;
}
