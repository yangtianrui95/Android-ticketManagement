package com.example.yangtianrui.ticketmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.yangtianrui.ticketmanagement.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvImg;
    private Button mBtnFlight;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
        Animation animation = new AlphaAnimation(0.5f, 1.0f);
        animation.setDuration(3000);
        mIvImg.setAnimation(animation);
        mBtnLogin.setOnClickListener(this);
        mBtnFlight.setOnClickListener(this);
    }

    private void initView() {
        mIvImg = (ImageView) findViewById(R.id.id_iv_spalsh);
        mBtnFlight = (Button) findViewById(R.id.id_btn_flight_manager);
        mBtnLogin = (Button) findViewById(R.id.id_btn_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_flight_manager:
                Intent intent = new Intent(this, FlightActivity.class);
                startActivity(intent);
                break;
            case R.id.id_btn_login:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
