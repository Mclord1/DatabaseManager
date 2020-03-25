package com.example.databasemanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_groups")
public class UserGroup {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "group_id")
    public int groupId;

    public UserGroup(int id, int userId, int groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
    }

    @Ignore
    public UserGroup(int userId, int groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
