package com.example.garage_app;

public class CourseModel {
    int img;
    String car_make, car_model;

    public CourseModel(int img, String car_make, String car_model)
    {
        this.car_make = car_make;
        this.car_model = car_model;
        this.img = img;
    }
}