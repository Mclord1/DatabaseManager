package com.example.databasemanagement.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasemanagement.R;
import com.example.databasemanagement.adapter.LeagueAdapter;
import com.example.databasemanagement.data.LeagueViewModel;
import com.example.databasemanagement.models.League;

import java.util.List;

public class LeagueFragment extends Fragment {
    private LeagueViewModel mLeagueViewModel;
    RecyclerView mRecyclerView;
    LeagueAdapter mLeagueAdapter;
    List<League> allLeagues;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leagues, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView emptyView = view.findViewById(R.id.emptyLeagueText);
        mRecyclerView = view.findViewById(R.id.league_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mLeagueAdapter = new LeagueAdapter(getContext(), allLeagues);
        mRecyclerView.setAdapter(mLeagueAdapter);

        mLeagueViewModel = new ViewModelProvider(this).get(LeagueViewModel.class);
        mLeagueViewModel.getAllLeagues().observe(getViewLifecycleOwner(), new Observer<List<League>>() {
            @Override
            public void onChanged(List<League> leagues) {
                mLeagueAdapter.setLeagueList(leagues);

                if (mLeagueAdapter.getAllLeagues().isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
