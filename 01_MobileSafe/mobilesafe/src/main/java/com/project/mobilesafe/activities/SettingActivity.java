package com.project.mobilesafe.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.android.internal.telephony.ITelephony;
import com.project.mobilesafe.R;
import com.project.mobilesafe.services.BlackContactInterceptService;
import com.project.mobilesafe.services.PhoneLocationService;
import com.project.mobilesafe.ui.SetItemView;
import com.project.mobilesafe.utils.ServiceStatusUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：设置页面
 * Created by danke on 2017/4/19.
 */

public class SettingActivity extends Activity {
    @Bind(R.id.isv_autoUpload)
    SetItemView isvAutoUpload;
    @Bind(R.id.isv_intercept)
    SetItemView isvIntercept;
    @Bind(R.id.isv_locationSet)
    SetItemView isvLocationSet;
    @Bind(R.id.isv_locationStyle)
    SetItemView isvLocationStyle;
    @Bind(R.id.isv_watchDog)
    SetItemView isvWatchDog;

    private static final String TAG = "SettingActivity";
    private SharedPreferences config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        isvAutoUpload.setListener(onItemClickListener);
        isvIntercept.setListener(onItemClickListener);
        isvLocationSet.setListener(onItemClickListener);
        isvLocationStyle.setListener(onItemClickListener);
        isvWatchDog.setListener(onItemClickListener);

        // 将数据回显
        config = getSharedPreferences("config", MODE_PRIVATE);
        isvAutoUpload.setStatus(config.getBoolean("autoUpdate", true));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 判断服务是否开启，设置按钮状态
        isvIntercept.setStatus(ServiceStatusUtils.isServiceRunning(this, BlackContactInterceptService.class));
        isvLocationSet.setStatus(ServiceStatusUtils.isServiceRunning(this, PhoneLocationService.class));
    }

    private SetItemView.OnItemClickListener onItemClickListener = new SetItemView.OnItemClickListener() {
        @Override
        public void onClick(View v, boolean status) {
            Log.i(TAG, "id: " + v.getId() + ", status: " + status);
            switch (v.getId()) {
                case R.id.isv_autoUpload: // 自动更新设置
                    SharedPreferences.Editor edit = config.edit();
                    edit.putBoolean("autoUpdate", status);
                    edit.commit();
                    break;
                case R.id.isv_intercept: // 骚扰拦截设置
                    Intent serviceIntercept = new Intent(SettingActivity.this, BlackContactInterceptService.class);
                    if (status) {
                        startService(serviceIntercept);
                    } else {
                        stopService(serviceIntercept);
                    }
                    break;
                case R.id.isv_locationSet: // 归属地显示设置
                    Intent serviceLocation = new Intent(SettingActivity.this, PhoneLocationService.class);
                    if (status) {
                        startService(serviceLocation);
                    } else {
                        stopService(serviceLocation);
                    }
                    break;
                case R.id.isv_locationStyle: // 归属地显示风格
                    break;
                case R.id.isv_watchDog: // 程序锁看门狗服务
                    break;
            }
        }
    };

    public static void start(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }
}
