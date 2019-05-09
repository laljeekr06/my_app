package com.example.bookkar;

import java.util.Date;

public class Booking {
    private String bookingId;
    private String bookingDate;
    private String consumerId;
    private String deliveryDate;
    private int amount;
    private String booking_status;

    public Booking(){

    }

    public Booking(String bookingId, String bookingDate, String consumerId, String deliveryDate, int amount, String booking_status) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.consumerId = consumerId;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
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
