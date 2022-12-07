package com.example.wagba;

public class Restaurant {

    String restaurantName;
    int restaurantImage;

    public Restaurant(String restaurantName, int restaurantImage) {
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantImage(int restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getRestaurantImage() {
        return restaurantImage;
    }
}
