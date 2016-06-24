package com.example.yangtianrui.ticketmanagement.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by yangtianrui on 16-6-19.
 * <p/>
 * 提供数据库连接
 */
public class DBHelper {
    private static final String DB_PATH = "/data/data/" +
            "com.example.yangtianrui.ticketmanagement/databases/ticket_db.db3";

    /**
     * 获取可读写的数据库
     */
    public SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }


    /**
     * 获取仅可读的数据库
     */
    public SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }
}
