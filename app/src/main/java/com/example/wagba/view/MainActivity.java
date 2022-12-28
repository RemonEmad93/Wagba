package com.example.wagba.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.wagba.R;
import com.example.wagba.databinding.ActivityMainBinding;
import com.example.wagba.view.Adapter.OrderHistoryAdapter;
import com.example.wagba.view.RecyclerViewInterface.RestaurantRecyclerViewInterface;
import com.example.wagba.model.RestaurantModel;
import com.example.wagba.view.Adapter.RestaurantAdapter;
import com.example.wagba.viewmodel.LogOutViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RestaurantRecyclerViewInterface {

    private ActivityMainBinding binding;
    private LogOutViewModel logOutViewModel;

    Intent loginInt, dishInt, cartInt, profileInt, ordersInt;

    FirebaseAuth auth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    DatabaseReference myRef;

    RestaurantAdapter restaurantAdapter;
    ArrayList<RestaurantModel> restaurantList;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    public static String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        cartInt= new Intent(this, CartActivity.class); //go to cart page
        profileInt= new Intent(this, ProfileActivity.class); //go to profile page
        ordersInt=new Intent(this, OrderHistoryActivity.class); //go to orders history page

        auth= FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if(currentUser == null){
            startLoginActivity();
            return;
        }

        currentUserEmail=auth.getCurrentUser().getEmail();


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        binding.RestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.RestaurantRecyclerView.setHasFixedSize(true);
        restaurantList= new ArrayList<>();
        restaurantAdapter= new RestaurantAdapter(this, restaurantList, this);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurantList.clear();
                for (DataSnapshot restaurants: snapshot.child("restaurants").getChildren()){
                    RestaurantModel restaurant= new RestaurantModel(restaurants.child("name").getValue(String.class),restaurants.child("logo").getValue().toString(),restaurants.child("num").getValue(String.class));
                    restaurantList.add(restaurant);
                }
                binding.RestaurantRecyclerView.setAdapter(restaurantAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.include.menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMenu(view);
            }
        });

        binding.include.cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(cartInt);
            }
        });

        logOutViewModel= new ViewModelProvider(this).get(LogOutViewModel.class);
        logOutViewModel.getLoggedOutMutualLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                startLoginActivity();
                return;
            }
        });

    }

    private void ShowMenu(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().toString().equals("Profile")){
                    startActivity(profileInt);
                }
                if(item.getTitle().toString().equals("Orders")){
                    startActivity(ordersInt);
                }
                if(item.getTitle().toString().equals("Logout")){
                    logout();
                }
                return true;
            }
        });

        popupMenu.show();
    }

    private void startLoginActivity(){
        loginInt=new Intent(this, LoginActivity.class);
        startActivity(loginInt);
        finish();
        return;
    }

    private void logout(){
        //delete sp when logout
        sp=getSharedPreferences("orderss",0);
        ed=sp.edit();
        ed.clear();
        ed.commit();
        logOutViewModel.logOut();
    }

    @Override
    public void onRestaurantClick(int position) {
        dishInt= new Intent(this, DishesActivity.class);
        dishInt.putExtra("place",restaurantList.get(position).getNum().toString());
        startActivity(dishInt);
    }
}