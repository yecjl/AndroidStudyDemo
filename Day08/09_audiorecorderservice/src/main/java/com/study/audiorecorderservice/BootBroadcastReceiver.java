package com.study.audiorecorderservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 功能：开机启动，电话录音
 * Created by danke on 2017/4/6.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootBroadcastReceiver", "开机启动成功");

        Intent launcher = new Intent(context, PhoneService.class);
        context.startService(launcher);
    }
}
