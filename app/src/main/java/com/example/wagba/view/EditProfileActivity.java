package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.wagba.R;
import com.example.wagba.databinding.ActivityEditProfileBinding;
import com.example.wagba.databinding.ActivityProfileBinding;
import com.example.wagba.model.DatabaseModel;
import com.example.wagba.viewmodel.DatabaseViewModel;
import com.example.wagba.viewmodel.LogOutViewModel;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private DatabaseViewModel databaseViewModel;
    private DatabaseModel databaseModelObject;
    LogOutViewModel logOutViewModel;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    Intent loginInt,profileInt,orderInt,cartInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        cartInt= new Intent(this, CartActivity.class); //go to cart page
        profileInt= new Intent(this, ProfileActivity.class); //go to profile page
        orderInt=new Intent(this, OrderHistoryActivity.class); //go to orders history page

        databaseViewModel= new ViewModelProvider(this).get(DatabaseViewModel.class);
        databaseViewModel.getAllData().observe(this, data ->{
            for(DatabaseModel databaseModel: data){
                if(Objects.equals(MainActivity.currentUserEmail, databaseModel.getEmail())){
                    binding.ProfileNameEditText.setText(databaseModel.getName());
                    binding.ProfileEmailEditText.setText(databaseModel.getEmail());
                    binding.ProfilePhoneNumberEditText.setText(databaseModel.getPhone_number());
                    databaseModelObject=databaseModel;
                }
            }
        }  );


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //insert user data into room database
                databaseModelObject.setName(binding.ProfileNameEditText.getText().toString());
                databaseModelObject.setEmail(binding.ProfileEmailEditText.getText().toString());
                databaseModelObject.setPhone_number(binding.ProfilePhoneNumberEditText.getText().toString());
                databaseViewModel.updateProfile(databaseModelObject);

                startActivity(profileInt);
                finish();
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