package com.example.bookkar;

import java.util.Date;

public class Booking {
    private String bookingId;
    private Date bookingDate;
    private String customerId;
    private Date deliveryDate;
    private int amount;
    private String booking_status;

    public Booking(){

    }

    public Booking(String bookingId, Date bookingDate, String customerId, Date deliveryDate, int amount, String booking_status) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.customerId = customerId;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.booking_status = booking_status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }
}
