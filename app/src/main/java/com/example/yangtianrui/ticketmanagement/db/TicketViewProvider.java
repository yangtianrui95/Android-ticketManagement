package com.example.yangtianrui.ticketmanagement.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by yangtianrui on 16-6-24.
 */
public class TicketViewProvider extends ContentProvider {
    public static final String TICKET_VIEW_URI = "content://com.yangtianrui.TicketView";
    private OrdersDAO mOrdersDAO;

    @Override
    public boolean onCreate() {
        mOrdersDAO = new OrdersDAO();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        return mOrdersDAO.rawQuery("select * from ticket_view where guest_name = ?", selectionArgs);
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
