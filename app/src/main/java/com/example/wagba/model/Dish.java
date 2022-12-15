package com.example.wagba.model;

public class Dish {
    private Boolean availability;
    private String name;
    private String image;
    private int price;

    public Dish() {
    }

    public Dish( String name, String image, int price, Boolean availability) {
        this.availability = availability;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
