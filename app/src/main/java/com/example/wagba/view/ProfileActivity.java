package com.example.wagba.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wagba.databinding.ActivityProfileBinding;
import com.example.wagba.model.DatabaseModel;
import com.example.wagba.viewmodel.DatabaseViewModel;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private DatabaseViewModel databaseViewModel;
    private Intent editInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        editInt= new Intent(this, EditProfileActivity.class);

        databaseViewModel= new ViewModelProvider(this).get(DatabaseViewModel.class);
        databaseViewModel.getAllData().observe(this, data ->{
            for(DatabaseModel databaseModel: data){
//                Log.d("plzdata",SignUpActivity.userEmail+"fdsfsdfds"+databaseModel.getEmail());
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
    }
}