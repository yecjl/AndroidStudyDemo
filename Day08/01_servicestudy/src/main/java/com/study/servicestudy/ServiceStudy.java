package com.study.servicestudy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 功能：
 * Created by danke on 2017/3/31.
 */

public class ServiceStudy extends Service {
    private boolean isCheck = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 服务第一次创建的时候调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceStudy", "服务被开启， 服务在" + Thread.currentThread().getName() + "线程中运行");
        new Thread(new Runnable() {
            @Override
            public void run() {
                isCheck = true;
                while (isCheck) {
                    Log.d("ServiceStudy", "检查中...");
                    SystemClock.sleep(1000);
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ServiceStudy", "服务被关闭");
        isCheck = false;
    }
}
