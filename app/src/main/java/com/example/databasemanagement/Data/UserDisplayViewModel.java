package com.example.databasemanagement.Data;

import android.app.Application;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.databasemanagement.models.User;

import java.util.List;

public class UserDisplayViewModel extends AndroidViewModel {

    private UserRepository mUserRepository;
    private LiveData<List<User>> allUsers;


    public UserDisplayViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        allUsers = mUserRepository.getAllUsers();
    }

    public void insert(User user) {
        mUserRepository.insertUser(user);
    }

    public void update(User user) {
        mUserRepository.updateUser(user);
    }

    public void delete(User user) {
        mUserRepository.deleteUser(user);
    }

    public void deleteAllUsers() {
        mUserRepository.deleteAllUsers();
    }

    public void getUser(User user) {
        mUserRepository.getUser(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

}
