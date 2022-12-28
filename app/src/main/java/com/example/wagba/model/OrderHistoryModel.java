package com.example.wagba.model;

import java.util.ArrayList;

public class OrderHistoryModel {
    public String time;
    public String States;
//    public ArrayList<CartItemModel> cartItemModelArrayList;

    public OrderHistoryModel(String time, String states) {
        this.time = time;
        this.States = states;
    }

    public String getTime() {
    return time;
    }

    public String getStates() {
        return States;
    }

//    public ArrayList<CartItemModel> cartItemModelArrayList() {
//        return cartItemModelArrayList;
//    }
}
