package com.example.databasemanagement.data;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.databasemanagement.models.PlayerGroup;
import com.example.databasemanagement.models.UserGroup;

import java.util.List;

public class UserGroupViewModel extends AndroidViewModel {
    private UserGroupRepository mUserGroupRepository;
    private LiveData<List<UserGroup>> usersAndGroupsList;

    public UserGroupViewModel(@NonNull Application application) {
        super(application);
        mUserGroupRepository = new UserGroupRepository(application);
        usersAndGroupsList = mUserGroupRepository.getAllUsersAndGroups();
    }

    public void insertUserGroup(UserGroup userGroup) {
        mUserGroupRepository.insertUserGroup(userGroup);
    }

    public LiveData<List<UserGroup>> getUsersAndGroupsList() {
        return usersAndGroupsList;
    }

    public List<PlayerGroup> getUserGroups(int id) throws InterruptedException {
        return mUserGroupRepository.getUserGroups(id);
    }


}
