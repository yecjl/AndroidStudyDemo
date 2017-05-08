package com.project.mobilesafe.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.project.mobilesafe.R;

/**
 * 功能：来电，去电号码归属地 显示的吐司
 * Created by danke on 2017/5/5.
 */

public class LocationToast {
    private View mNextView;
    private TN mTN;

    private int[] toastBackground = new int[]{R.drawable.shape_blue_bg_radius10, R.drawable.shape_green_bg_radius10,
            R.drawable.shape_orange_bg_radius10, R.drawable.shape_pink_bg_radius10, R.drawable.shape_gray_bg_radius10};

    private LocationToast() {
        mTN = new TN();
    }

    public static LocationToast makeText(Context context, String text, int bgResId) {
        LocationToast toast = new LocationToast();
        View view = View.inflate(context, R.layout.toast_location, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_toast_message);
        tvMessage.setText(text);
        if (bgResId != -1) {
            tvMessage.setBackgroundResource(bgResId);
        }
        toast.mNextView = view;
        return toast;
    }

    public void show() {
        if (mNextView != null) {
            TN tn = mTN;
            tn.mNextView = mNextView;
            tn.show();
        }
    }

    public void hide() {
        if (mNextView != null) {
            TN tn = mTN;
            tn.hide();
        }
    }

    private static class TN{
        WindowManager mWM;
        View mNextView;
        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        TN() {
            final WindowManager.LayoutParams params = mParams;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }

        public void show() {
            Context context = mNextView.getContext().getApplicationContext();
            if (context == null) {
                context = mNextView.getContext();
            }
            mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (mNextView.getParent() != null) {
                mWM.removeView(mNextView);
            }
            mWM.addView(mNextView, mParams);
        }

        public void hide() {
            if (mNextView != null) {
                if (mNextView.getParent() != null) {
                    mWM.removeView(mNextView);
                }
                mNextView = null;
            }
        }
    }
}
