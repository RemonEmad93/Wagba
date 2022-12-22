package com.example.wagba.model.Repository;


import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInUpRepository {

    private Application application;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;


    public SignInUpRepository(Application application){

        this.application=application;

        mAuth =FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        userMutableLiveData= new MutableLiveData<>();
        loggedOutMutableLiveData= new MutableLiveData<>();
    }

    public void signUp(String username, String email, String password, String phone_number){

        //add email and password to firebase authenticate
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //check if email and password added successfully
                        if (task.isSuccessful()) {



//                            rDatabase= new DatabaseModel();
//                            rDatabase.setName(username);
//                            rDatabase.setEmail(email);
//                            rDatabase.setPhone_number(phone_number);
//
//                            databaseViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(DatabaseViewModel.class);
//                            databaseViewModel.insertProfile(rDatabase);

                            // add user data to firebase DB
                            Log.d("car",phone_number);
                            UserModel user= new UserModel(username,email,password,phone_number);
                            myRef.child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //pass current user id to viewModel
                                            userMutableLiveData.postValue(mAuth.getCurrentUser());
                                        }
                                    });
                        } else {
                            Toast.makeText(application, "Registration failed, try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userMutableLiveData.postValue(mAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application, "email or password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logout(){
        mAuth.signOut();
        loggedOutMutableLiveData.postValue(true);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData(){
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData(){
        return loggedOutMutableLiveData;
    }
}
