package com.example.wagba.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.model.Repository.SignInUpRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {

    private SignInUpRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        appRepository=  new SignInUpRepository(application);
        userMutableLiveData= appRepository.getUserMutableLiveData();
    }

    public void login( String email, String password){
        appRepository.login(email,password);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData(){
        return userMutableLiveData;
    }


}
