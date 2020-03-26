package com.example.databasemanagement.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.databasemanagement.models.PlayerGroup;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM groups")
    LiveData<List<PlayerGroup>> LoadAllGroups();

    @Insert
    long insertPlayerGroup(PlayerGroup playerGroup);

    @Update
    void updatePlayerGroup(PlayerGroup playerGroup);

    @Delete
    void deletePlayerGroup(PlayerGroup playerGroup);

    @Query("SELECT id FROM groups WHERE name = :groupName")
    LiveData<Integer> getGroupId(String groupName);

}
