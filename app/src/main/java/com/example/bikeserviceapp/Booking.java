package com.example.bikeserviceapp;

public class Booking {

    public String bookingId;
    public String bikeModel;
    public String serviceType;
    public String address;
    public String phone;
    public String pickupTime;
    public String deliveryTime;   // ✅ ADD THIS
    public String status;
    public String userId;

    public Booking(){}

    public Booking(String bikeModel, String serviceType,
                   String address, String phone,
                   String pickupTime,
                   String status, String userId){

        this.bikeModel = bikeModel;
        this.serviceType = serviceType;
        this.address = address;
        this.phone = phone;
        this.pickupTime = pickupTime;
        this.status = status;
        this.userId = userId;
    }



}