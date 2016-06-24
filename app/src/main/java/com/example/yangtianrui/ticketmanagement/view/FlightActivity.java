package com.example.yangtianrui.ticketmanagement.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.yangtianrui.ticketmanagement.R;
import com.example.yangtianrui.ticketmanagement.fragment.AddFlightFragment;
import com.example.yangtianrui.ticketmanagement.fragment.AddTicketFragment;

public class FlightActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTlTabs;
    private ViewPager mVpContents;
    private Fragment[] mFragments = new Fragment[2];
    private String[] mLabels = new String[]{"添加航班", "添加机票"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar_flight);
        mToolbar.setTitle("航班管理");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initData();
        initView();
    }

    private void initData() {
        mFragments[0] = new AddFlightFragment();
        mFragments[1] = new AddTicketFragment();
    }

    private void initView() {
        mTlTabs = (TabLayout) findViewById(R.id.id_tl_tabs);
        mVpContents = (ViewPager) findViewById(R.id.id_vp_content);
        mVpContents.setAdapter(new MyFragAdapter(getSupportFragmentManager()));
        mTlTabs.setupWithViewPager(mVpContents);
    }

    class MyFragAdapter extends FragmentPagerAdapter {
        public MyFragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mLabels[position];
        }

    }

}
