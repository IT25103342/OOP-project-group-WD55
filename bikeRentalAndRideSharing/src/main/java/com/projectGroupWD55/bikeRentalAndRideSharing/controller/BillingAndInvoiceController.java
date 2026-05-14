package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BillingAndInvoice;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BillingAndInvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingAndInvoiceController {
    private final BillingAndInvoiceService service;

    public BillingAndInvoiceController(BillingAndInvoiceService service) {
        this.service = service;
    }

    @PostMapping("/generate/{bookingId}")
    public BillingAndInvoice create(@PathVariable Long bookingId) {
        return service.generateInvoice(bookingId);
    }

    @GetMapping("/history/{userId}")
    public List<BillingAndInvoice> getHistory(@PathVariable Long userId) {
        return service.getHistory(userId);
    }

    @PutMapping("/{id}/status")
    public BillingAndInvoice update(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
}