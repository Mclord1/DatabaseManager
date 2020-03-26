package com.example.databasemanagement.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.databasemanagement.models.PlayerGroup;
import com.example.databasemanagement.models.UserGroup;

import java.util.List;

@Dao
public interface UserGroupDao {

    @Query("SELECT * FROM groups WHERE id IN (SELECT group_id FROM user_groups WHERE user_id = :id)")
    List<PlayerGroup> getUserGroups(int id);

    @Query("SELECT * FROM user_groups")
    LiveData<List<UserGroup>> getAllUsersAndGroups();

    @Insert
    void insertUserGroup(UserGroup userGroup);

    @Update
    void updateUserGroup(UserGroup userGroup);

    @Query("SELECT uId FROM users WHERE first_name = :userFirstName")
    int getUserId(String userFirstName);

}
