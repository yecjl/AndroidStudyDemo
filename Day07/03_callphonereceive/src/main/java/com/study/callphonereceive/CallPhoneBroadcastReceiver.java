package com.study.callphonereceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * 功能：
 * Created by danke on 2017/3/17.
 */

public class CallPhoneBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取广播消息，通常为空
        String phone = getResultData();
        SharedPreferences sp = context.getSharedPreferences("config", 0);
        String ip = sp.getString("ip", "");
        // 将数据重新编辑后设置回去
        if (phone.startsWith("0")) {
            setResultData(ip + phone);
        }
    }
}
