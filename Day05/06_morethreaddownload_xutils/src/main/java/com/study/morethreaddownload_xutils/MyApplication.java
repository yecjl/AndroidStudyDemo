package com.study.morethreaddownload_xutils;

import android.app.Application;

import org.xutils.x;

/**
 * 功能：
 * Created by danke on 2017/3/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
