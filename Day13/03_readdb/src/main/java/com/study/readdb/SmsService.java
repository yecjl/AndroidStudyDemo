package com.study.readdb;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.stericson.RootTools.RootTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by danke on 2017/4/17.
 */

public class SmsService extends Service {
    private List<SmsInfo> mNameList;
    public final static String READ_SMS_SUCCESS = "READ_SMS_SUCCESS";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNameList = new ArrayList<>();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String dbPath = "data/data/com.android.providers.telephony/databases/mmssms.db";
                RootTools.sendShell("chmod 777 " + dbPath, 30000);
                SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = db.query("sms", new String[]{"address", "date", "body", "type"}, null, null, null, null, null);
                mNameList.clear();
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String address = cursor.getString(0);
                        String date = cursor.getString(1);
                        String body = cursor.getString(2);
                        String type = cursor.getString(3);

                        SmsInfo smsInfo = new SmsInfo();
                        smsInfo.setAddress(address);
                        smsInfo.setDate(date);
                        smsInfo.setBody(body);
                        smsInfo.setType(type);
                        mNameList.add(smsInfo);
                    }
                }
                cursor.close();
                db.close();
                RootTools.sendShell("chmod 660 " + dbPath, 30000); //rw-rw----

                // 发送广播，数据读取到了
                Intent readIntent = new Intent(READ_SMS_SUCCESS);
                readIntent.putExtra("nameList", (ArrayList<SmsInfo>)mNameList);
                sendBroadcast(readIntent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
    }
}
