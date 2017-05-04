package com.project.mobilesafe.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.project.mobilesafe.beans.BlackContact;
import com.project.mobilesafe.db.dao.BlackListDao;
import com.project.mobilesafe.utils.ServiceStatusUtils;

import java.lang.reflect.Method;


/**
 * 功能：黑名单拦截的服务: 电话拦截，短信拦截，全部拦截
 * Created by danke on 2017/5/3.
 */

public class BlackContactInterceptService extends Service {

    private TelephonyManager tm;
    private PhoneStateListener listener;
    private BlackListDao dao;
    private final static String TAG = "BlackContactService";
    private BroadcastReceiver broadcastReceiver; // 短信广播的接收者

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "骚扰拦截已经打开");
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
                                Log.i(TAG, incomingNumber + ": 电话拦截");
                                endCall();
                                deleteCallLog(incomingNumber);
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

        // 注册短信拦截的广播接受者
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Object[] pdus = (Object[]) intent.getExtras().get("pdus");
                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage sm = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    // 判断是否是黑名单用户，拦截短信
                    String address = sm.getOriginatingAddress();
                    BlackListDao dao = new BlackListDao(context);
                    BlackContact blackContact = dao.findBlackContact(address);
                    if (blackContact != null) {
                        // 拦截短信
                        String mode = blackContact.getMode(); // 1、电话拦截 2、短信拦截 3、全部拦截
                        if ("2".equals(mode) || "3".equals(mode)) {
                            Log.i(TAG, address + ": 短信拦截");
                            abortBroadcast();
                        }
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        registerReceiver(broadcastReceiver, filter);
        super.onCreate();
    }

    /**
     * 挂断电话
     */
    private void endCall() {
        // 通过查看TelephonyManager的源码
        try {
            // ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
            Class<?> clazz = getClassLoader().loadClass("android.os.ServiceManager");
            Method method = clazz.getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
            ITelephony iTelephony = ITelephony.Stub.asInterface(binder);
            iTelephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用内容提供者，清除呼叫记录
     *
     * @param incomingNumber 来电黑名单的号码
     */
    private void deleteCallLog(final String incomingNumber) {
        final ContentResolver contentResolver = getContentResolver();
        Uri parse = Uri.parse("content://call_log/calls");

        // 利用内容观察者，观察呼叫记录数据库，如果生成了呼叫记录，就立即清除呼叫记录
        contentResolver.registerContentObserver(parse, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                contentResolver.delete(uri, "number=?", new String[]{incomingNumber});
                contentResolver.unregisterContentObserver(this);
            }
        });
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "骚扰拦截已经关闭");
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
        listener = null;
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
