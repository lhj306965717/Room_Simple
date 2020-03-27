package com.room_simple.db;

import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public int id;

    @NonNull
    public String name;
    public int age;
    public int height;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
