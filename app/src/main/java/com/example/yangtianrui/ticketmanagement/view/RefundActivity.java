package com.example.yangtianrui.ticketmanagement.view;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.yangtianrui.ticketmanagement.R;
import com.example.yangtianrui.ticketmanagement.adapter.OrdersAdapter;
import com.example.yangtianrui.ticketmanagement.db.OrdersDAO;
import com.example.yangtianrui.ticketmanagement.db.TicketDAO;
import com.example.yangtianrui.ticketmanagement.db.TicketViewProvider;

public class RefundActivity extends BaseActivity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private ListView mLvRefund;
    private CursorAdapter mAdapter;
    private Cursor mCursor;
    private OrdersDAO mOrderDAO;
    private String mUserName;
    private TicketDAO mTicketDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Snackbar.make(mLvRefund, "点击指定的订单即可实现删除", Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void initEvent() {
        mLvRefund.setAdapter(mAdapter);
        mLvRefund.setOnItemClickListener(this);
    }

    @Override
    protected String setLabel() {
        return "机票退款";
    }

    @Override
    protected int setViewLayout() {
        return R.layout.activity_refund;
    }

    @Override
    protected void initData() {
        mUserName = getIntent().getStringExtra(SignupActivity.SEND_USER_NAME);
        mOrderDAO = new OrdersDAO();
        mTicketDAO = new TicketDAO();
        mCursor = mOrderDAO.rawQuery("select * from ticket_view where guest_name = ?"
                , new String[]{mUserName});
        mAdapter = new OrdersAdapter(this, mCursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        getLoaderManager().initLoader(2, null, this);
    }

    @Override
    protected void initView() {
        mLvRefund = (ListView) findViewById(R.id.id_lv_refund_list);
    }

    /**
     * 删除指定的订单列表
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor temp = (Cursor) mAdapter.getItem(position);
        final int order_id = temp.getInt(0);
//        Cursor c = mOrderDAO.queryOrders("_id=?", new String[]{order_id + ""});
        int ticket_id = temp.getInt(temp.getColumnIndex("ticket_id"));
        mOrderDAO.deleteOrders("_id=?", new String[]{order_id + ""});
        ContentValues values = new ContentValues();
        values.put("is_pick", "false");
        mTicketDAO.update(values, "_id=?", new String[]{ticket_id + ""});
        Snackbar.make(mLvRefund, "删除成功", Snackbar.LENGTH_SHORT).show();
        // 更新ticket的is_pick信息
        getLoaderManager().restartLoader(2, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse(TicketViewProvider.TICKET_VIEW_URI);
        return new CursorLoader(this, uri, null, null, new String[]{mUserName}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
