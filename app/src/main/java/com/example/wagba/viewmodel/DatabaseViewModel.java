package com.example.wagba.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wagba.model.Repository.DatabaseRepository;
import com.example.wagba.model.DatabaseModel;

import java.util.List;

public class DatabaseViewModel extends AndroidViewModel {

    private DatabaseRepository databaseRepository;
    private LiveData<List<DatabaseModel>> data;

    public DatabaseViewModel(Application application){
        super(application);

        databaseRepository= new DatabaseRepository(application);
        data= databaseRepository.getAllData();
    }

    public void insertProfile(DatabaseModel databaseModel){
        databaseRepository.insert(databaseModel);
    }

    public LiveData<List<DatabaseModel>> getAllData(){
        return data;
    }
}
