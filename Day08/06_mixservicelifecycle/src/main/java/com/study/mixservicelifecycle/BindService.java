package com.study.mixservicelifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * 功能：
 * Created by danke on 2017/4/1.
 */

public class BindService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("bindservicelifecycle", "BindService: onBind");
        return new MyBinder();
    }

    private class MyBinder extends Binder implements IService {
        @Override
        public void callMethodInService() {
            methodInService();
        }
    }

    public void methodInService() {
        Toast.makeText(this, "我是service中的方法！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("bindservicelifecycle", "BindService: onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("bindservicelifecycle", "BindService: onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("bindservicelifecycle", "BindService: onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("bindservicelifecycle", "BindService: onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("bindservicelifecycle", "BindService: onDestroy");
    }
}
