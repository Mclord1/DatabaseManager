package com.example.databasemanagement.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.databasemanagement.models.League;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LeagueRepository {

    private LeagueDao mLeagueDao;
    private static final String TAG = "LeagueRepository";
    private GroupDao mGroupDao;
    private LiveData<List<League>> allLeagues;
    public static final int NUMBER_OF_THREADS = 5;
    public static ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private int groupId;

    public LeagueRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mGroupDao = appDatabase.mGroupDao();
        mLeagueDao = appDatabase.mLeagueDao();
        allLeagues = mLeagueDao.loadAllLeagues();
    }

    public LiveData<List<League>> getAllLeagues() {
        return allLeagues;
    }

    public void insertLeague(final League league) {
        databaseWriteExecutor.execute((new Runnable() {
            @Override
            public void run() {
                mLeagueDao.insertLeague(league);
            }
        }));
    }

    public int getGroupId(final String name) throws InterruptedException {

        databaseWriteExecutor.execute((new Runnable() {
            @Override
            public void run() {
                groupId = mGroupDao.getGroupId(name);
            }
        }));

        databaseWriteExecutor.awaitTermination(1, TimeUnit.SECONDS);

        return groupId;
    }
}