package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wagba.R;
import com.example.wagba.databinding.ActivityEditProfileBinding;
import com.example.wagba.databinding.ActivityProfileBinding;
import com.example.wagba.model.DatabaseModel;
import com.example.wagba.viewmodel.DatabaseViewModel;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private DatabaseViewModel databaseViewModel;
    private DatabaseModel databaseModelObject;

    Intent profileInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);


        profileInt= new Intent(this, ProfileActivity.class);

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
//                databaseModel= new DatabaseModel( );
                Log.d("helpplz","hrere"+binding.ProfileNameEditText.getText().toString());
                databaseModelObject.setName(binding.ProfileNameEditText.getText().toString());
                databaseModelObject.setEmail(binding.ProfileEmailEditText.getText().toString());
                databaseModelObject.setPhone_number(binding.ProfilePhoneNumberEditText.getText().toString());
//                databaseViewModel= new ViewModelProvider(this).get(DatabaseViewModel.class);
                databaseViewModel.updateProfile(databaseModelObject);

                startActivity(profileInt);
                finish();
            }
        });
    }
}