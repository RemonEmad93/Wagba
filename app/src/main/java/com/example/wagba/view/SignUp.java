package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wagba.databinding.ActivitySignUpBinding;
import com.example.wagba.viewmodel.SignUpViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private SignUpViewModel signUpViewModel;
//    private FirebaseAuth mAuth;
//    FirebaseDatabase database;
//    DatabaseReference myRef;
    Intent homeInt,loginInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        homeInt = new Intent(this, MainActivity.class);  //go to mainActivity page
        loginInt= new Intent(this, Login.class);         //go to login page

        binding.include.cartImageView.setVisibility(View.INVISIBLE); //hide cart icon
        binding.include.menuImageView.setVisibility(View.GONE); //hide menu icon
        binding.include.appName.setPadding(50,0,0,0);

        //when user signUp go to mainActivity page and destroy the sign up/in activity
        signUpViewModel= new ViewModelProvider(this).get(SignUpViewModel.class);
        signUpViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                startActivity(homeInt);
                finish();
                Login.LA.finish();
                return;
            }
        });
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("users");
//        mAuth = FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser() != null){
//            finish();
//            return;
//        }

        //if user returns to login page then destroy the sign up and the opened login activity
        binding.loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(loginInt);
                finish();
                Login.LA.finish();
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


        //check that user entered all data
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }



        //pass the entered data to viewModel
        signUpViewModel.signUp(username, email, password);

        /*mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user= new User(username,email,password);
                            myRef.child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            startActivity(homeInt);
                                            finish();
                                            Login.LA.finish();
                                            return;
                                        }
                                    });
                        } else {
                            Toast.makeText(SignUp.this, "Registration failed, try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }
}