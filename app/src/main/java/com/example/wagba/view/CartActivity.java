package com.example.wagba.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.model.CartItemModel;
import com.example.wagba.view.Adapter.CartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;

    SharedPreferences sp;

    FirebaseDatabase database;
    DatabaseReference myRef;

    CartAdapter cartAdapter;
    ArrayList<CartItemModel> cartArrayList;

    int totalPrice=0;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        sp = getSharedPreferences("orderss", 0);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerView.setHasFixedSize(true);


        cartArrayList = new ArrayList<>();

        cartAdapter = new CartAdapter(this, cartArrayList);


        Map<String, ?> allEntries = sp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

//            if(myRef.child("restaurants/res10/dishes/name"). == entry.getKey()){
//
//            }

            if(entry.getKey().toString().substring(entry.getKey().toString().length() - 1).equals("0")){
                pleasework(entry, 10);
            }else{
                pleasework(entry, Integer.parseInt(entry.getKey().substring(entry.getKey().length() - 1)));
            }


//            counter++;

//            Log.d("testt", "here1" + entry.getKey());
//            if (entry.getValue().toString().substring(entry.getValue().toString().length() - 1) == "10") {
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot items : snapshot.child("restaurants/res10/dishes").getChildren()) {
//
//                            Log.d("testt", "here2");
//
//                            if (items.child("name").getValue(String.class) == entry.getKey().substring(0, entry.getKey().length() - 2)) {
//                                CartItem item = new CartItem(items.child("name").getValue().toString(), items.child("image").getValue().toString(), items.child("price").getValue(Integer.class), entry.getValue().toString());
//                                cartArrayList.add(item);
//                                counter++;
//                                if (counter == sp.getAll().size()) {
//                                    Log.d("testt", cartArrayList.toString());
//                                    binding.cartRecyclerView.setAdapter(cartAdapter);
//                                }
//                            }
//                        }
////                binding.dishRecyclerView.setAdapter(new DishAdapter(Dishes.this,dishArrayList));
////                        binding.dishRecyclerView.setAdapter(dishAdapter);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//                });
//            } else {
//                Log.d("testt", "here5");
//                myRef.addValueEventListener(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Log.d("testt1", "here44" + entry.getValue().toString().substring(entry.getValue().toString().length() - 1));
//                        for (DataSnapshot items : snapshot.child("restaurants/res" + entry.getKey().toString().substring(entry.getKey().toString().length() - 1) + "/dishes").getChildren()) {
//
//                            Log.d("testt", "here3" + "fdsfdsfdfd" + entry.getKey().substring(0, entry.getKey().length() - 1));
//
//                            Log.d("testt1", "here3333" + items.child("name").getValue(String.class));
//                            Log.d("testt1", "here3333" + entry.getKey().substring(0, entry.getKey().length() - 1));
//
//                            if (items.child("name").getValue(String.class) == entry.getKey().substring(0, entry.getKey().length() - 1)) {
//                                CartItem item = new CartItem(items.child("name").getValue().toString(), items.child("image").getValue().toString(), items.child("price").getValue(Integer.class), entry.getValue().toString());
//                                cartArrayList.add(item);
//                                counter++;
//                                Log.d("testt", "rerererer" + String.valueOf(sp.getAll().size()));
//
//                                if (counter == sp.getAll().size()) {
//                                    Log.d("testt", cartArrayList.toString());
//                                    binding.cartRecyclerView.setAdapter(cartAdapter);
//                                }
//                            }
//                        }
////                binding.dishRecyclerView.setAdapter(new DishAdapter(Dishes.this,dishArrayList));
////                        binding.dishRecyclerView.setAdapter(dishAdapter);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//                });
//
//            }
//
//            if (counter == sp.getAll().size()) {
//                Log.d("testt", cartArrayList.toString());
//                binding.cartRecyclerView.setAdapter(cartAdapter);
//            }


//            if(entry.getKey().contains("10")){
//                for (DataSnapshot dishes: snapshot.child("restaurants/res"+num+"/dishes").getChildren()){
//
//                    Dish dish= new Dish(dishes.child("name").getValue().toString(),dishes.child("image").getValue().toString(),dishes.child("price").getValue(Integer.class),dishes.child("availability").getValue(Boolean.class));
//                    dishArrayList.add(dish);
//
//                }
//            }
//            Log.d("test2", entry.getKey()+entry.getValue().toString());


//            Log.d("map_values", "fgdgdfg"+entry.getKey() + ": " + entry.getValue().toString());
        }


        binding.confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.GateRadioGroup.getCheckedRadioButtonId()== -1){
                    Toast.makeText(CartActivity.this, "Please choose gate", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("abc",binding.radioButtonA.getText().toString());
                }

            }
        });

//        Log.d("test1", "thisf225"+String.valueOf(sp.getAll()));

    }

    private void pleasework(Map.Entry<String, ?> entry, int num) {

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("plz","here44"+entry.getKey().toString().substring(entry.getKey().toString().length() - 1)+" " +num);
                for (DataSnapshot items: snapshot.child("restaurants/res"+num+"/dishes").getChildren()){



                    Log.d("plz","here3");

//                    Log.d("testt1","here3333"+items.child("name").getValue(String.class));
//                    Log.d("testt1","here3333"+entry.getKey().substring(0, entry.getKey().length() - 1));

                    if(Objects.equals(items.child("name").getValue(String.class), entry.getKey().substring(0, entry.getKey().length() - String.valueOf(num).length()))){
                        CartItemModel item= new CartItemModel(items.child("name").getValue().toString(),items.child("image").getValue().toString(),items.child("price").getValue(Integer.class),entry.getValue().toString());
                        cartArrayList.add(item);
                        totalPrice+=items.child("price").getValue(Integer.class);
                        counter++;
                        Log.d("plz","here4");

                        Log.d("testt12","rerererer"+String.valueOf(sp.getAll()));

                        if(counter == sp.getAll().size()){
                            Log.d("plz","here5");
                            Log.d("testt1",cartArrayList.toString());
                            binding.totalPriceTextView.setText("total: "+totalPrice+" EGP");
                            binding.cartRecyclerView.setAdapter(cartAdapter);
                        }
                    }
                }
//                binding.dishRecyclerView.setAdapter(new DishAdapter(Dishes.this,dishArrayList));
//                        binding.dishRecyclerView.setAdapter(dishAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}


//    @Override
//    protected void onPostResume() {
//
//        Log.d("testt",cartArrayList.toString());
//        binding.cartRecyclerView.setAdapter(cartAdapter);
//        super.onPostResume();
//    }
