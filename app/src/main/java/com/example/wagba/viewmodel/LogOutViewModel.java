package com.example.wagba.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.model.Repository.SignInUpRepository;
import com.google.firebase.auth.FirebaseUser;

public class LogOutViewModel extends AndroidViewModel{

    private SignInUpRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutualLiveData;

    public LogOutViewModel(@NonNull Application application) {
        super(application);
        appRepository=  new SignInUpRepository(application);
        userMutableLiveData= appRepository.getUserMutableLiveData();
        loggedOutMutualLiveData= appRepository.getLoggedOutMutableLiveData();
    }

    public void logOut(){
        appRepository.logout();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData(){
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutualLiveData(){
        return loggedOutMutualLiveData;
    }

}
