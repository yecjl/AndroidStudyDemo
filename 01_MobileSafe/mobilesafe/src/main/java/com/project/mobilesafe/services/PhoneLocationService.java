package com.project.mobilesafe.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobilesafe.db.dao.AddressDao;

/**
 * 功能：电话归属地服务
 * Created by danke on 2017/5/5.
 */

public class PhoneLocationService extends Service {

    private PhoneStateListener listener;
    private TelephonyManager tm;
    private AddressDao dao;
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 监听电话状态变化（来电）
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        dao = new AddressDao(this);
        listener = new MyPhoneStateListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        // 注册外拨电话的广播接受者（去电）
        broadcastReceiver = new OutCallBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(broadcastReceiver, filter);
    }

    /**
     * 电话状态改变的监听器
     */
    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(PhoneLocationService.this, dao.findLocation(incomingNumber), Toast.LENGTH_LONG).show();


                    wm.addView(view, params);
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    /**
     * 外拨电话的广播接受者
     */
    private class OutCallBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String phone = getResultData();
            Toast.makeText(PhoneLocationService.this, dao.findLocation(phone), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
        listener = null;
        unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
    }
}
