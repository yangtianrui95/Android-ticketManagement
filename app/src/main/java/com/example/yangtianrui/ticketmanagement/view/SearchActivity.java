package com.example.yangtianrui.ticketmanagement.view;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.yangtianrui.ticketmanagement.R;
import com.example.yangtianrui.ticketmanagement.adapter.FlightsAdapter;
import com.example.yangtianrui.ticketmanagement.db.FlightProvider;
import com.example.yangtianrui.ticketmanagement.db.OrdersDAO;
import com.example.yangtianrui.ticketmanagement.db.TicketDAO;

public class SearchActivity extends BaseActivity {

    private EditText mEtStarting;
    private EditText mEtEnding;
    private Button mBtnSearch;
    private ListView mLvResult;
    private FlightsAdapter mAdapter;
    private Cursor mCursor;
    private LayoutInflater mInflater;

    private View mDialogView;
    private ListView mLvTickets;
    private SimpleCursorAdapter mTicketAdapter;
    private TicketDAO mTicketDAO;
    private OrdersDAO mOrdersDAO;
    private String mUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvent() {
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = mEtStarting.getText().toString();
                String end = mEtEnding.getText().toString();
                if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
                    mCursor = getContentResolver().query(Uri.parse(FlightProvider.FLIGHT_URI)
                            , null, "flight_starting=? and flight_ending=?", new String[]{start, end}, null);
                    mAdapter = new FlightsAdapter(SearchActivity.this, mCursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    mLvResult.setAdapter(mAdapter);
                }
            }
        });
        // 弹出对话框,提供订票功能
        mLvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 根据位置,返回指定的Cursor对象
                Cursor temp = (Cursor) mAdapter.getItem(position);
                int flight_id = temp.getInt(0);
                //  弹出对话框,列出所有的机票
                Cursor ticketCursor = mTicketDAO.rawQuery("select * from ticket t inner join flights f" +
                        " on t.flight_id  = f._id where f._id = ? and t.is_pick = 'false'", new String[]{flight_id + ""});
                mTicketAdapter = new SimpleCursorAdapter(SearchActivity.this, R.layout.item_pick_ticket, ticketCursor
                        , new String[]{"seat_id", "seat_info"}
                        , new int[]{R.id.id_tv_pick_seat_id, R.id.id_tv_pick_seat_info}
                        , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                mDialogView = mInflater.inflate(R.layout.dialog_show_ticket, null, false);
                mLvTickets = (ListView) mDialogView.findViewById(R.id.id_lv_dialog_tickets);
                mLvTickets.setAdapter(mTicketAdapter);
                builder.setView(mDialogView);
                final AlertDialog dialog = builder.create();
                // 点击时,进行订票
                mLvTickets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cursor c = (Cursor) mTicketAdapter.getItem(position);
                        int ticket_id = c.getInt(0);
                        ContentValues values = new ContentValues();
                        values.put("guest_name", mUserName);
                        values.put("ticket_id", ticket_id);
                        if (mOrdersDAO.insertOrders(values) > 0) {
                            // 更新被选中的票
                            ContentValues values1 = new ContentValues();
                            values1.put("is_pick", "true");
                            mTicketDAO.update(values1, "_id=?", new String[]{ticket_id + ""});
                            Snackbar.make(mLvResult, "添加机票成功!", Snackbar.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected String setLabel() {
        return "航班查询";
    }

    @Override
    protected int setViewLayout() {
        return R.layout.activity_search;
    }


    @Override
    protected void initData() {
        mInflater = LayoutInflater.from(this);
        mTicketDAO = new TicketDAO();
        mOrdersDAO = new OrdersDAO();
        mUserName = getIntent().getStringExtra(SignupActivity.SEND_USER_NAME);
    }

    @Override
    protected void initView() {
        mEtStarting = (EditText) findViewById(R.id.id_et_search_starting);
        mEtEnding = (EditText) findViewById(R.id.id_et_search_ending);
        mBtnSearch = (Button) findViewById(R.id.id_btn_search);
        mLvResult = (ListView) findViewById(R.id.id_lv_found_flight);
        mLvResult.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCursor != null) {
            mCursor.close();
        }
    }
}
