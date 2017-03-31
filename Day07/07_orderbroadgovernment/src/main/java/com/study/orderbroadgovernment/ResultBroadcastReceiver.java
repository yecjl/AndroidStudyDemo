package com.study.orderbroadgovernment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 功能：
 * Created by danke on 2017/3/20.
 */

public class ResultBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Log.d("onReceive", "ResultBroadcastReceiver: " + resultData);
    }
}
