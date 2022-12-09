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
    private String logo;
    private String num;

    public Restaurant() {
    }

    public Restaurant(String name, String logo, String num) {
        this.name = name;
        this.logo=logo;
        this.num=num;
    }

    public String getRestaurantName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getNum() {
        return num;
    }
}
