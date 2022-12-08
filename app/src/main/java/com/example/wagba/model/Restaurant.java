package com.example.wagba.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Restaurant {

//    private String restaurantName;
//    private int restaurantImage;
//
//    public Restaurant(String restaurantName) {
//        this.restaurantName = restaurantName;
////        this.restaurantImage = restaurantImage;
//    }
//
//
//    public String getRestaurantName() {
//        return restaurantName;
//    }
//
//    public int getRestaurantImage() {
//        return restaurantImage;
//    }

    private String name;

    public Restaurant(String name) {
        this.name = name;
    }

    public String getRestaurantName() {
        return name;
    }

}
