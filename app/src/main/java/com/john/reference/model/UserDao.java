package com.john.reference.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserModel user);

    @Update
    void update(UserModel user);

    @Delete
    void delete(UserModel user);

    @Query("SELECT * FROM users")
    LiveData<List<UserModel>> getAllUsers();

}
