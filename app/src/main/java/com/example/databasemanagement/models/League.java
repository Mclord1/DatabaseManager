package com.example.databasemanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "leagues")
public class League {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "group_id")
    public int groupId;

    public League(int id, String name, int groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }

    @Ignore
    public League(String name, int groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
