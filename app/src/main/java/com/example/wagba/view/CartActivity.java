package com.example.wagba.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.model.CartItemModel;
import com.example.wagba.model.OrderModel;
import com.example.wagba.model.UserModel;
import com.example.wagba.view.Adapter.CartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    FirebaseDatabase database;
    DatabaseReference myRef;

    CartAdapter cartAdapter;
    ArrayList<CartItemModel> cartArrayList;

    String currentTime;

    Intent orderInt;

    int totalPrice=0;

    int counter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        orderInt= new Intent(this,OrderHistoryActivity.class);

        sp = getSharedPreferences("orderss", 0);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerView.setHasFixedSize(true);


        cartArrayList = new ArrayList<>();

        cartAdapter = new CartAdapter(this, cartArrayList);


        if(sp.getAll().isEmpty()){
            binding.cartLinearLayout.setVisibility(View.GONE);
        }else{
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
        }

        SimpleDateFormat formatterT = new SimpleDateFormat("HH:mm:ss");
        Date dateT = new Date();
        currentTime=formatterT.format(dateT);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String currentDateTime=formatter.format(date);

        if(currentTime.compareTo("13:00:00") > 0){
            //creating calender instance
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.add(Calendar.DATE, 1);

            // getting the new date from calendar
            Date addedDate = calendar.getTime();

            // printing the new date
            currentTime = formatterT.format(addedDate);

            Toast.makeText(CartActivity.this, "order for tomorrow", Toast.LENGTH_SHORT).show();
        }

        if(currentTime.compareTo("10:00:00") > 0 && currentTime.compareTo("13:00:00") <0){
            binding.radioButton12.setEnabled(false);
        }




        binding.confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                // printing the date in the readable format.
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//                String dateStr = dateFormat.format(date);
//                System.out.println("Current date : "+dateStr);

//                //creating calender instance
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//
//                calendar.add(Calendar.DATE, 1);
//
//                // getting the new date from calendar
//                Date addedDate = calendar.getTime();
//
//                // printing the new date
//                currentTime = formatterT.format(addedDate);
//                System.out.println("New date after adding 2 years : "+newDateinStr);



                if(binding.GateRadioGroup.getCheckedRadioButtonId()== -1 || binding.TimeRadioGroup.getCheckedRadioButtonId()== -1){
                    Toast.makeText(CartActivity.this, "Please choose gate & Time", Toast.LENGTH_SHORT).show();
                } else{
                    RadioButton gateRadioButton=findViewById(binding.GateRadioGroup.getCheckedRadioButtonId());
                    RadioButton timeRadioButton=findViewById(binding.TimeRadioGroup.getCheckedRadioButtonId());



                    Log.d("one",cartArrayList.toString());
                    myRef = database.getReference("orders");
                    // add user data to firebase DB

                    Log.d("plxwork",MainActivity.currentUserEmail);
                    OrderModel order=new OrderModel(String.valueOf(totalPrice),timeRadioButton.getText().toString(),gateRadioButton.getText().toString(),"processing",MainActivity.currentUserEmail);
                    myRef.child(currentDateTime)
                            .setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //pass current user id to viewModel
//                                    int i=0;
//                                    for(i=0;i<cartArrayList.size();i++){
                                    ArrayList<CartItemModel> tmp;

                                    if (cartArrayList.size() > cartArrayList.size() / 2 + 1) {
                                        cartArrayList.subList(cartArrayList.size() / 2 , cartArrayList.size()).clear();
                                    }
                                    Log.d("threeee",cartArrayList.toString()+ cartArrayList.size());
                                        myRef.child(currentDateTime).child("dishes")
                                                .setValue(cartArrayList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        //go to order activity
                                                        Toast.makeText(CartActivity.this, "order confirmed", Toast.LENGTH_SHORT).show();
                                                        ed=sp.edit();
                                                        ed.clear();
                                                        ed.commit();
                                                        startActivity(orderInt);
                                                        finish();
                                                        return;
                                                        //clear sharedPreferences
                                                        //destroy cart activity

                                                    }
                                                });
//                                    }

                                }

                            });
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

//                        Log.d("one","rfsfdf"+cartArrayList.get(0).getPrice());
                        totalPrice+=items.child("price").getValue(Integer.class);
                        counter++;
                        Log.d("plz","here4");

                        Log.d("testt12","rerererer"+String.valueOf(sp.getAll()));

                        if(counter == sp.getAll().size()){
                            Log.d("plz","here5");
                            Log.d("testt1",cartArrayList.toString());
                            binding.totalPriceTextView.setText("total: "+totalPrice+" EGP");
                            Log.d("ct","hrerere"+cartArrayList.toString());
//                            if (cartArrayList.isEmpty()){
//
//                                binding.cartLinearLayout.setVisibility(View.GONE);
//                            }
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
