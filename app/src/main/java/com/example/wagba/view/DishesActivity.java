package com.example.wagba.view;

import static com.example.wagba.view.Adapter.DishAdapter.DishViewHolder.flag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.wagba.R;
import com.example.wagba.view.RecyclerViewInterface.DishRecyclerViewInterface;
import com.example.wagba.databinding.ActivityDishesBinding;
import com.example.wagba.model.DishModel;
import com.example.wagba.view.Adapter.DishAdapter;
import com.example.wagba.viewmodel.LogOutViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DishesActivity extends AppCompatActivity implements DishRecyclerViewInterface {

    private ActivityDishesBinding binding;

    FirebaseDatabase database;
    DatabaseReference myRef;
    LogOutViewModel logOutViewModel;

    DishAdapter dishAdapter;
    ArrayList<DishModel> dishArrayList;

    private String num;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    Intent loginInt,profileInt,orderInt,cartInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDishesBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        cartInt= new Intent(this, CartActivity.class); //go to cart page
        profileInt= new Intent(this, ProfileActivity.class); //go to profile page
        orderInt=new Intent(this, OrderHistoryActivity.class); //go to orders history page

        num= getIntent().getStringExtra("place");//get restaurant num from mainActivity

        sp=getSharedPreferences("orderss",0);
        ed=sp.edit();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        binding.dishRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.dishRecyclerView.setHasFixedSize(true);
        dishArrayList= new ArrayList<>();
        dishAdapter= new DishAdapter(this, dishArrayList, this,num);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dishArrayList.clear();
                for (DataSnapshot dishes: snapshot.child("restaurants/res"+num+"/dishes").getChildren()){
                    DishModel dish= new DishModel(dishes.child("name").getValue().toString(),dishes.child("image").getValue().toString(),dishes.child("price").getValue(Integer.class),dishes.child("availability").getValue(Boolean.class));
                    dishArrayList.add(dish);
                }
                binding.dishRecyclerView.setAdapter(dishAdapter);
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

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public void onDishClick(int position) {

        if(flag){
            ed.putString(dishArrayList.get(position).getName().toString()+num,
                    String.valueOf(Integer.parseInt(sp.getString(dishArrayList.get(position).getName().toString()+num,"0"))+1));
        }else{
            if(Objects.equals(sp.getString(dishArrayList.get(position).getName() + num, "0"), "1")){
                ed.remove(dishArrayList.get(position).getName().toString()+num).commit();

            }else{
                ed.putString(dishArrayList.get(position).getName().toString()+num,
                        String.valueOf(Integer.parseInt(sp.getString(dishArrayList.get(position).getName().toString()+num,"0")) -1));
            }
        }

        ed.commit();
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
                    startActivity(orderInt);
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