package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.databinding.ActivityLoginBinding;
import com.example.wagba.databinding.ActivityMainBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    Intent loginInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loginInt= new Intent(this, SignUp.class);

        binding.signUpPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(loginInt);
            }
        });
    }

}