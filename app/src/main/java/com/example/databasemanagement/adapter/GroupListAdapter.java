package com.example.databasemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasemanagement.R;
import com.example.databasemanagement.models.PlayerGroup;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {
    private List<PlayerGroup> allGroups;
    private LayoutInflater mLayoutInflater;

    public GroupListAdapter(Context context, List<PlayerGroup> allGroups) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.allGroups = allGroups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.group_entry_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        PlayerGroup currentGroup = allGroups.get(position);
        holder.entryTextView.setText(currentGroup.name);
    }

    @Override
    public int getItemCount() {
        if (allGroups == null) {
            return 0;
        }
        return allGroups.size();
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView entryTextView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            entryTextView = itemView.findViewById(R.id.group_entry_item);
        }
    }

    public void setGroupList(List<PlayerGroup> allGroups) {
        this.allGroups = allGroups;
        notifyDataSetChanged();
    }

    public List<PlayerGroup> getAllGroups() {
        return allGroups;
    }

}
