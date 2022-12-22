package com.example.wagba.model;

public class User {
    public String username;
    public String email;
    public String password;
    public String phone_number;


    public User(){

    }

    public User(String username,String email,String password, String phone_number){
        this.username=username;
        this.email=email;
        this.password=password;
        this.phone_number=phone_number;
    }
}
