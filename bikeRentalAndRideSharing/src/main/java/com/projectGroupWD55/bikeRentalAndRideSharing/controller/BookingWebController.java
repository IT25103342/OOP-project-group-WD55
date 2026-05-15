package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BookingRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BookingResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BikeResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BillingAndInvoice;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BookingStatus;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BikeService;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BillingAndInvoiceService;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/bookings")
public class BookingWebController {
    private final BikeService bikeService;
    private final BookingService bookingService;
    private final BillingAndInvoiceService billingService;

    public BookingWebController(BookingService bookingService, BikeService bikeService, BillingAndInvoiceService billingService) {
        this.bikeService = bikeService;
        this.billingService = billingService;
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

    @GetMapping("/rent/{bikeId}")
    public String showRentForm(@PathVariable Long bikeId, Model model, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        BikeResponseDTO bike = bikeService.getBikeById(bikeId);
        model.addAttribute("bike", bike);
        model.addAttribute("userId", user.getId());
        return "rent-bike";
    }

    @PostMapping("/rent")
    public String createBooking(@RequestParam Long userId,
                                @RequestParam Long bikeId,
                                @RequestParam String startTime,
                                @RequestParam String endTime,
                                HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        BookingRequestDTO request = new BookingRequestDTO();
        request.setUserId(userId);
        request.setBikeId(bikeId);
        request.setStartTime(LocalDateTime.parse(startTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        request.setEndTime(LocalDateTime.parse(endTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));

        BookingResponseDTO booking = bookingService.createBooking(request);
        BillingAndInvoice invoice = billingService.generateInvoice(booking.getId());

        return "redirect:/bookings/confirm/" + invoice.getId();
    }

    @GetMapping("/confirm/{invoiceId}")
    public String showConfirmation(@PathVariable Long invoiceId, Model model, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        BillingAndInvoice invoice = billingService.getInvoiceById(invoiceId);
        model.addAttribute("invoice", invoice);
        return "invoice-confirm";
    }

    @PostMapping("/confirm/{invoiceId}")
    public String confirmPayment(@PathVariable Long invoiceId,
                                 @RequestParam String paymentMethod,
                                 HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        billingService.updateStatus(invoiceId, "COMPLETED");
        bookingService.updateBookingStatus(
                billingService.getInvoiceById(invoiceId).getBooking().getId(),
                BookingStatus.ACTIVE
        );
        return "redirect:/bookings/my-bookings";
    }

    @GetMapping("/cancel-invoice/{invoiceId}")
    public String cancelFromConfirm(@PathVariable Long invoiceId, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        BillingAndInvoice invoice = billingService.getInvoiceById(invoiceId);
        bookingService.cancelBooking(invoice.getBooking().getId());
        billingService.updateStatus(invoiceId, "CANCELLED");
        return "redirect:/bikes";
    }
}