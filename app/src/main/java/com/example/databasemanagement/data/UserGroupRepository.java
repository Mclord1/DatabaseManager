package com.example.databasemanagement.data;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.databasemanagement.models.PlayerGroup;
import com.example.databasemanagement.models.UserGroup;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserGroupRepository {
    private UserGroupDao mUserGroupDao;
    private static final String TAG = "UserGroupRepository";
    private LeagueDao mLeagueDao;
    private GroupDao mGroupDao;
    private LiveData<List<UserGroup>> userGroupList;
    private List<PlayerGroup> userGroups;
    private int userId;

    public UserGroupRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mGroupDao = appDatabase.mGroupDao();
        mLeagueDao = appDatabase.mLeagueDao();
        mUserGroupDao = appDatabase.mUserGroupDao();
        userGroupList = mUserGroupDao.getAllUsersAndGroups();
    }

    public LiveData<List<UserGroup>> getAllUsersAndGroups() {
        return userGroupList;
    }

    public void insertUserGroup(final UserGroup userGroup) {
        LeagueRepository.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mUserGroupDao.insertUserGroup(userGroup);
            }
        });
    }

    public List<PlayerGroup> getUserGroups(final int id) throws InterruptedException {
        LeagueRepository.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userGroups = mUserGroupDao.getUserGroups(id);
            }
        });

        LeagueRepository.databaseWriteExecutor.awaitTermination(1, TimeUnit.SECONDS);

        return userGroups;
    }

    public int getUserId(final String name) throws InterruptedException {
        LeagueRepository.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userId = mUserGroupDao.getUserId(name);
            }
        });

        LeagueRepository.databaseWriteExecutor.awaitTermination(1, TimeUnit.SECONDS);
        return userId;
    }

}
