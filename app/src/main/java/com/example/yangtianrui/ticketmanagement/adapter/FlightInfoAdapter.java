package com.example.yangtianrui.ticketmanagement.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.yangtianrui.ticketmanagement.R;

import java.text.NumberFormat;

/**
 * Created by yangtianrui on 16-6-19.
 */
public class FlightInfoAdapter extends CursorAdapter {

    private Context mContext;


    public FlightInfoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_flight_info, null, false);
        ViewHolder holder = new ViewHolder();
        holder.mTvCompany = (TextView) root.findViewById(R.id.id_tv_item_info_company);
        holder.mTvStarting = (TextView) root.findViewById(R.id.id_tv_item_info_starting);
        holder.mTvEnding = (TextView) root.findViewById(R.id.id_tv_item_info_ending);
        holder.mTvPrice = (TextView) root.findViewById(R.id.id_tv_item_info_price);
        holder.mTvTime = (TextView) root.findViewById(R.id.id_tv_item_info_time);
        holder.mTvCapacity = (TextView) root.findViewById(R.id.id_tv_item_info_capacity);
        root.setTag(holder);
        return root;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mTvCompany.setText("航空公司: " + cursor.getString(cursor.getColumnIndex("flight_company")));
        holder.mTvStarting.setText("始发地: " + cursor.getString(cursor.getColumnIndex("flight_starting")));
        holder.mTvEnding.setText("目的地: " + cursor.getString(cursor.getColumnIndex("flight_ending")));
        holder.mTvPrice.setText("价格: " + cursor.getString(cursor.getColumnIndex("flight_price")));
        holder.mTvTime.setText(cursor.getString(cursor.getColumnIndex("flight_time")));
        float all_count = cursor.getInt(cursor.getColumnIndex("all_count"));
        Log.v("LOG", "all_count: " + all_count);
        float pick_count = cursor.getInt(cursor.getColumnIndex("pick_count"));
        Log.v("LOG", "pick_count: " + pick_count);
        float ratio = pick_count / all_count;
        // 格式化浮点数
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        holder.mTvCapacity.setText("满坐率: " + ratio);
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
    }

    class ViewHolder {
        TextView mTvCompany;
        TextView mTvStarting;
        TextView mTvEnding;
        TextView mTvPrice;
        TextView mTvTime;
        TextView mTvCapacity;
    }
}
