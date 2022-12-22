package com.example.wagba.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.model.SignInUpRepository;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends AndroidViewModel {

    private SignInUpRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

   public SignUpViewModel(@NonNull Application application) {
        super(application);
        appRepository=  new SignInUpRepository(application);
        userMutableLiveData= appRepository.getUserMutableLiveData();
   }

   public void signUp(String username, String email, String password, String phone_number){
       appRepository.signUp(username,email,password, phone_number);
   }

   public MutableLiveData<FirebaseUser> getUserMutableLiveData(){
       return userMutableLiveData;
   }
}
