package com.example.bikerental.controller;

import com.example.bikerental.model.Booking;
import com.example.bikerental.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String getAllBookings(@RequestParam(required = false) String bookingId, Model model) {
        List<Booking> bookings;
        
        if (bookingId != null && !bookingId.trim().isEmpty()) {
            // Search for specific booking
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking != null) {
                bookings = new ArrayList<>();
                bookings.add(booking);
                model.addAttribute("searchQuery", bookingId);
            } else {
                bookings = new ArrayList<>();
                model.addAttribute("errorMessage", "Booking with ID '" + bookingId + "' not found!");
                model.addAttribute("searchQuery", bookingId);
            }
        } else {
            // Show all bookings
            bookings = bookingService.getAllBookings();
        }
        
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "create-booking";
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute Booking booking, RedirectAttributes redirectAttributes) {
        if (!bookingService.checkAvailability(booking.getBikeId(), booking.getStartTime(), booking.getEndTime(), null)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bike is not available for the selected time period!");
            return "redirect:/bookings/create";
        }

        Booking createdBooking = bookingService.createBooking(booking);
        if (createdBooking != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Booking created successfully!");
            return "redirect:/bookings";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create booking!");
            return "redirect:/bookings/create";
        }
    }

    @GetMapping("/update/{bookingId}")
    public String showUpdateForm(@PathVariable String bookingId, Model model, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking != null) {
            model.addAttribute("booking", booking);
            return "update-booking";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Booking not found!");
        return "redirect:/bookings";
    }

    @PostMapping("/update/{bookingId}")
    public String updateBooking(@PathVariable String bookingId, @ModelAttribute Booking booking, RedirectAttributes redirectAttributes) {
        Booking existingBooking = bookingService.getBookingById(bookingId);
        if (existingBooking == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Booking not found!");
            return "redirect:/bookings";
        }

        if (!existingBooking.getBikeId().equals(booking.getBikeId()) ||
            !existingBooking.getStartTime().equals(booking.getStartTime()) ||
            !existingBooking.getEndTime().equals(booking.getEndTime())) {
            if (!bookingService.checkAvailability(booking.getBikeId(), booking.getStartTime(), booking.getEndTime(), bookingId)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Bike is not available for the selected time period!");
                return "redirect:/bookings/update/" + bookingId;
            }
        }

        Booking updatedBooking = bookingService.updateBooking(bookingId, booking);
        if (updatedBooking != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Booking updated successfully!");
            return "redirect:/bookings";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update booking!");
            return "redirect:/bookings/update/" + bookingId;
        }
    }

    @GetMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable String bookingId, RedirectAttributes redirectAttributes) {
        if (bookingService.cancelBooking(bookingId)) {
            redirectAttributes.addFlashAttribute("successMessage", "Booking deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete booking!");
        }
        return "redirect:/bookings";
    }

    @GetMapping("/check")
    public String showCheckAvailabilityForm(
            @RequestParam(required = false) String bikeId, 
            @RequestParam(required = false) String startTime, 
            @RequestParam(required = false) String endTime, 
            Model model) {
        
        // If no parameters, just show the form
        if (bikeId == null || startTime == null || endTime == null) {
            return "check-availability";
        }
        
        // Check availability with the provided parameters
        boolean available = bookingService.checkAvailability(bikeId, startTime, endTime, null);
        model.addAttribute("bikeId", bikeId);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("available", available);
        model.addAttribute("messageType", available ? "success" : "danger");
        model.addAttribute("message", available ? "Bike is available!" : "Bike is not available!");
        return "check-availability";
    }
}
