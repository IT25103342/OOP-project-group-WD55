package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BookingRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BookingResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.*;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.BikeRepository;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.BookingRepository;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BikeRepository bikeRepository;
    private final UserRepository userRepository;
    public BookingService(BookingRepository bookingRepository, BikeRepository bikeRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bikeRepository = bikeRepository;
        this.userRepository = userRepository;
    }
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {
        User1 user= userRepository.findById(bookingRequestDTO.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        Bike bike = bikeRepository.findById(bookingRequestDTO.getBikeId()).orElseThrow(()-> new RuntimeException("Bike not found"));
        if(bike.getStatus()!=BikeStatus.AVAILABLE){
            throw new RuntimeException("Bike not available");
        }
        long durationHours = ChronoUnit.HOURS.between(bookingRequestDTO.getStartTime(), bookingRequestDTO.getEndTime());
        Double totalPrice = bike.getPricePerHour() * durationHours;

        Booking newBooking = new Booking();
        newBooking.setUser(user);
        newBooking.setBike(bike);
        newBooking.setCreatedAt(LocalDateTime.now());
        newBooking.setStartTime(bookingRequestDTO.getStartTime());
        newBooking.setEndTime(bookingRequestDTO.getEndTime());
        newBooking.setTotalPrice(totalPrice);
        newBooking.setBookingStatus(BookingStatus.PENDING);
        newBooking.setDurationHours(durationHours);
        bookingRepository.save(newBooking);
        bike.setStatus(BikeStatus.RENTED);
        bikeRepository.save(bike);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setBookingStatus(BookingStatus.ACTIVE);


        bookingResponseDTO.setId(newBooking.getId());
        bookingResponseDTO.setUsername(user.getUsername());
        bookingResponseDTO.setBikeModel(bike.getModel());
        bookingResponseDTO.setStartTime(bookingRequestDTO.getStartTime());
        bookingResponseDTO.setEndTime(bookingRequestDTO.getEndTime());
        bookingResponseDTO.setTotalPrice(totalPrice);
        bookingResponseDTO.setBookingStatus(newBooking.getBookingStatus());
        bookingResponseDTO.setDurationHours(durationHours);

        return bookingResponseDTO;

    //id, userName, bikeModel, startTime, endTime, durationHours, totalPrice, status
    }
    public boolean checkAvailability(Long bikeId, LocalDateTime startTime, LocalDateTime endTime) {
        return !bookingRepository.existsByBikeIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                bikeId, BookingStatus.PENDING, endTime, startTime)
                && !bookingRepository.existsByBikeIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                bikeId, BookingStatus.ACTIVE, endTime, startTime);
    }
    public List<BookingResponseDTO> getUserBookings(Long user){
        List<BookingResponseDTO> bookingResponseDTOList = bookingRepository.findByUserId(user).stream().map(this::mapToResponse).collect(Collectors.toList());
        return bookingResponseDTOList;

    }
    public BookingResponseDTO updateBookingStatus(Long bookingId, BookingStatus bookingStatus){
        Booking booking =  bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking not found"));
        booking.setBookingStatus(bookingStatus);
        Booking saved=bookingRepository.save(booking);
        return mapToResponse(saved);
    }
    public BookingResponseDTO cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking not found"));
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        Bike bike = booking.getBike();
        bike.setStatus(BikeStatus.AVAILABLE);
        bikeRepository.save(bike);
        return mapToResponse(booking);
    }

    //code looked very and took so much time so made this
    private BookingResponseDTO mapToResponse(Booking booking) {
        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(booking.getId());
        response.setUsername(booking.getUser().getUsername());
        response.setBikeModel(booking.getBike().getModel());
        response.setStartTime(booking.getStartTime());
        response.setEndTime(booking.getEndTime());
        response.setDurationHours(booking.getDurationHours());
        response.setTotalPrice(booking.getTotalPrice());
        response.setBookingStatus(booking.getBookingStatus());
        return response;
    }

}

//updateBookingStatus(Long id, status) → change PENDING → ACTIVE etc.
//cancelBooking(Long id) → set CANCELLED, set bike back to AVAILABLE