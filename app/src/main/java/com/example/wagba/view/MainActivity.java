package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.RestaurantFragment;
import com.example.wagba.databinding.ActivityMainBinding;
import com.example.wagba.view.Login;
import com.example.wagba.viewmodel.LogOutViewModel;
import com.example.wagba.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LogOutViewModel logOutViewModel;
    FirebaseAuth auth;
    FirebaseUser currentUser;
//    FirebaseDatabase database;
    Intent loginInt;
//    DatabaseReference myRef;

    TextView b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
        setContentView(R.layout.activity_main);

        replaceFragment(new RestaurantFragment());
/*
        logOutViewModel= new ViewModelProvider(this).get(LogOutViewModel.class);
        logOutViewModel.getLoggedOutMutualLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                startLoginActivity();
                return;
            }
        });*/

        auth= FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        /*if(currentUser == null){
            startLoginActivity();
            return;
        }*/

//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("testx");
//
//        myRef.setValue("database connected");

//        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });

        b1= findViewById(R.id.logoutButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "test test", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void replaceFragment(Fragment fragment) {
        Log.d("testfrag","here");
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    private void startLoginActivity(){
        loginInt=new Intent(this, Login.class);
        startActivity(loginInt);
        finish();
        return;
    }

    private void logout(){
        logOutViewModel.logOut();
    }
}