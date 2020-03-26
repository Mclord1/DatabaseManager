package com.example.databasemanagement.data;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.databasemanagement.models.PlayerGroup;
import com.example.databasemanagement.models.UserGroup;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class UserGroupRepository {
    private UserGroupDao mUserGroupDao;
    private LiveData<List<UserGroup>> userGroupList;
    private List<PlayerGroup> userGroups;

    public UserGroupRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
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

    public LiveData<List<PlayerGroup>> getUserGroups(final int id) {
        return mUserGroupDao.getUserGroups(id);
    }

    public LiveData<Integer> getUserId(final String name) {
        return mUserGroupDao.getUserId(name);
    }

}
