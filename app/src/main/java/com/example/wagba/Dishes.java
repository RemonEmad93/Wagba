package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.wagba.databinding.ActivityDishesBinding;
import com.example.wagba.model.Dish;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dishes extends AppCompatActivity {

    private ActivityDishesBinding binding;
//    FirebaseAuth auth;
//    FirebaseUser currentUser;
    FirebaseDatabase database;
    DatabaseReference myRef;

    DishAdapter dishAdapter;
    ArrayList<Dish> dishArrayList;

    private String num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDishesBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        num= getIntent().getStringExtra("place");


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        binding.dishRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.dishRecyclerView.setHasFixedSize(true);


        dishArrayList= new ArrayList<>();

        dishAdapter= new DishAdapter(this,dishArrayList);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dishArrayList.clear();
                for (DataSnapshot dishes: snapshot.child("restaurants/res"+num+"/dishes").getChildren()){

                    Dish dish= new Dish(dishes.child("name").getValue().toString(),dishes.child("image").getValue().toString(),dishes.child("price").getValue(Integer.class));
                    dishArrayList.add(dish);

                }
                binding.dishRecyclerView.setAdapter(new DishAdapter(Dishes.this,dishArrayList));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}