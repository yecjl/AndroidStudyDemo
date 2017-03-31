package com.study.appstatereceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 功能：
 * Created by danke on 2017/3/17.
 */

public class AppStateBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String packageName = intent.getData().toString();
        switch (action) {
            case Intent.ACTION_PACKAGE_ADDED:
                Toast.makeText(context, "【安装】，包名：" + packageName, Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_PACKAGE_REMOVED:
                Toast.makeText(context, "【卸载】，包名：" + packageName, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
