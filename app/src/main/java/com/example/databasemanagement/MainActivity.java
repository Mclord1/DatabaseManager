package com.example.databasemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_FIRST_NAME = "com.example.databasemanagement.UI.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.example.databasemanagement.UI.EXTRA_LAST_NAME";
    public static final String EXTRA_GENDER = "com.example.databasemanagement.UI.EXTRA_GENDER";
    public static final String EXTRA_AGE = "com.example.databasemanagement.UI.EXTRA_AGE";
    public static final String EXTRA_ID = "com.example.databasemanagement.UI.EXTRA_ID";


    public static final String TAG = "MainActivityThis";
    private Spinner mSpinner;
    private EditText mFirstNameText;
    private EditText mLastNameText;
    private EditText mAgeText;
    private ArrayAdapter<CharSequence> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        mSpinner = findViewById(R.id.gender_spinner);
        mFirstNameText = findViewById(R.id.first_name_field);
        mLastNameText = findViewById(R.id.last_name_field);
        mAgeText = findViewById(R.id.age_name_field);
        Button saveButton = findViewById(R.id.save_button);

        mAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.gender, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);

        mFirstNameText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if (getIntent().hasExtra(UserListActivity.USER_ID)) {
            saveButton.setText(R.string.update);
            setTitle("Edit User");

            mFirstNameText.setText(getIntent().getStringExtra(EXTRA_FIRST_NAME));
            mLastNameText.setText(getIntent().getStringExtra(EXTRA_LAST_NAME));
            mAgeText.setText(String.valueOf(getIntent().getIntExtra(EXTRA_AGE, 0)));

            int genderPosition = mAdapter.getPosition(getIntent().getStringExtra(EXTRA_GENDER));
            mSpinner.setSelection(genderPosition);
        } else {
            setTitle("Add New User");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = mFirstNameText.getText().toString().trim();
                String lastName = mLastNameText.getText().toString().trim();
                String age = mAgeText.getText().toString().trim();
                String genderSpinner = mSpinner.getSelectedItem().toString();

                if (firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a value for First Name, Last Name and Age", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_FIRST_NAME, firstName);
                    intent.putExtra(EXTRA_LAST_NAME, lastName);
                    intent.putExtra(EXTRA_AGE, age);
                    intent.putExtra(EXTRA_GENDER, genderSpinner);

                    int userId = getIntent().getIntExtra(UserListActivity.USER_ID, -1);
                    if (userId != -1) {
                        intent.putExtra(EXTRA_ID, userId);
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
