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
import android.view.View;
import android.widget.TextView;

import com.example.wagba.Dishes;
import com.example.wagba.R;
import com.example.wagba.RestaurantRecyclerViewInterface;
import com.example.wagba.model.Restaurant;
import com.example.wagba.RestaurantAdapter;
import com.example.wagba.databinding.ActivityMainBinding;
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
    FirebaseAuth auth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    Intent loginInt, dishInt;
    DatabaseReference myRef;

//    RecyclerView recyclerView;
    RestaurantAdapter restaurantAdapter;
    ArrayList<Restaurant> restaurantList;

    SharedPreferences sp;
    SharedPreferences.Editor ed;



//    TextView b1;

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

//        sp=getSharedPreferences("orderss",0);
//        ed=sp.edit();
//        ed.clear();


//        ed.clear();
//        ed.putString("chicken","1");
//        ed.commit();

//        Log.d("helpppppp",sp.getString("chicken","null"));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

//        myRef.setValue("database connected");


        binding.RestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.RestaurantRecyclerView.setHasFixedSize(true);




        restaurantList= new ArrayList<>();
//        restaurantList.add(new Restaurant("test1",R.drawable.logo));
//        restaurantList.add(new Restaurant("test2",R.drawable.logo));
//        restaurantList.add(new Restaurant("test3",R.drawable.logo));
//        restaurantList.add(new Restaurant("test4",R.drawable.logo));
//        restaurantList.add(new Restaurant("test5",R.drawable.logo));
//        restaurantList.add(new Restaurant("test6",R.drawable.logo));
//        restaurantList.add(new Restaurant("test7",R.drawable.logo));
//        restaurantList.add(new Restaurant("test8",R.drawable.logo));
//        restaurantList.add(new Restaurant("test9",R.drawable.logo));
//        restaurantList.add(new Restaurant("test10",R.drawable.logo));

        restaurantAdapter= new RestaurantAdapter(this, restaurantList, this);
//        binding.RestaurantRecyclerView.setAdapter(restaurantAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                restaurantList.clear();
                for (DataSnapshot restaurants: snapshot.child("restaurants").getChildren()){

                    Restaurant restaurant= new Restaurant(restaurants.child("name").getValue(String.class),restaurants.child("logo").getValue().toString(),restaurants.child("num").getValue(String.class));
                    restaurantList.add(restaurant);

//                    Restaurant restaurant= dataSnapshot.getValue(Restaurant.class);
//                    restaurantList.add(restaurant);
                }
//                binding.RestaurantRecyclerView.setAdapter(new RestaurantAdapter(MainActivity.this,restaurantList,restaurantRecyclerViewInterface.this));
                binding.RestaurantRecyclerView.setAdapter(restaurantAdapter);
//                restaurantAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });

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

    @Override
    public void onRestaurantClick(int position) {

        dishInt= new Intent(this, Dishes.class);
        dishInt.putExtra("place",restaurantList.get(position).getNum().toString());
        dishInt.putExtra("name",restaurantList.get(position).getRestaurantName().toString());
        Log.d("thePlace",restaurantList.get(position).getNum().toString());
        startActivity(dishInt);

    }
}