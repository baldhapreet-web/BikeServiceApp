package com.example.bikeserviceapp;

public class User {

    public String name, phone, address;
    public String bikeBrand, bikeModel, bikeNumber;

    public User(){}

    public User(String name,String phone,String address,
                String bikeBrand,String bikeModel,String bikeNumber){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.bikeBrand = bikeBrand;
        this.bikeModel = bikeModel;
        this.bikeNumber = bikeNumber;
    }
}