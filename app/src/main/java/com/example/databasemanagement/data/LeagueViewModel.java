package com.example.databasemanagement.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.databasemanagement.models.League;
import com.example.databasemanagement.models.PlayerGroup;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LeagueViewModel extends AndroidViewModel {
    private LeagueRepository mLeagueRepository;
    private LiveData<List<League>> allLeagues;

    public LeagueViewModel(@NonNull Application application) {
        super(application);
        mLeagueRepository = new LeagueRepository(application);
        allLeagues = mLeagueRepository.getAllLeagues();
    }

    public void insertLeague(League league) {
        mLeagueRepository.insertLeague(league);
    }

    public LiveData<List<League>> getAllLeagues() {
        return allLeagues;
    }

    public int getGroupId(String name) throws InterruptedException {
        return mLeagueRepository.getGroupId(name);
    }
}
