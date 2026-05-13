package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Booking;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
    public Booking findById(Long id) {
        if(bookingRepository.findById(id)==null) {
            throw new RuntimeException("Booking not found");
        }
        return bookingRepository.findById(id).orElse(null);
    }
    public Booking create(Booking booking) {
        Booking booking1 = new Booking();

        booking1.setId(booking.getId());
        booking1.setDistance(booking.getDistance());
        booking1.setPrice(booking.getPrice());
        booking1.setStartTime(booking.getStartTime());
        booking1.setEndTime(booking.getEndTime());
        booking1.setStatus("ACTIVE");

        return bookingRepository.save(booking1);
    }
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
    public List<Booking> findActiveRides() {
        return bookingRepository.findByStatus("ACTIVE");
    }
}
