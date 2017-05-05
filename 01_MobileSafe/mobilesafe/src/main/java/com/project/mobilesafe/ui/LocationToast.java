package com.project.mobilesafe.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.WindowManager;
import android.widget.TextView;

import static android.content.Context.WINDOW_SERVICE;

/**
 * 功能：来电，去电号码归属地 显示的吐司
 * Created by danke on 2017/5/5.
 */

public class LocationToast {
    private WindowManager wm;

    public LocationToast(Context context) {
        // 获取窗体管理的服务
        wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);

        TextView view = new TextView(context);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
    }

    public void show() {

    }
}
