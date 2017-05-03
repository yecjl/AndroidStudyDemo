package com.project.mobilesafe.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 功能：开机启动完成的监听
 * Created by danke on 2017/4/25.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BootBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean isOpenProtect = sp.getBoolean("isOpenProtect", false);

        if (isOpenProtect) {
            Log.i(TAG, "防盗保护开启，检测绑定的sim卡是否一致");

            String sim = sp.getString("sim", null); // 用户绑定的sim卡
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String realSim = tm.getSimSerialNumber() + "123";
            Log.i(TAG, "sim: " + sim + ", realSim: " + realSim);

            if (realSim.equals(sim)) {
                Log.i(TAG, "SIM卡一致，还是您的手机");
            } else {
                Log.i(TAG, "SIM卡不一致，手机可能被盗，发送报警短信");

                String safePhone = sp.getString("safePhone", null);
                SmsManager sm = SmsManager.getDefault();
                sm.sendTextMessage(safePhone, null, "【Katrina mobileSafe】检测到您朋友的手机可能被盗了", null, null);
            }
        }
    }
}
