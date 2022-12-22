package com.example.wagba.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wagba.database.ProfileDatabase;
import com.example.wagba.model.DatabaseRepository;
import com.example.wagba.model.DatabaseModel;

import java.util.List;

public class DatabaseViewModel extends AndroidViewModel {

    private DatabaseRepository databaseRepository;
    private LiveData<List<DatabaseModel>> data;

    public DatabaseViewModel(Application application){
        super(application);

        databaseRepository= new DatabaseRepository(application);

//        databaseRepository= new DatabaseRepository(application);
        data= databaseRepository.getAllData();
    }

    public void insertProfile(DatabaseModel databaseModel){
        Log.d("inseeeert","here1");
        databaseRepository.insert(databaseModel);
    }

    public LiveData<List<DatabaseModel>> getAllData(){
        Log.d("geeeet","here1");
        return data;
    }
}
