package com.study.sreenreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 功能：
 * Created by danke on 2017/3/31.
 */

public class ScreenBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case Intent.ACTION_SCREEN_OFF:
                Log.d("ScreenBroadcastReceiver", "锁屏");
                break;
            case Intent.ACTION_SCREEN_ON:
                Log.d("ScreenBroadcastReceiver", "解锁");
                break;
        }
    }
}
