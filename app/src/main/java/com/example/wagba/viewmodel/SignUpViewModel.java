package com.example.wagba.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.model.AppRepository;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

   public SignUpViewModel(@NonNull Application application) {
        super(application);
        appRepository=  new AppRepository(application);
        userMutableLiveData= appRepository.getUserMutableLiveData();
   }

   public void signUp(String username, String email, String password){
       appRepository.signUp(username,email,password);
   }

   public MutableLiveData<FirebaseUser> getUserMutableLiveData(){
       return userMutableLiveData;
   }
}
