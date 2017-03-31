package com.study.receivecctv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 功能：
 * Created by danke on 2017/3/20.
 */

public class CCTVBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getData() != null) {
            Intent launchIntent = new Intent(context, MainActivity.class);
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchIntent.setData(intent.getData());
            context.startActivity(launchIntent);
        }
    }
}
