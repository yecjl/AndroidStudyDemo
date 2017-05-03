package com.project.mobilesafe.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.project.mobilesafe.beans.BlackContact;
import com.project.mobilesafe.db.dao.BlackListDao;


/**
 * 功能：黑名单拦截的服务: 电话拦截，短信拦截，全部拦截
 * Created by danke on 2017/5/3.
 */

public class BlackContactInterceptService extends Service {

    private TelephonyManager tm;
    private PhoneStateListener listener;
    private BlackListDao dao;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        dao = new BlackListDao(this);

        // 注册电话状态的监听器
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE: // 空闲状态
                        break;
                    case TelephonyManager.CALL_STATE_RINGING: // 响铃状态
                        // 判断是否为拦截的电话
                        BlackContact blackContact = dao.findBlackContact(incomingNumber);
                        if (blackContact != null) {
                            // 拦截电话
                            String mode = blackContact.getMode(); // 1、电话拦截 2、短信拦截 3、全部拦截
                            if ("1".equals(mode) || "3".equals(mode)) {

                            }
                        }
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK: // 接通电话状态
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        super.onCreate();
    }



    @Override
    public void onDestroy() {
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
        listener = null;
        super.onDestroy();
    }
}
