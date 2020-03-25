package com.example.databasemanagement.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.databasemanagement.models.League;

import java.util.List;

@Dao
public interface LeagueDao {

    @Query("SELECT * FROM leagues")
    LiveData<List<League>> loadAllLeagues();

    @Query("SELECT * FROM leagues WHERE group_id = :id")
    List<League> getAllLeaguesInGroup(int id);

    @Insert
    void insertLeague(League league);

    @Update
    void updateLeague(League league);

}
