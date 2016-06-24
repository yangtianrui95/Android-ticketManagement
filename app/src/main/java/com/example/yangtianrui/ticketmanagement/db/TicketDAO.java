package com.example.yangtianrui.ticketmanagement.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by yangtianrui on 16-6-20.
 */
public class TicketDAO {
    private static final String TABLE_NAME = "ticket";
    private DBHelper mDBHelper;

    public TicketDAO() {
        mDBHelper = new DBHelper();
    }


    public int insertTicket(ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int row = (int) db.insert(TABLE_NAME, null, values);
        db.close();
        return row;
    }

    public Cursor rawQuery(String sql, String[] args) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        return db.rawQuery(sql, args);
    }


    public Cursor query(String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        return db.query(false, TABLE_NAME, null, selection, selectionArgs, null, null, null, null);
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }
}
