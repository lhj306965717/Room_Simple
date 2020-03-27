package com.room_simple;

import android.database.Cursor;
import android.util.Log;

import com.room_simple.db.DBManager;
import com.room_simple.db.User;
import com.room_simple.db.UserDao;
import com.tencent.wcdb.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

public class UserDaoImpl implements UserDao {
    private final RoomDatabase __db;

    private String tableName;

    private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

    private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

    private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

    public UserDaoImpl(RoomDatabase __db, final String tableName) {
        this.__db = __db;
        this.tableName = tableName;
        this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
            @Override
            public String createQuery() {
                return "INSERT OR REPLACE INTO `" + tableName + "` (`id`,`name`,`age`,`height`) VALUES (?,?,?,?)";
            }

            @Override
            public void bind(SupportSQLiteStatement stmt, User value) {
                stmt.bindLong(1, value.id);
                if (value.name == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.name);
                }
                stmt.bindLong(3, value.age);
                stmt.bindLong(4, value.height);
            }
        };
        this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
            @Override
            public String createQuery() {
                return "DELETE FROM `" + tableName + "` WHERE `id` = ?";
            }

            @Override
            public void bind(SupportSQLiteStatement stmt, User value) {
                stmt.bindLong(1, value.id);
            }
        };
        this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
            @Override
            public String createQuery() {
                return "UPDATE OR ABORT `" + tableName + "` SET `id` = ?,`name` = ?,`age` = ?,`height` = ? WHERE `id` = ?";
            }

            @Override
            public void bind(SupportSQLiteStatement stmt, User value) {
                stmt.bindLong(1, value.id);
                if (value.name == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.name);
                }
                stmt.bindLong(3, value.age);
                stmt.bindLong(4, value.height);
                stmt.bindLong(5, value.id);
            }
        };
    }

    public void createTable() {
        SQLiteDatabase sqLiteDatabase = DBManager.getSQLiteDatabase();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `" + tableName + "` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `age` INTEGER NOT NULL, `height` INTEGER NOT NULL, PRIMARY KEY(`id`))");
    }

    public void checkTable() {
//        String sql = "select * from sqlite_master where type='table' and name='"+tableName+"'";
        String sql = "select count(*) from sqlite_master where name='" + tableName + "' and type='table'";

        Cursor cursor = __db.query(sql, null);

//        Log.e("TAG", "checkTable: " + cursor.getCount());

        while (cursor.moveToNext()){
            Log.e("TAG", cursor.getString(cursor.getColumnIndex("name")));
        }

        cursor.close();
    }

    @Override
    public void insert(final User user) {
        __db.assertNotSuspendingTransaction();
        __db.beginTransaction();
        try {
            __insertionAdapterOfUser.insert(user);
            __db.setTransactionSuccessful();
        } finally {
            __db.endTransaction();
        }
    }

    @Override
    public void delete(final User user) {
        __db.assertNotSuspendingTransaction();
        __db.beginTransaction();
        try {
            __deletionAdapterOfUser.handle(user);
            __db.setTransactionSuccessful();
        } finally {
            __db.endTransaction();
        }
    }

    @Override
    public void update(final User user) {
        __db.assertNotSuspendingTransaction();
        __db.beginTransaction();
        try {
            __updateAdapterOfUser.handle(user);
            __db.setTransactionSuccessful();
        } finally {
            __db.endTransaction();
        }
    }

    @Override
    public List<User> getAll() {
        final String _sql = "select * from " + tableName;
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
        __db.assertNotSuspendingTransaction();
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
            final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
            final List<User> _result = new ArrayList<User>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                final User _item;
                _item = new User();
                _item.id = _cursor.getInt(_cursorIndexOfId);
                _item.name = _cursor.getString(_cursorIndexOfName);
                _item.age = _cursor.getInt(_cursorIndexOfAge);
                _item.height = _cursor.getInt(_cursorIndexOfHeight);
                _result.add(_item);
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }
}
