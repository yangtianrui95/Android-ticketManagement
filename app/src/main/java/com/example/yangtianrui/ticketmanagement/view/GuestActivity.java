package com.example.yangtianrui.ticketmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.yangtianrui.ticketmanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView mGvMenu;
    private Toolbar mToolbar;
    private TextView mTvUser;
    private SimpleAdapter mAdapter;
    private String mUserName;
    private String[] mLabels = new String[]{
            "航班查询", "已定机票", "机票退款",
            "信息查询", "尚未开放", "尚未开放",
            "尚未开放", "尚未开放", "尚未开放",
    };
    private int[] mImages = new int[]{
            R.drawable.menu_flight_info, R.drawable.menu_ticket, R.drawable.menu_reserve,
            R.drawable.menu_search, R.drawable.menu_dispark, R.drawable.menu_dispark,
            R.drawable.menu_dispark, R.drawable.menu_dispark, R.drawable.menu_dispark,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar_guest);
        mToolbar.setTitle("机票管理");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mGvMenu = (GridView) findViewById(R.id.id_gv_menu);
        mTvUser = (TextView) findViewById(R.id.id_tv_username);
        initData();
        mTvUser.setText("欢迎你, " + mUserName);
        mGvMenu.setAdapter(mAdapter);
        mGvMenu.setOnItemClickListener(this);
    }

    private void initData() {
        String[] from = {"image", "text"};
        int[] to = {R.id.id_item_menu_img, R.id.id_item_menu_label};
        List<Map<String, Object>> datas = new ArrayList<>();
        for (int i = 0; i < mLabels.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", mImages[i]);
            map.put("text", mLabels[i]);
            datas.add(map);
        }
        mAdapter = new SimpleAdapter(this, datas, R.layout.item_menu, from, to);
        mUserName = getIntent().getStringExtra(SignupActivity.SEND_USER_NAME);
    }


    /**
     * 菜单单击时
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                switchToActivity(SearchActivity.class);
                break;
            case 1:
                switchToActivity(OrdersActivity.class);
                break;
            case 2:
                switchToActivity(RefundActivity.class);
                break;
            case 3:
                switchToActivity(FlightInfoActivity.class);
                break;
        }
    }

    /**
     * 切换到指定的Activity
     */
    private void switchToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(SignupActivity.SEND_USER_NAME, mUserName);
        startActivity(intent);
    }
}
