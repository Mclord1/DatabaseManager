package com.example.databasemanagement.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.databasemanagement.R;
import com.example.databasemanagement.data.GroupViewModel;
import com.example.databasemanagement.data.LeagueViewModel;
import com.example.databasemanagement.models.League;
import com.example.databasemanagement.models.PlayerGroup;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SettingsActivity extends AppCompatActivity {
    BottomSheetDialog bottomSheetDialog;
    GroupViewModel mGroupViewModel;
    LeagueViewModel mLeagueViewModel;
    public static final String TAG = "SettingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Database Items");

        mGroupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        findViewById(R.id.create_group_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(SettingsActivity.this);
                bottomSheetDialog.setContentView(R.layout.group_bottom_sheet_modal);
                bottomSheetDialog.show();

                Button mSaveGroupButton = bottomSheetDialog.findViewById(R.id.save_group_button);
                final EditText groupNameField = bottomSheetDialog.findViewById(R.id.group_name_field);

                mSaveGroupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        assert groupNameField != null;
                        String groupName = groupNameField.getText().toString().trim();

                        if (groupName.isEmpty()) {
                            Toast.makeText(SettingsActivity.this, "Please enter a value for the Group Name", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            PlayerGroup playerGroup = new PlayerGroup(groupName);
                            mGroupViewModel.insert(playerGroup);

                            Toast.makeText(SettingsActivity.this, "Successfully Saved", Toast.LENGTH_LONG).show();
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
            }
        });


        mLeagueViewModel = new ViewModelProvider(this).get(LeagueViewModel.class);
        findViewById(R.id.create_league_button).setOnClickListener(new View.OnClickListener() {
            ProgressBar mProgressBar = findViewById(R.id.progressBar);

            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(SettingsActivity.this);
                bottomSheetDialog.setContentView(R.layout.league_bottom_sheet_modal);
                bottomSheetDialog.show();

                final Spinner groupSpinner = bottomSheetDialog.findViewById(R.id.group_spinner);
                assert groupSpinner != null;
                populateSpinner(groupSpinner);

                Button mSaveLeagueButton = bottomSheetDialog.findViewById(R.id.save_league_button);
                final EditText leagueNameField = bottomSheetDialog.findViewById(R.id.league_name_field);

                assert mSaveLeagueButton != null;
                mSaveLeagueButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        assert leagueNameField != null;
                        String leagueName = leagueNameField.getText().toString().trim();
                        String groupSpinnertext = groupSpinner.getSelectedItem().toString();
                        int groupId;


                        if (leagueName.isEmpty()) {
                            Toast.makeText(SettingsActivity.this, "Please enter a value for the Group Name", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            try {
                                groupId = mLeagueViewModel.getGroupId(groupSpinnertext);
                                League league = new League(leagueName, groupId);

                                mLeagueViewModel.insertLeague(league);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            Toast.makeText(SettingsActivity.this, "Successfully Saved", Toast.LENGTH_LONG).show();
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
            }
        });


    }

    private void populateSpinner(Spinner spinner) {
        GroupViewModel groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        final List<String> groupList = new ArrayList<>();

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.simple_spinner_item, groupList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        groupViewModel.getAllGroups().observe(this, new Observer<List<PlayerGroup>>() {
            @Override
            public void onChanged(List<PlayerGroup> playerGroups) {
                for (PlayerGroup group : playerGroups) {
                    groupList.add(group.name);
                }

                dataAdapter.notifyDataSetChanged();
            }
        });


    }
}










