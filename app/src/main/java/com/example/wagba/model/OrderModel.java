package com.example.wagba.model;

public class OrderModel {

    public String total_price;
    public String time; // can be delivery time or time the order placed
    public String gate;
    public String States;
    public String userEmail;

//    public OrderModel(String time, String states) {
//        this.time = time;
//        this.States = states;
//    }

    public OrderModel(String total_price, String time, String gate,String States,String userEmail) {
        this.total_price = total_price;
        this.time = time;
        this.gate = gate;
        this.States=States;
        this.userEmail=userEmail;
    }


}
