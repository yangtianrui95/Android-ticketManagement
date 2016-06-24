package com.example.yangtianrui.ticketmanagement.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yangtianrui.ticketmanagement.R;

/**
 * Created by yangtianrui on 16-6-22.
 */
public class OrdersAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private View mRoot;
    private boolean mIsPaying;

    public OrdersAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        mRoot = mInflater.inflate(R.layout.item_orders, null, false);
        mIsPaying = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("is_paying")));
        ViewHolder holder = new ViewHolder();
        holder.mTvStarting = (TextView) mRoot.findViewById(R.id.id_tv_orders_starting);
        holder.mTvEnding = (TextView) mRoot.findViewById(R.id.id_tv_orders_ending);
        holder.mTvSeatID = (TextView) mRoot.findViewById(R.id.id_tv_orders_seat_id);
        holder.mTvSeatInfo = (TextView) mRoot.findViewById(R.id.id_tv_orders_seat_info);
        holder.mTvTime = (TextView) mRoot.findViewById(R.id.id_tv_orders_flight_time);
        holder.mIvPaying = (ImageView) mRoot.findViewById(R.id.id_iv_is_paying);
        mRoot.setTag(holder);
        return mRoot;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mTvStarting.setText(cursor.getString(cursor.getColumnIndex("flight_starting")));
        holder.mTvEnding.setText(cursor.getString(cursor.getColumnIndex("flight_ending")));
        holder.mTvSeatID.setText(cursor.getString(cursor.getColumnIndex("seat_id")));
        holder.mTvSeatInfo.setText(cursor.getString(cursor.getColumnIndex("seat_info")));
        holder.mTvTime.setText(cursor.getString(cursor.getColumnIndex("flight_time")));
        if (mIsPaying) {
            holder.mIvPaying.setImageResource(R.drawable.item_is_paying);
        }
    }


    /**
     * 怕判断当前行是否可以单击
     */
    @Override
    public boolean isEnabled(int position) {

        return super.isEnabled(position);
    }

    public static class ViewHolder {
        public TextView mTvStarting;
        public TextView mTvEnding;
        public TextView mTvSeatID;
        public TextView mTvSeatInfo;
        public TextView mTvTime;
        public ImageView mIvPaying;
    }
}
