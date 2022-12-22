package com.example.wagba.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wagba.model.DatabaseModel;

import java.util.List;

@Dao
public interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfile(DatabaseModel Database);

    @Query("SELECT * FROM profileData")
    LiveData<List<DatabaseModel>>  getAllData();

//    @Update
//    void updateProfile(ProfileDatabase profileDatabase);
}
