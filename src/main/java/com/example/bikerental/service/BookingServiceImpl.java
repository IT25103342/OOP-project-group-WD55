package com.example.bikerental.service;

import com.example.bikerental.model.Booking;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {
    
    private static final String DATA_FILE = "src/main/resources/data/bookings.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final Object FILE_LOCK = new Object();
    
    // Helper method to parse datetime from either format
    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            // Try ISO format first (from datetime-local input)
            if (dateTimeString.contains("T")) {
                return LocalDateTime.parse(dateTimeString, ISO_FORMATTER);
            }
            // Fall back to stored format
            return LocalDateTime.parse(dateTimeString, DATE_FORMATTER);
        } catch (Exception e) {
            // If both fail, return null
            return null;
        }
    }
    
    // Helper method to format datetime for storage
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    @Override
    public Booking createBooking(Booking booking) {
        synchronized (FILE_LOCK) {
            if (booking.getBookingId() == null || booking.getBookingId().isEmpty()) {
                booking.setBookingId(generateBookingId());
            }
            
            if (booking.getStatus() == null || booking.getStatus().isEmpty()) {
                booking.setStatus("Pending");
            }
            
            // Convert datetime format if needed
            LocalDateTime startDt = parseDateTime(booking.getStartTime());
            LocalDateTime endDt = parseDateTime(booking.getEndTime());
            if (startDt != null) {
                booking.setStartTime(formatDateTime(startDt));
            }
            if (endDt != null) {
                booking.setEndTime(formatDateTime(endDt));
            }

            try {
                List<String> lines = readAllLines();
                lines.add(booking.toString());
                writeAllLines(lines);
                return booking;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Booking updateBooking(String bookingId, Booking updatedBooking) {
        synchronized (FILE_LOCK) {
            try {
                // Convert datetime format if needed
                LocalDateTime startDt = parseDateTime(updatedBooking.getStartTime());
                LocalDateTime endDt = parseDateTime(updatedBooking.getEndTime());
                if (startDt != null) {
                    updatedBooking.setStartTime(formatDateTime(startDt));
                }
                if (endDt != null) {
                    updatedBooking.setEndTime(formatDateTime(endDt));
                }
                
                List<String> lines = readAllLines();
                List<String> updatedLines = new ArrayList<>();
                boolean found = false;

                for (String line : lines) {
                    Booking booking = Booking.fromString(line);
                    if (booking != null && booking.getBookingId().equals(bookingId)) {
                        updatedBooking.setBookingId(bookingId);
                        updatedLines.add(updatedBooking.toString());
                        found = true;
                    } else {
                        updatedLines.add(line);
                    }
                }

                if (found) {
                    writeAllLines(updatedLines);
                    return updatedBooking;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean checkAvailability(String bikeId, String startTime, String endTime, String excludeBookingId) {
        synchronized (FILE_LOCK) {
            try {
                List<String> lines = readAllLines();
                LocalDateTime newStart = parseDateTime(startTime);
                LocalDateTime newEnd = parseDateTime(endTime);
                
                if (newStart == null || newEnd == null) {
                    return false;
                }

                for (String line : lines) {
                    Booking booking = Booking.fromString(line);
                    if (booking != null && booking.getBikeId().equals(bikeId)) {
                        if (excludeBookingId != null && booking.getBookingId().equals(excludeBookingId)) {
                            continue;
                        }
                        
                        if (!booking.getStatus().equals("Cancelled")) {
                            LocalDateTime existingStart = parseDateTime(booking.getStartTime());
                            LocalDateTime existingEnd = parseDateTime(booking.getEndTime());
                            
                            if (existingStart != null && existingEnd != null) {
                                if (!(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd))) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public List<Booking> getAllBookings() {
        synchronized (FILE_LOCK) {
            List<Booking> bookings = new ArrayList<>();
            try {
                List<String> lines = readAllLines();
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        Booking booking = Booking.fromString(line);
                        if (booking != null) {
                            bookings.add(booking);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bookings;
        }
    }

    @Override
    public Booking getBookingById(String bookingId) {
        synchronized (FILE_LOCK) {
            try {
                List<String> lines = readAllLines();
                for (String line : lines) {
                    Booking booking = Booking.fromString(line);
                    if (booking != null && booking.getBookingId().equals(bookingId)) {
                        return booking;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        synchronized (FILE_LOCK) {
            try {
                List<String> lines = readAllLines();
                List<String> updatedLines = new ArrayList<>();
                boolean found = false;

                for (String line : lines) {
                    Booking booking = Booking.fromString(line);
                    if (booking != null && booking.getBookingId().equals(bookingId)) {
                        found = true;
                        // Skip this line (delete it)
                    } else {
                        updatedLines.add(line);
                    }
                }

                if (found) {
                    writeAllLines(updatedLines);
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private List<String> readAllLines() throws IOException {
        Path path = Paths.get(DATA_FILE);
        if (Files.exists(path)) {
            return Files.readAllLines(path);
        }
        return new ArrayList<>();
    }

    private void writeAllLines(List<String> lines) throws IOException {
        Path path = Paths.get(DATA_FILE);
        Files.createDirectories(path.getParent());
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String generateBookingId() {
        return "BK" + System.currentTimeMillis();
    }
}
