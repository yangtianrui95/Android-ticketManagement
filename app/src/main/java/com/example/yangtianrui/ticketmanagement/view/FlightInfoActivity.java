package com.example.yangtianrui.ticketmanagement.view;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.yangtianrui.ticketmanagement.R;
import com.example.yangtianrui.ticketmanagement.adapter.FlightInfoAdapter;
import com.example.yangtianrui.ticketmanagement.db.FlightDAO;

public class FlightInfoActivity extends BaseActivity {

    private ListView mLvFlights;
    private ListAdapter mAdapter;
    private Cursor mCursor;
    private FlightDAO mFlightDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvent() {
        mLvFlights.setAdapter(mAdapter);
    }

    @Override
    protected String setLabel() {
        return "航班信息";
    }

    @Override
    protected int setViewLayout() {
        return R.layout.activity_flight_info;
    }

    @Override
    protected void initData() {
        mFlightDAO = new FlightDAO();
        mCursor = mFlightDAO.rawQuery("select * from flight_capacity", null);
        mAdapter = new FlightInfoAdapter(this, mCursor
                , CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    protected void initView() {
        mLvFlights = (ListView) findViewById(R.id.id_lv_flights_info);
    }
}
