package com.study.sdreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 功能：SD卡的广播接受者
 * Created by danke on 2017/3/17.
 */

public class SDUNBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "SD卡已经弹出，部分功能不可使用", Toast.LENGTH_SHORT).show();
    }
}
