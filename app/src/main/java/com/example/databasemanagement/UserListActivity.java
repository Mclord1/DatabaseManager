package com.example.databasemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasemanagement.Data.UserDisplayViewModel;
import com.example.databasemanagement.models.User;
import com.example.databasemanagement.Adapter.UserListAdapter;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class UserListActivity extends AppCompatActivity implements UserListAdapter.onItemListener {
    public static final String TAG = "UserListActivityThis";
    public static final String USER_ID = "USER_ID";
    private static final int ADD_USER_REQUEST_CODE = 1;
    private static final int EDIT_USER_REQUEST_CODE = 2;
    RecyclerView mRecyclerView;
    LinearLayout mEmptyView;
    UserListAdapter mUserListAdapter;
    private UserDisplayViewModel mUserDisplayViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setTitle("View All Users");

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserListActivity.this, MainActivity.class);
                startActivityForResult(intent, ADD_USER_REQUEST_CODE);
            }
        });

        mEmptyView = findViewById(R.id.emptyView);
        mRecyclerView = findViewById(R.id.user_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mUserListAdapter = new UserListAdapter(getApplicationContext(), this);
        mRecyclerView.setAdapter(mUserListAdapter);

        mUserDisplayViewModel = new ViewModelProvider(this).get(UserDisplayViewModel.class);
        mUserDisplayViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // Update RecyclerView
                mUserListAdapter.setUserList(users);

                if (mUserListAdapter.getUserList().isEmpty()) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(UserListActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(UserListActivity.this, R.color.buttonColor))
                        .addSwipeRightLabel("Delete")
                        .setSwipeLeftLabelColor(R.color.labelColor)
                        .addSwipeLeftLabel("Delete")
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteUser(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(mRecyclerView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_USER_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String firstName = data.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
            String lastName = data.getStringExtra(MainActivity.EXTRA_LAST_NAME);
            String age = data.getStringExtra(MainActivity.EXTRA_AGE);
            String gender = data.getStringExtra(MainActivity.EXTRA_GENDER);

            User user = new User(firstName, lastName, Integer.parseInt(age), gender);
            mUserDisplayViewModel.insert(user);

            Toast.makeText(UserListActivity.this, "Successfully Saved", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_USER_REQUEST_CODE && resultCode == RESULT_OK) {

            assert data != null;
            int user_id = data.getIntExtra(MainActivity.EXTRA_ID, -1);

            if (user_id == -1) {
                Toast.makeText(UserListActivity.this, "This user can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String firstName = data.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
            String lastName = data.getStringExtra(MainActivity.EXTRA_LAST_NAME);
            String age = data.getStringExtra(MainActivity.EXTRA_AGE);
            String gender = data.getStringExtra(MainActivity.EXTRA_GENDER);

            User user = new User(firstName, lastName, Integer.parseInt(age), gender);
            user.setuId(user_id);
            mUserDisplayViewModel.update(user);

            Toast.makeText(UserListActivity.this, "User details updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(UserListActivity.this, "User not saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserClick(int position) {
        // Get the selected item
        List<User> mUsers = mUserListAdapter.getUserList();
        User mUser = mUsers.get(position);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(UserListActivity.USER_ID, mUserListAdapter.getUserAtPosition(position).getuId());
        intent.putExtra(MainActivity.EXTRA_FIRST_NAME, mUser.getFirstName());
        intent.putExtra(MainActivity.EXTRA_LAST_NAME, mUser.getLastName());
        intent.putExtra(MainActivity.EXTRA_AGE, mUser.getAge());
        intent.putExtra(MainActivity.EXTRA_GENDER, mUser.getGender());

        startActivityForResult(intent, EDIT_USER_REQUEST_CODE);
    }

    @Override
    public void onLongClick(int position) {
        deleteUser(position);
    }

    public void deleteUser(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this user?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mUserDisplayViewModel.delete(mUserListAdapter.getUserAtPosition(position));
                mUserListAdapter.notifyDataSetChanged();

                Toast.makeText(UserListActivity.this, "User Deleted Successfully", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mUserListAdapter.notifyDataSetChanged();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_notes) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_close);
            builder.setMessage("Delete all users?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mUserDisplayViewModel.deleteAllUsers();
                    Toast.makeText(UserListActivity.this, "All users deleted", Toast.LENGTH_LONG).show();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    mUserListAdapter.notifyDataSetChanged();
                }
            });
            builder.create();
            builder.show();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
