package com.example.wagba.model.Repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.wagba.database.ProfileDao;
import com.example.wagba.database.ProfileDatabase;
import com.example.wagba.model.DatabaseModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseRepository {

    private ProfileDao profileDao;
    private LiveData<List<DatabaseModel>> data;
    private ProfileDatabase profileDatabase;
//    private UserDatabase userDatabase;
    private Executor executor= Executors.newSingleThreadExecutor();

    public DatabaseRepository(Application application){

        profileDatabase= ProfileDatabase.getInstance(application);
        profileDao=profileDatabase.profileDao();
//        data= profileDao.getAllData();
    }

//    public void insertProfile(DatabaseModel database){
//
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                profileDatabase.profileDao().insertProfile(database);
//            }
//        });
//    }

    public void insert(DatabaseModel databaseModel){
        Log.d("inseeeert","here2");
        new insertProfile(profileDao).execute(databaseModel);
    }


    private static class insertProfile extends AsyncTask<DatabaseModel,Void,Void>{

        private ProfileDao proDao;

        insertProfile(ProfileDao proDao){
            this.proDao=proDao;
        }


        @Override
        protected Void doInBackground(DatabaseModel... databaseModels) {

            proDao.insertProfile(databaseModels[0]);
            return null;
        }
    }

    public LiveData<List<DatabaseModel>> getAllData(){
        Log.d("geeeet","here2");
        return profileDao.getAllData();
    }

}
