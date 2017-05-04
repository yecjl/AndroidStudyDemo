package com.project.mobilesafe.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 功能：服务状态的工具类
 * Created by danke on 2017/5/4.
 */

public class ServiceStatusUtils {
    /**
     * 判断当前服务是否正在运行
     *
     * @param context
     * @param clz     指定服务的class
     * @return
     */
    public static boolean isServiceRunning(Context context, Class clz) {
        // 获取系统的进程管理器
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        // 获取正在运行的服务，maxNum 返回最大集合数，但会按照实际返回
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(200);
        for (int i = 0; i < runningServices.size(); i++) {
            ActivityManager.RunningServiceInfo runningServiceInfo = runningServices.get(i);
            ComponentName service = runningServiceInfo.service;
            Log.i(TAG, "service: " + service);
            if (service.compareTo(new ComponentName(context, clz)) == 0) {
                return true;
            }
        }
        return false;
    }
}
