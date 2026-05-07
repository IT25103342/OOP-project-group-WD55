package com.example.bikerental.model;

public class Booking extends Reservation {
    private static final long serialVersionUID = 1L;

    private String bookingId;
    private String status;

    public Booking() {
        super();
    }

    public Booking(String bookingId, String userId, String bikeId, String startTime, String endTime, String status) {
        super(userId, bikeId, startTime, endTime);
        this.bookingId = bookingId;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return bookingId + "|" + getUserId() + "|" + getBikeId() + "|" + 
               getStartTime() + "|" + getEndTime() + "|" + status;
    }

    public static Booking fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 6) {
            return new Booking(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }
}
