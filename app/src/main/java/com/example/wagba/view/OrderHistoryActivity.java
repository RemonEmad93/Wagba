package com.example.wagba.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.databinding.ActivityOrderHistoryBinding;
import com.example.wagba.model.CartItemModel;
import com.example.wagba.model.DishModel;
import com.example.wagba.model.OrderHistoryModel;
import com.example.wagba.model.OrderModel;
import com.example.wagba.view.Adapter.CartAdapter;
import com.example.wagba.view.Adapter.OrderHistoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private ActivityOrderHistoryBinding binding;

    OrderHistoryAdapter orderHistoryAdapter;
    ArrayList<OrderHistoryModel> orderHistoryArrayList;
    ArrayList<CartItemModel> cartArrayList;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        binding.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.ordersRecyclerView.setHasFixedSize(true);

        orderHistoryArrayList = new ArrayList<>();
        cartArrayList = new ArrayList<>();



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderHistoryArrayList.clear();
                for (DataSnapshot orders: snapshot.child("orders").getChildren()){
                    OrderHistoryModel order= new OrderHistoryModel(orders.getKey().toString(),orders.child("States").getValue().toString());
                    orderHistoryArrayList.add(order);

                    cartArrayList.clear();
                    for (DataSnapshot items: snapshot.child("orders/"+orders.getKey().toString() +"/dishes").getChildren()){
                        CartItemModel item = new CartItemModel(items.child("name").getValue().toString(),items.child("image").getValue().toString(),items.child("price").getValue(Integer.class),items.child("count").getValue().toString());
                        cartArrayList.add(item);
                    }

                }
                orderHistoryAdapter = new OrderHistoryAdapter(OrderHistoryActivity.this, orderHistoryArrayList, cartArrayList);
                binding.ordersRecyclerView.setAdapter(orderHistoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}