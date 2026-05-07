package com.example.bikerental.service;

import com.example.bikerental.model.Booking;
import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    
    Booking updateBooking(String bookingId, Booking booking);
    
    boolean checkAvailability(String bikeId, String startTime, String endTime, String excludeBookingId);
    
    List<Booking> getAllBookings();
    
    Booking getBookingById(String bookingId);
    
    boolean cancelBooking(String bookingId);
}
