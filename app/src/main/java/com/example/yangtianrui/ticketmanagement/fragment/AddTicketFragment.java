package com.example.yangtianrui.ticketmanagement.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.yangtianrui.ticketmanagement.R;
import com.example.yangtianrui.ticketmanagement.adapter.FlightsAdapter;
import com.example.yangtianrui.ticketmanagement.bean.Flight;
import com.example.yangtianrui.ticketmanagement.db.FlightDAO;
import com.example.yangtianrui.ticketmanagement.db.FlightLoader;
import com.example.yangtianrui.ticketmanagement.db.TicketDAO;
import com.example.yangtianrui.ticketmanagement.view.FlightInfoActivity;

/**
 * Created by yangtianrui on 16-6-19.
 * <p/>
 * 添加航班信息
 */
public class AddTicketFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View mRoot;
    private ListView mLvFlights;
    private FlightDAO mFlightDAO;
    private TicketDAO mTicketDAO;
    private Cursor mCursor;
    private FlightsAdapter mAdapter;

    // 对话框UI
    private View mDialogview;
    private EditText mEtSeatID;
    private Button mBtnAddSeat;
    private RadioButton mRbSeatInfo;

    public static final int LOADER_ID = 1;
    public static FlightLoader mLoader;

    public AddTicketFragment() {
    }


    /**
     * 从数据库中获取Cursor
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlightDAO = new FlightDAO();
        mTicketDAO = new TicketDAO();
        mCursor = mFlightDAO.queryFlight(null, null);
    }

    /**
     * 初始化数据
     */
    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new FlightsAdapter(getActivity()
                , mCursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mLvFlights.setAdapter(mAdapter);
        mLoader = new FlightLoader(getActivity(), mAdapter);
        // 初始化Loader
        getLoaderManager().initLoader(LOADER_ID, null, mLoader);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_add_ticket, container, false);
        mLvFlights = (ListView) mRoot.findViewById(R.id.id_lv_flights);
        mLvFlights.setOnItemClickListener(this);
        // 注册上下文菜单
        registerForContextMenu(mLvFlights);
        return mRoot;
    }

    @Override
    public void onPause() {
        super.onPause();
        mCursor.close();
    }


    /**
     * 跳转到指定的航班页
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 根据位置返回特定的Cursor对象
        Cursor cursor = (Cursor) mAdapter.getItem(position);
        final int _id = cursor.getInt(0);
        // 弹出对话框，添加机票
        // 创建一个对话框
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mDialogview = inflater.inflate(R.layout.dialog_add_ticket, null, false);
        mBtnAddSeat = (Button) mDialogview.findViewById(R.id.id_btn_add_ticket);
        mEtSeatID = (EditText) mDialogview.findViewById(R.id.id_et_seat_id);
        mRbSeatInfo = (RadioButton) mDialogview.findViewById(R.id.id_rb_first_seat);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mDialogview);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        mBtnAddSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("flight_id", _id);
                values.put("seat_id", mEtSeatID.getText().toString());
                values.put("seat_info", mRbSeatInfo.isChecked() ? "一等座" : "二等座");
                mTicketDAO.insertTicket(values);
                Snackbar.make(mRoot, "添加机票成功", Snackbar.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 创建上下文菜单
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("信息查询");
        menu.add(0, v.getId(), 1, "满座率");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO 点击后,跳转到航班信息的统计页面
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //int position = mAdapter.getItem(info.position);
        int position = info.position; // list中的位置
        // CursorAdapter中getItem()返回特定的cursor对象
        // 获取ID
        Cursor c = (Cursor) mAdapter.getItem(position);
        int flight_id = c.getInt(0);
        Intent intent = new Intent(getActivity(), FlightInfoActivity.class);
        startActivity(intent);
        return super.onContextItemSelected(item);
    }
}
