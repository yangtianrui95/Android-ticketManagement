package com.example.yangtianrui.ticketmanagement.db;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.widget.CursorAdapter;

/**
 * Created by yangtianrui on 16-6-20.
 * <p/>
 * 异步加载航班数据
 */
public class FlightLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private CursorAdapter mAdapter;

    public FlightLoader(Context context, CursorAdapter adapter) {
        this.mAdapter = adapter;
        this.mContext = context;
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse(FlightProvider.FLIGHT_URI);
        return new CursorLoader(mContext, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
