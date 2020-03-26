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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.databasemanagement.R;
import com.example.databasemanagement.data.GroupViewModel;
import com.example.databasemanagement.data.LeagueViewModel;
import com.example.databasemanagement.data.UserDisplayViewModel;
import com.example.databasemanagement.data.UserGroupViewModel;
import com.example.databasemanagement.models.League;
import com.example.databasemanagement.models.PlayerGroup;
import com.example.databasemanagement.models.User;
import com.example.databasemanagement.models.UserGroup;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    BottomSheetDialog bottomSheetDialog;
    GroupViewModel mGroupViewModel;
    LeagueViewModel mLeagueViewModel;
    UserGroupViewModel mUserGroupViewModel;
    public static final String TAG = "SettingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Database Items");

        mUserGroupViewModel = new ViewModelProvider(this).get(UserGroupViewModel.class);
        mGroupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        findViewById(R.id.create_group_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(SettingsActivity.this);
                bottomSheetDialog.setContentView(R.layout.group_bottom_sheet_modal);
                bottomSheetDialog.show();

                final Spinner userSpinner = bottomSheetDialog.findViewById(R.id.user_spinner);
                assert userSpinner != null;
                populateUserSpinner(userSpinner);

                Button mSaveGroupButton = bottomSheetDialog.findViewById(R.id.save_group_button);
                final EditText groupNameField = bottomSheetDialog.findViewById(R.id.group_name_field);

                assert mSaveGroupButton != null;
                mSaveGroupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        assert groupNameField != null;
                        String groupName = groupNameField.getText().toString().trim();
                        String userSpinnerText = userSpinner.getSelectedItem().toString();
                        int userId;

                        if (userSpinnerText.equals("Select User")) {
                            Toast.makeText(SettingsActivity.this, "Please select a user", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (groupName.isEmpty()) {
                            Toast.makeText(SettingsActivity.this, "Please enter a value for the Group Name", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            try {
                                Log.d(TAG, "onClick: " + groupProgressBar);

                                userId = mUserGroupViewModel.getUserId(userSpinnerText);
                                PlayerGroup playerGroup = new PlayerGroup(groupName);

                                int groupId = mGroupViewModel.insert(playerGroup);

                                UserGroup userGroup = new UserGroup(userId, groupId);
                                mUserGroupViewModel.insertUserGroup(userGroup);

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


        mLeagueViewModel = new ViewModelProvider(this).get(LeagueViewModel.class);
        findViewById(R.id.create_league_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(SettingsActivity.this);
                bottomSheetDialog.setContentView(R.layout.league_bottom_sheet_modal);
                bottomSheetDialog.show();

                final Spinner groupSpinner = bottomSheetDialog.findViewById(R.id.group_spinner);
                assert groupSpinner != null;
                populateGroupSpinner(groupSpinner);

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

                        if (groupSpinnertext.equals("Select Group")) {
                            Toast.makeText(SettingsActivity.this, "Please select a group", Toast.LENGTH_SHORT).show();
                            return;
                        }


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

    private void populateGroupSpinner(Spinner spinner) {
        GroupViewModel groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        final List<String> groupList = new ArrayList<>();
        groupList.add("Select Group");

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

    private void populateUserSpinner(Spinner spinner) {
        UserDisplayViewModel userDisplayViewModel = new ViewModelProvider(this).get(UserDisplayViewModel.class);
        final List<String> userList = new ArrayList<>();
        userList.add("Select User");

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.simple_spinner_item, userList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        userDisplayViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (User user : users) {
                    userList.add(user.firstName);
                }

                dataAdapter.notifyDataSetChanged();
            }
        });
    }
}










