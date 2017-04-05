package com.study.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * 功能：
 * Created by danke on 2017/4/5.
 */

public class RemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    private class MyBind extends IService.Stub{
        @Override
        public void callMethodInService() throws RemoteException {
            methodInService();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("07_remoteservice", "RemoteService: onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("07_remoteservice", "RemoteService: onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("07_remoteservice", "RemoteService: onDestroy");
    }

    public void methodInService() {
        Log.d("07_remoteservice", "RemoteService: methodInService");
        Toast.makeText(this, "我是远程服务的方法", Toast.LENGTH_SHORT).show();
    }
}
