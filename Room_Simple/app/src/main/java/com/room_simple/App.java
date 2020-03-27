package com.room_simple;

import android.app.Application;

import com.room_simple.db.DBManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DBManager.initDB(getApplicationContext());


    }
}
