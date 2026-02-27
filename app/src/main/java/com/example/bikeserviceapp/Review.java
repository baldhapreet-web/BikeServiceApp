package com.example.bikeserviceapp;

public class Review {

    public String reviewId;
    public String bookingId;
    public String userId;
    public String userName;   // ⭐ NEW
    public String bikeModel;
    public float rating;
    public String comment;

    public Review(){}

    public Review(String reviewId,String bookingId,
                  String userId,String userName,
                  String bikeModel,float rating,String comment){

        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.userName = userName;
        this.bikeModel = bikeModel;
        this.rating = rating;
        this.comment = comment;
    }
}