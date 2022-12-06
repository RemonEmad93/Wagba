package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.databinding.ActivityLoginBinding;
import com.example.wagba.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    Intent loginInt;
//    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth= FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if(currentUser == null){
            startLoginActivity();
            return;
        }

        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("testx");
//
//        myRef.setValue("database connected");

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


    }

    private void startLoginActivity(){
        loginInt=new Intent(this, Login.class);
        startActivity(loginInt);
        finish();
        return;
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startLoginActivity();
        return;

    }
}