package com.example.wagba.model;

public class CartItem {

    private String name;
    private String image;
    private int price;
    private String count;


    public CartItem() {
    }

    public CartItem(String name, String image, int price, String count) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.count = count;
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

    public String getCount() {
        return count;
    }
}
