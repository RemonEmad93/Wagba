package com.example.wagba.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.wagba.model.DatabaseModel;


@Database(entities ={DatabaseModel.class}, exportSchema= false, version=5)
public abstract class ProfileDatabase extends RoomDatabase {

    public abstract ProfileDao profileDao();
    public static ProfileDatabase instance;

    public static ProfileDatabase getInstance(final Context context){

        if(instance== null){
            synchronized (ProfileDatabase.class){
                if(instance==null){
                    instance= Room.databaseBuilder(context.getApplicationContext(), ProfileDatabase.class, "profile_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }

}
