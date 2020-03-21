package com.example.databasemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasemanagement.R;
import com.example.databasemanagement.models.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<User> mUserList;
    private onItemListener mOnItemListener;

    public UserListAdapter(Context context, onItemListener onItemListener) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mOnItemListener = onItemListener;
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView userFullName, userAge, userGender;
        onItemListener mOnItemListener;


        public UserViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);
            userFullName = itemView.findViewById(R.id.user_full_name_text);
            userAge = itemView.findViewById(R.id.user_age);
            userGender = itemView.findViewById(R.id.user_gender);
            this.mOnItemListener = onItemListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            mOnItemListener.onLongClick(getAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View view) {
            mOnItemListener.onUserClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder holder, int position) {
        if (mUserList != null) {
            User currentUser = mUserList.get(position);

            holder.userFullName.setText(currentUser.getUserFullName());
            holder.userAge.setText(String.valueOf(currentUser.getAge()));
            holder.userGender.setText(currentUser.getGender());

        } else {
            holder.userFullName.setText(R.string.no_user_available);
        }
    }

    @Override
    public int getItemCount() {
        if (mUserList == null) {
            return 0;
        }
        return mUserList.size();
    }

    public void setUserList(List<User> userList) {
        this.mUserList = userList;
        notifyDataSetChanged();
    }

    public User getUserAtPosition(int position) {
        return mUserList.get(position);
    }


    public List<User> getUserList() {
        return mUserList;
    }


    public interface onItemListener {
        void onUserClick(int position);
        void onLongClick(int position);
    }

}
