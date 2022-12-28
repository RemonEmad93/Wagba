package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.wagba.R;
import com.example.wagba.databinding.ActivityProfileBinding;
import com.example.wagba.model.DatabaseModel;
import com.example.wagba.viewmodel.DatabaseViewModel;
import com.example.wagba.viewmodel.LogOutViewModel;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private DatabaseViewModel databaseViewModel;

    LogOutViewModel logOutViewModel;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    Intent loginInt,orderInt,cartInt,editInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        editInt= new Intent(this, EditProfileActivity.class);
        cartInt= new Intent(this, CartActivity.class); //go to cart page
        orderInt=new Intent(this, OrderHistoryActivity.class); //go to orders history page

        databaseViewModel= new ViewModelProvider(this).get(DatabaseViewModel.class);
        databaseViewModel.getAllData().observe(this, data ->{
            for(DatabaseModel databaseModel: data){
                if(Objects.equals(MainActivity.currentUserEmail, databaseModel.getEmail())){
                    binding.ProfileNameTextView.setText(databaseModel.getName());
                    binding.ProfileEmailTextView.setText(databaseModel.getEmail());
                    binding.ProfilePhoneNumberTextView.setText(databaseModel.getPhone_number());
                }
            }
        }  );

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(editInt);
                finish();
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