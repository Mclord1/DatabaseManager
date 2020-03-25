package com.example.databasemanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups")
public class PlayerGroup {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    public PlayerGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public PlayerGroup(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }
}