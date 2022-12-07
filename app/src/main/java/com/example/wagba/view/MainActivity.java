package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.Restaurant;
import com.example.wagba.RestaurantAdapter;
import com.example.wagba.databinding.ActivityMainBinding;
import com.example.wagba.viewmodel.LogOutViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LogOutViewModel logOutViewModel;
    FirebaseAuth auth;
    FirebaseUser currentUser;
//    FirebaseDatabase database;
    Intent loginInt;
//    DatabaseReference myRef;

//    RecyclerView recyclerView;
    RestaurantAdapter restaurantAdapter;
    ArrayList<Restaurant> restaurantList;



    TextView b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        logOutViewModel= new ViewModelProvider(this).get(LogOutViewModel.class);
        logOutViewModel.getLoggedOutMutualLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                startLoginActivity();
                return;
            }
        });

        auth= FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if(currentUser == null){
            startLoginActivity();
            return;
        }

//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("testx");
//
//        myRef.setValue("database connected");


        binding.RestaurantRecyclerView.setHasFixedSize(true);
        binding.RestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantList= new ArrayList<>();
        restaurantList.add(new Restaurant("test1",R.drawable.logo));
        restaurantList.add(new Restaurant("test2",R.drawable.logo));
        restaurantList.add(new Restaurant("test3",R.drawable.logo));
        restaurantList.add(new Restaurant("test4",R.drawable.logo));
        restaurantList.add(new Restaurant("test5",R.drawable.logo));
        restaurantList.add(new Restaurant("test6",R.drawable.logo));
        restaurantList.add(new Restaurant("test7",R.drawable.logo));
        restaurantList.add(new Restaurant("test8",R.drawable.logo));
        restaurantList.add(new Restaurant("test9",R.drawable.logo));
        restaurantList.add(new Restaurant("test10",R.drawable.logo));

        restaurantAdapter= new RestaurantAdapter(this, restaurantList);
        binding.RestaurantRecyclerView.setAdapter(restaurantAdapter);

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
        logOutViewModel.logOut();
    }
}