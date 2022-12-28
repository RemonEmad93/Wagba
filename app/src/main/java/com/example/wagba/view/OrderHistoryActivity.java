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
import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.databinding.ActivityOrderHistoryBinding;
import com.example.wagba.model.CartItemModel;
import com.example.wagba.model.DishModel;
import com.example.wagba.model.OrderHistoryModel;
import com.example.wagba.model.OrderModel;
import com.example.wagba.view.Adapter.CartAdapter;
import com.example.wagba.view.Adapter.OrderHistoryAdapter;
import com.example.wagba.viewmodel.LogOutViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class OrderHistoryActivity extends AppCompatActivity {

    private ActivityOrderHistoryBinding binding;

    OrderHistoryAdapter orderHistoryAdapter;
    ArrayList<OrderHistoryModel> orderHistoryArrayList;

    FirebaseDatabase database;
    DatabaseReference myRef;

    LogOutViewModel logOutViewModel;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    Intent loginInt,profileInt,cartInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        cartInt= new Intent(this, CartActivity.class); //go to cart page
        profileInt= new Intent(this, ProfileActivity.class); //go to profile page

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        binding.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.ordersRecyclerView.setHasFixedSize(true);

        orderHistoryArrayList = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderHistoryArrayList.clear();
                for (DataSnapshot orders: snapshot.child("orders").getChildren()){
                    if(Objects.equals(MainActivity.currentUserEmail, orders.child("userEmail").getValue().toString())){
                        OrderHistoryModel order= new OrderHistoryModel(orders.getKey().toString(),orders.child("States").getValue().toString());
                        orderHistoryArrayList.add(order);
                    }
                }
                orderHistoryAdapter = new OrderHistoryAdapter(OrderHistoryActivity.this, orderHistoryArrayList);
                binding.ordersRecyclerView.setAdapter(orderHistoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        binding.include.cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(cartInt);
            }
        });

        binding.include.menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMenu(view);
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
                if(item.getTitle().toString().equals("Logout")){
                    logout();
                }
                return true;
            }
        });

        popupMenu.show();
    }

    private void logout(){
        //delete sp when logout
        sp=getSharedPreferences("orderss",0);
        ed=sp.edit();
        ed.clear();
        ed.commit();
        logOutViewModel.logOut();
    }

    private void startLoginActivity(){
        loginInt=new Intent(this, LoginActivity.class);
        startActivity(loginInt);
        finish();
        return;
    }


}