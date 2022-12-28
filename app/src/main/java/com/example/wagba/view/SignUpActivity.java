package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wagba.databinding.ActivitySignUpBinding;
import com.example.wagba.model.DatabaseModel;
import com.example.wagba.viewmodel.DatabaseViewModel;
import com.example.wagba.viewmodel.SignUpViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private SignUpViewModel signUpViewModel;
    Intent homeInt,loginInt;
    private DatabaseModel databaseModel;
    private DatabaseViewModel databaseViewModel;
//    public static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        homeInt = new Intent(this, MainActivity.class);  //go to mainActivity page
        loginInt= new Intent(this, LoginActivity.class); //go to login page

        binding.include.cartImageView.setVisibility(View.INVISIBLE); //hide cart icon
        binding.include.menuImageView.setVisibility(View.GONE); //hide menu icon
        binding.include.appName.setPadding(50,0,0,0); //add padding

        //when user signUp go to mainActivity page and destroy the sign up/in activity
        signUpViewModel= new ViewModelProvider(this).get(SignUpViewModel.class);
        signUpViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                startActivity(homeInt);
                finish();
                LoginActivity.LA.finish();
                return;
            }
        });

        //if user returns to login page then destroy the sign up and the opened login activity
        binding.loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(loginInt);
                finish();
                LoginActivity.LA.finish();
                return;
            }
        });

        //sign up button
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }


    private void registerUser(){
        String username= binding.signUpUsername.getText().toString();
        String email= binding.signUpEmail.getText().toString();
        String password= binding.signUpPassword.getText().toString();
        String phone_number=binding.editTextPhone.getText().toString();

        //check that user entered all data
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone_number.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //insert user data into room database
        databaseModel= new DatabaseModel(username,email,phone_number);
        databaseViewModel= new ViewModelProvider(this).get(DatabaseViewModel.class);
        databaseViewModel.insertProfile(databaseModel);

        //pass the entered data to viewModel to insert data in firebase database
        signUpViewModel.signUp(username, email, password, phone_number);

//        userEmail=email;
//        Log.d("plzdata",userEmail);
    }
}