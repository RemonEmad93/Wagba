package com.example.wagba.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.databinding.ActivityProfileBinding;
import com.example.wagba.model.DatabaseModel;
import com.example.wagba.viewmodel.DatabaseViewModel;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;

    private DatabaseViewModel databaseViewModel;
//    private DatabaseModel databaseModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);





//        databaseModel.

////        databaseViewModel.getAllData();
//
//        databaseViewModel.insertProfile();

        databaseViewModel= new ViewModelProvider(this).get(DatabaseViewModel.class);
        databaseViewModel.getAllData().observe(this, data ->{

            for(DatabaseModel databaseModel: data){
                binding.ProfileNameTextView.setText(databaseModel.getName());
                binding.ProfileEmailTextView.setText(databaseModel.getEmail());
                binding.ProfilePhoneNumberTextView.setText(databaseModel.getPhone_number());
            }
        }  );


    }
}