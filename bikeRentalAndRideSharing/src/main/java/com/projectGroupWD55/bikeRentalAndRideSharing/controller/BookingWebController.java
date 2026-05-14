package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingWebController {

    private final BookingService bookingService;

    public BookingWebController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/user/{userId}")
    public String viewUserBookings(@PathVariable Long userId, Model model) {
        model.addAttribute("bookings", bookingService.getUserBookings(userId));
        model.addAttribute("userId", userId);
        return "bookings-list";
    }
    @GetMapping("/my-bookings")
    public String viewMyBookings(HttpSession session, Model model) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("bookings", bookingService.getUserBookings(user.getId()));
        return "bookings-list";
    }

    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, @RequestParam Long userId) {
        bookingService.cancelBooking(id);
        return "redirect:/bookings/user/" + userId;
    }
}