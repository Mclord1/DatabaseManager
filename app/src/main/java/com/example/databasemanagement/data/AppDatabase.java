package com.example.databasemanagement.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.databasemanagement.models.League;
import com.example.databasemanagement.models.PlayerGroup;
import com.example.databasemanagement.models.User;
import com.example.databasemanagement.models.UserGroup;

@Database(entities = {User.class, League.class, PlayerGroup.class, UserGroup.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String db_name = "user_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, db_name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDao mUserDao();

    public abstract GroupDao mGroupDao();

    public abstract LeagueDao mLeagueDao();

    public abstract UserGroupDao mUserGroupDao();
}
