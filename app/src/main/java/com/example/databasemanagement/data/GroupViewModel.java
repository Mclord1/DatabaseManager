package com.example.databasemanagement.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.databasemanagement.models.PlayerGroup;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {
    private GroupRepository mGroupRepository;
    private LiveData<List<PlayerGroup>> allGroups;

    public GroupViewModel(@NonNull Application application) {
        super(application);
        mGroupRepository = new GroupRepository(application);
        allGroups = mGroupRepository.getAllGroups();
    }

    public void insert(PlayerGroup group) {
        mGroupRepository.insertGroup(group);
    }

    public void update(PlayerGroup group) {
        mGroupRepository.updateGroup(group);
    }

    public void delete(PlayerGroup group) {
        mGroupRepository.deleteGroup(group);
    }

    public LiveData<List<PlayerGroup>> getAllGroups() {
        return allGroups;
    }

    public LiveData<Integer> getGroupId(String name) {
        return mGroupRepository.getGroupId(name);
    }
}
