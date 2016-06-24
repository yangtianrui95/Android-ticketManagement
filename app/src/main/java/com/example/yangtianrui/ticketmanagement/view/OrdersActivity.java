package com.example.yangtianrui.ticketmanagement.view;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.yangtianrui.ticketmanagement.R;
import com.example.yangtianrui.ticketmanagement.adapter.OrdersAdapter;
import com.example.yangtianrui.ticketmanagement.db.OrdersDAO;

public class OrdersActivity extends BaseActivity {

    private ListView mLvOrders;
    private OrdersDAO mOrderDAO;
    private String mUserName;

    private Cursor mCursor;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvent() {
        mLvOrders.setAdapter(mAdapter);
        mLvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // 对订单进行付款
                Cursor temp = (Cursor) mAdapter.getItem(position);
                final int order_id = temp.getInt(0);
                boolean isPaying = Boolean.parseBoolean(temp.getString(temp.getColumnIndex("is_paying")));
                // 需正确获取每个Item
                View itemView = mLvOrders.getChildAt((position - mLvOrders.getFirstVisiblePosition()));
                final OrdersAdapter.ViewHolder holder = (OrdersAdapter.ViewHolder) itemView.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);
                builder.setTitle("确认付款");
                builder.setMessage("点击确定即可付款");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        values.put("is_paying", "true");
                        if (mOrderDAO.updateOrders(values, "_id=?", new String[]{order_id + ""}) > 0) {
                            // 付款成功后添加图标
                            holder.mIvPaying.setImageResource(R.drawable.item_is_paying);
                            Snackbar.make(mLvOrders, "付款成功", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                final AlertDialog dialog = builder.create();
                if (!isPaying) {

                    dialog.show();
                }
            }
        });
    }

    @Override
    protected String setLabel() {
        return "已定机票";
    }

    @Override
    protected int setViewLayout() {
        return R.layout.activity_orders;
    }

    @Override
    protected void initData() {
        mOrderDAO = new OrdersDAO();
        mUserName = getIntent().getStringExtra(SignupActivity.SEND_USER_NAME);
        mCursor = mOrderDAO.rawQuery("select * from ticket_view  where guest_name = ?", new String[]{mUserName});
        mAdapter = new OrdersAdapter(this, mCursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    protected void initView() {
        mLvOrders = (ListView) findViewById(R.id.id_lv_orders);
    }
}
