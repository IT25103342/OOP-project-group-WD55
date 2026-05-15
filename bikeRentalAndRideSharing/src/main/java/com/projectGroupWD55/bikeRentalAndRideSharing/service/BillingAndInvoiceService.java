package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Booking;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BillingAndInvoice;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.BookingRepository;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.BillingAndInvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillingAndInvoiceService {
    private final BillingAndInvoiceRepository repository;
    private final BookingRepository bookingRepository;

    public BillingAndInvoiceService(BillingAndInvoiceRepository repository, BookingRepository bookingRepository) {
        this.repository = repository;
        this.bookingRepository = bookingRepository;
    }

    public BillingAndInvoice generateInvoice(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BillingAndInvoice invoice = new BillingAndInvoice();
        invoice.setBooking(booking);
        invoice.setUser(booking.getUser());
        invoice.setAmount(booking.getTotalPrice());
        invoice.setPaymentDate(LocalDateTime.now());
        invoice.setStatus("UNPAID");

        return repository.save(invoice);
    }

    public List<BillingAndInvoice> getHistory(Long userId) {
        return repository.findByUserId(userId);
    }

    public BillingAndInvoice updateStatus(Long id, String status) {
        BillingAndInvoice invoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        invoice.setStatus(status.toUpperCase());
        return repository.save(invoice);
    }
    @Transactional
    public BillingAndInvoice getInvoiceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

}