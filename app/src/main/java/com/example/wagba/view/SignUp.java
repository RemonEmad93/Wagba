package com.example.wagba.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wagba.User;
import com.example.wagba.databinding.ActivitySignUpBinding;
import com.example.wagba.viewmodel.SignUpViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        homeInt = new Intent(this, MainActivity.class);
        loginInt= new Intent(this, Login.class);

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

        binding.loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(loginInt);
                finish();
                Login.LA.finish();
                return;
            }
        });

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


        if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!email.contains("@eng.asu.edu.eg")){
            Toast.makeText(this, "Enter Valid email", Toast.LENGTH_SHORT).show();
            return;
        }

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