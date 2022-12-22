package com.example.wagba.model;

public class UserModel {
    public String username;
    public String email;
    public String password;
    public String phone_number;


    public UserModel(){

    }

    public UserModel(String username, String email, String password, String phone_number){
        this.username=username;
        this.email=email;
        this.password=password;
        this.phone_number=phone_number;
    }
}
