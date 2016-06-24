package com.example.yangtianrui.ticketmanagement;


import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
    }


    /**
     * 初始化数据库,从Assets资源中获取
     */
    private void initDB() {
        String DB_DIR_PATH = "/data/data/" + this.getPackageName() + "/databases";
        // 初始化数据库
        if (!new File(DB_DIR_PATH + "/ticket_db.db3").exists()) {
            try {
                new File(DB_DIR_PATH).mkdir();
                FileOutputStream fos = new FileOutputStream(DB_DIR_PATH + "/ticket_db.db3");
                InputStream is = getAssets().open("ticket_db.db3");
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                is.close();
                fos.close();
            } catch (IOException e) {
                Log.v("LOG", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}