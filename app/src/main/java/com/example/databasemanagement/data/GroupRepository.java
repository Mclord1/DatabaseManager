package com.example.databasemanagement.data;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.databasemanagement.models.PlayerGroup;

import java.util.List;

public class GroupRepository {

    private GroupDao mGroupDao;
    private LiveData<List<PlayerGroup>> allGroups;

    public GroupRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mGroupDao = appDatabase.mGroupDao();
        allGroups = mGroupDao.LoadAllGroups();
    }

    public void insertGroup(final PlayerGroup group) {
        LeagueRepository.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGroupDao.insertPlayerGroup(group);
            }
        });
    }

    public void updateGroup(final PlayerGroup group) {
        LeagueRepository.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGroupDao.updatePlayerGroup(group);
            }
        });
    }

    public void deleteGroup(final PlayerGroup group) {
        LeagueRepository.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGroupDao.deletePlayerGroup(group);
            }
        });
    }

    public LiveData<List<PlayerGroup>> getAllGroups() {
        return allGroups;
    }

}
