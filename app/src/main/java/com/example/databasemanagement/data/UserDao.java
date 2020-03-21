package com.example.databasemanagement.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.databasemanagement.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users ORDER BY UID")
    LiveData<List<User>> loadAllUsers();

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();

    @Query("SELECT * FROM users WHERE uId = :uId")
    User getUser(int uId);
}
