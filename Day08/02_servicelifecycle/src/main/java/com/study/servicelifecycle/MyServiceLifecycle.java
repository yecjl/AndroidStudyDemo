package com.study.servicelifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 功能：
 * Created by danke on 2017/3/31.
 */

public class MyServiceLifecycle extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyServiceLifecycle", "service is onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyServiceLifecycle", "service is onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("MyServiceLifecycle", "service is onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyServiceLifecycle", "service is onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyServiceLifecycle", "service is onDestroy");
    }
}
