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

        //check if cart(sp) is empty
        if(sp.getAll().isEmpty()){
            binding.cartLinearLayout.setVisibility(View.GONE);
        }else{
            Map<String, ?> allEntries = sp.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                //check if the res number is 10 (2 digits)
                if(entry.getKey().toString().substring(entry.getKey().toString().length() - 1).equals("0")){ //for item 10
                    dataFromFirebase(entry, 10);
                }else{
                    dataFromFirebase(entry, Integer.parseInt(entry.getKey().substring(entry.getKey().length() - 1))); //for items from 1 - 9
                }

            }
        }

        //get current date/time
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String currentDateTime=formatter.format(date);

        //to check current time
        SimpleDateFormat formatterT = new SimpleDateFormat("HH:mm:ss");
        Date dateT = new Date();
        currentTime=formatterT.format(dateT);

        //check if current time pass 1 PM, pass the order to the next day (add one day to the calendar)
        if(currentTime.compareTo("13:00:00") > 0){
            //creating calender instance
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            //add one day to the current date
            calendar.add(Calendar.DATE, 1);

            // getting the new date from calendar
            Date addedDate = calendar.getTime();

            // printing the new date
            currentTime = formatterT.format(addedDate);

            Toast.makeText(CartActivity.this, "order for tomorrow", Toast.LENGTH_SHORT).show();
        }

        //if time pass 10 AM and did not pass 1 PM make 12 PM time for delivery disabled
        if(currentTime.compareTo("10:00:00") > 0 && currentTime.compareTo("13:00:00") <0){
            binding.radioButton12.setEnabled(false);
        }


        binding.confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //user must choose gate and time for delivery
                if(binding.GateRadioGroup.getCheckedRadioButtonId()== -1 || binding.TimeRadioGroup.getCheckedRadioButtonId()== -1){
                    Toast.makeText(CartActivity.this, "Please choose gate & Time", Toast.LENGTH_SHORT).show();
                } else{
                    RadioButton gateRadioButton=findViewById(binding.GateRadioGroup.getCheckedRadioButtonId());
                    RadioButton timeRadioButton=findViewById(binding.TimeRadioGroup.getCheckedRadioButtonId());

                    //connect to orders table in firebase DB
                    myRef = database.getReference("orders");

                    //add current order to firebase DB (use current date/time as the order ID)
                    OrderModel order=new OrderModel(String.valueOf(totalPrice),timeRadioButton.getText().toString(),gateRadioButton.getText().toString(),"processing",MainActivity.currentUserEmail);
                    myRef.child(currentDateTime)
                            .setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    //the items enters 2 times in the cartArrayList so remove the replica
                                    if (cartArrayList.size() > cartArrayList.size() / 2 + 1) {
                                        cartArrayList.subList(cartArrayList.size() / 2 , cartArrayList.size()).clear();
                                    }

                                    //add the list of dishes of order to the firebase DB
                                    myRef.child(currentDateTime).child("dishes")
                                            .setValue(cartArrayList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    Toast.makeText(CartActivity.this, "order confirmed", Toast.LENGTH_SHORT).show();
                                                    //clear sharedPreferences
                                                    ed=sp.edit();
                                                    ed.clear();
                                                    ed.commit();
                                                    //go to order activity
                                                    startActivity(orderInt);
                                                    //destroy cart activity
                                                    finish();
                                                    return;
                                                }
                                            });
                                }

                            });
                }
            }
        });

    }

    private void dataFromFirebase(Map.Entry<String, ?> entry, int num) {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot items: snapshot.child("restaurants/res"+num+"/dishes").getChildren()){

                    //check the dish name in sp is equal to the one from firebase dp
                    if(Objects.equals(items.child("name").getValue(String.class), entry.getKey().substring(0, entry.getKey().length() - String.valueOf(num).length()))){
                        CartItemModel item= new CartItemModel(items.child("name").getValue().toString(),items.child("image").getValue().toString(),items.child("price").getValue(Integer.class),entry.getValue().toString());
                        cartArrayList.add(item);

                        totalPrice+=items.child("price").getValue(Integer.class);  //calculate total price
                        counter++; //to check it iterate over all items in sp

                        //when finish iterating over sp
                        if(counter == sp.getAll().size()){
                            binding.totalPriceTextView.setText("total: "+totalPrice+" EGP");
                            binding.cartRecyclerView.setAdapter(cartAdapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
