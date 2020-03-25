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
import com.example.databasemanagement.adapter.GroupListAdapter;
import com.example.databasemanagement.data.GroupViewModel;
import com.example.databasemanagement.models.PlayerGroup;

import java.util.List;

public class GroupFragment extends Fragment {
    private GroupViewModel mGroupViewModel;
    RecyclerView mRecyclerView;
    GroupListAdapter mGroupListAdapter;
    List<PlayerGroup> allGroups;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView emptyView = view.findViewById(R.id.emptyGroupText);
        mRecyclerView = view.findViewById(R.id.group_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mGroupListAdapter = new GroupListAdapter(getContext(), allGroups);
        mRecyclerView.setAdapter(mGroupListAdapter);

        mGroupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        mGroupViewModel.getAllGroups().observe(getViewLifecycleOwner(), new Observer<List<PlayerGroup>>() {
            @Override
            public void onChanged(List<PlayerGroup> playerGroups) {
                mGroupListAdapter.setGroupList(playerGroups);

                if (mGroupListAdapter.getAllGroups().isEmpty()) {
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
