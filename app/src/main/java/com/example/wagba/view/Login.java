package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wagba.databinding.ActivityLoginBinding;
import com.example.wagba.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
//    private FirebaseAuth mAuth;
    Intent signUpInt, homeInt;
    public static Activity LA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        LA= this;
        signUpInt= new Intent(this, SignUpActivity.class);
        homeInt= new Intent(this, MainActivity.class);

        binding.include.cartImageView.setVisibility(View.INVISIBLE); //hide cart icon
        binding.include.menuImageView.setVisibility(View.GONE); //hide menu icon
        binding.include.appName.setPadding(50,0,0,0); //add padding

        loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                startActivity(homeInt);
                finish();
                return;
            }
        });

//        mAuth = FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser() != null){
//            finish();
//            return;
//        }


        binding.signUpPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signUpInt);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        String email= binding.loginEmail.getText().toString();
        String password= binding.loginPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        loginViewModel.login(email,password);

        /*mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(homeInt);
                            finish();
                            return;
                        } else {
                            Toast.makeText(Login.this, "email or password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

    }

}