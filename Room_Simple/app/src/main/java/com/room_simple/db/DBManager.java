package com.room_simple.db;

import android.content.Context;

import com.tencent.wcdb.database.SQLiteCipherSpec;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.room.db.WCDBDatabase;
import com.tencent.wcdb.room.db.WCDBOpenHelperFactory;

import androidx.room.Room;

public class DBManager {

    private static AppDatabase sAppDatabase;

    private static final SQLiteCipherSpec cipherSpec = new SQLiteCipherSpec()
            .setPageSize(4096)
            .setKDFIteration(64000);

    public static void initDB(Context context){
        WCDBOpenHelperFactory factory = new WCDBOpenHelperFactory()
                .passphrase("passphrase".getBytes())  // passphrase to the database, remove this line for plain-text
                .cipherSpec(cipherSpec)               // cipher to use, remove for default settings
                .writeAheadLoggingEnabled(true)       // enable WAL mode, remove if not needed
                .asyncCheckpointEnabled(true);        // enable asynchronous checkpoint, remove if not needed

        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "ch.db") //dbName可以使用单独的名字或者绝对路径
                .allowMainThreadQueries()   // 允许主线程执行DB操作，一般不推荐
                .openHelperFactory(factory)   // 重要：使用WCDB打开Room
                .build();

        sAppDatabase = appDatabase;
    }

    public static AppDatabase getAppDatabase(){
        return sAppDatabase;
    }

    public static SQLiteDatabase getSQLiteDatabase(){
        return ((WCDBDatabase)sAppDatabase.getOpenHelper().getWritableDatabase()).getInnerDatabase();
    }

}
