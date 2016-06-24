package com.example.yangtianrui.ticketmanagement.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yangtianrui on 16-6-20.
 */
public class FlightProvider extends ContentProvider {
    public static final String FLIGHT_URI = "content://com.yangtianrui.Flight";
    private FlightDAO mFlightDAO;


    @Override
    public boolean onCreate() {
        mFlightDAO = new FlightDAO();
        return false;
    }


    /**
     * 查询所有航班信息
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        Log.v("LOG", "ContentProvider   query");
        return mFlightDAO.queryFlight(selection, selectionArgs);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
