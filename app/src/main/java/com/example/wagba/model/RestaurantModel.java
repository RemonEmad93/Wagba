package com.example.wagba.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RestaurantModel {

    private String name;
    private String logo;
    private String num;

    public RestaurantModel() {
    }

    public RestaurantModel(String name, String logo, String num) {
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
