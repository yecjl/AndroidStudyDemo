package com.study.receivebroadpeople;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 功能：
 * Created by danke on 2017/3/20.
 */

public class ProvinceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Log.d("onReceive", "ProvinceBroadcastReceiver: " + resultData);
        abortBroadcast();
        setResultData("Government will get you 50 mushrooms");
    }
}
