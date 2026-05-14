package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BookingRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BookingResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BookingStatus;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BikeService;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BookingService;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    public BookingResponseDTO createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.createBooking(bookingRequestDTO);
    }
    @GetMapping("/user/{id}")
    public List<BookingResponseDTO> getBookingsByUser(@PathVariable Long id) {
        return bookingService.getUserBookings(id);
    }
    @GetMapping("/availabilty")
    public boolean checkAvailability(@RequestParam Long bikeId, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        return bookingService.checkAvailability(bikeId,startTime,endTime);
    }
    @PutMapping("/users/{id}/status")
    public BookingResponseDTO updateBookingStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return bookingService.updateBookingStatus(id,status);
    }
    @DeleteMapping
    public BookingResponseDTO deleteBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
}
