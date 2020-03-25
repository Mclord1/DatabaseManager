package com.example.databasemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasemanagement.R;
import com.example.databasemanagement.databinding.UserListItemBinding;
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
        onItemListener mOnItemListener;
        private UserListItemBinding mUserListItemBinding;


        public UserViewHolder(UserListItemBinding userListItemBinding, onItemListener onItemListener) {
            super(userListItemBinding.getRoot());
            this.mUserListItemBinding = userListItemBinding;

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
        UserListItemBinding userListItemBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.user_list_item, parent, false);
        return new UserViewHolder(userListItemBinding, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder holder, int position) {
        if (mUserList != null) {
            User currentUser = mUserList.get(position);
            holder.mUserListItemBinding.setUser(currentUser);
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
