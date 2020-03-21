package com.example.databasemanagement.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.databasemanagement.models.User;

import java.util.List;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mUserDao = appDatabase.mUserDao();
        allUsers = mUserDao.loadAllUsers();
    }

    public void insertUser(User user) {
        new InsertUserDbAsyncTask(mUserDao).execute(user);
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(mUserDao).execute(user);
    }

    public void deleteUser(User user) {
        new DeleteUserAsyncTask(mUserDao).execute(user);
    }

    public void deleteAllUsers() {
        new DeleteAllUsersAsyncTask(mUserDao).execute();
    }

    public void getUser(User user) {
        new GetUserAsyncTask(mUserDao).execute();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }


    //    AsyncTasks for relating with the DB
    private class InsertUserDbAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;

        private InsertUserDbAsyncTask(UserDao userDao) {
            this.mUserDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUserDao.insertUser(users[0]);
            return null;
        }
    }

    private class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.mUserDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUserDao.updateUser(users[0]);
            return null;
        }
    }

    private class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.mUserDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUserDao.deleteUser(users[0]);
            return null;
        }
    }

    private class DeleteAllUsersAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;

        private DeleteAllUsersAsyncTask(UserDao userDao) {
            this.mUserDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUserDao.deleteAllUsers();
            return null;
        }
    }

    private class GetUserAsyncTask extends AsyncTask<Integer, Void, User> {
        private UserDao mUserDao;

        private GetUserAsyncTask(UserDao userDao) {
            this.mUserDao = userDao;
        }

        @Override
        protected User doInBackground(Integer... integers) {
              return mUserDao.getUser(integers[0]);
        }
    }

}
