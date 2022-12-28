package com.example.wagba.model.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wagba.database.ProfileDao;
import com.example.wagba.database.ProfileDatabase;
import com.example.wagba.model.DatabaseModel;

import java.util.List;

public class DatabaseRepository {

    private ProfileDao profileDao;
    private ProfileDatabase profileDatabase;

    public DatabaseRepository(Application application){
        profileDatabase= ProfileDatabase.getInstance(application);
        profileDao=profileDatabase.profileDao();
    }

    public void insert(DatabaseModel databaseModel){
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
        return profileDao.getAllData();
    }

    public void updateProfile(DatabaseModel databaseModel){
        new updateProfile(profileDao).execute(databaseModel);
    }

    private static class updateProfile extends AsyncTask<DatabaseModel,Void,Void>{
        private ProfileDao proDao;

        updateProfile(ProfileDao proDao){
            this.proDao=proDao;
        }

        @Override
        protected Void doInBackground(DatabaseModel... databaseModels) {
            proDao.updateProfile(databaseModels[0]);
            return null;
        }
    }

}
