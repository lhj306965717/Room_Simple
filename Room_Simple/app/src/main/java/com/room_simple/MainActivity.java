package com.room_simple;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.room_simple.db.AppDatabase;
import com.room_simple.db.DBManager;
import com.room_simple.db.User;
import com.room_simple.db.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase mAppDatabase;

    int id = 0;
    //    private UserDao mUserDao;
    private UserDaoImpl mUserDao;

    private String[] tables = new String[]{"User", "User1", "User2", "User3"};
    private UserDaoImpl[] mUserDaos = new UserDaoImpl[4];
    private int tableIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppDatabase = DBManager.getAppDatabase();

//        mUserDao = mAppDatabase.getUserDao();

        mUserDaos[0] = new UserDaoImpl(mAppDatabase, "User");
        mUserDaos[1] = new UserDaoImpl(mAppDatabase, "User1");
        mUserDaos[2] = new UserDaoImpl(mAppDatabase, "User2");
        mUserDaos[3] = new UserDaoImpl(mAppDatabase, "User3");

        mUserDao = mUserDaos[0];
    }

    public void insert(View v) {

        id++;

        User user = new User();
        user.id = id;
        user.name = "陈红";
        user.age = 26;
        user.height = 180;

        mUserDao.insert(user);
    }

    public void delete(View v) {

    }

    public void update(View v) {

    }

    public void select(View v) {

        List<User> all = mUserDao.getAll();

        Log.e("TAG", all.toString());
    }

    public void allTableName(View v) {
        String sql = "select * from sqlite_master";
        Cursor cursor = mAppDatabase.query(sql, null);

        while (cursor.moveToNext()) {

            Log.e("TAG", cursor.getString(cursor.getColumnIndex("name")));

        }

        cursor.close();
    }

    public void switcTable(View v) {

        tableIndex++;

        if (tableIndex > 3) {
            tableIndex = 0;
        }

        Log.e("TAG", "切换表名到：" + tables[tableIndex]);

        //mUserDao.setTableName(tables[tableIndex]);

        mUserDao = mUserDaos[tableIndex];

    }

    public void checkTable(View v) {
        mUserDao.checkTable();
    }

    public void createTable(View v){
        mUserDao.createTable();
    }
}
