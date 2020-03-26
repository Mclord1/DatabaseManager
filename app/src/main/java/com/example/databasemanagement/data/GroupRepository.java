package com.example.databasemanagement.data;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.databasemanagement.models.PlayerGroup;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GroupRepository {

    private GroupDao mGroupDao;
    private LiveData<List<PlayerGroup>> allGroups;
    private int groupId;
    private long groupId2;

    public GroupRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mGroupDao = appDatabase.mGroupDao();
        allGroups = mGroupDao.LoadAllGroups();
    }

    public long insertGroup(final PlayerGroup group) throws InterruptedException {
        LeagueRepository.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupId2 = mGroupDao.insertPlayerGroup(group);
            }
        });

        LeagueRepository.databaseWriteExecutor.awaitTermination(2, TimeUnit.SECONDS);

        return groupId2;
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

    public int getGroupId(final String groupName) throws InterruptedException {

        LeagueRepository.databaseWriteExecutor.execute((new Runnable() {
            @Override
            public void run() {
                groupId = mGroupDao.getGroupId(groupName);
            }
        }));

        LeagueRepository.databaseWriteExecutor.awaitTermination(2, TimeUnit.SECONDS);

        return groupId;
    }
}
