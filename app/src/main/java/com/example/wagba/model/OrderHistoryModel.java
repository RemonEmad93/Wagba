package com.example.wagba.model;

public class OrderHistoryModel {
    public String time;
    public String States;

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
}
