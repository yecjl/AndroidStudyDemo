package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import butterknife.ButterKnife;

/**
 * 功能：
 * Created by danke on 2017/4/21.
 */

public abstract class SetupWizardBaseActivity extends Activity {
    // 手势识别器
    private GestureDetector mDetector;
    private static final String TAG = "SetupWizardBaseActivity";
    protected SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());
        ButterKnife.bind(this);

        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            /**
             * @param e1 第一次按下
             * @param e2 第二次按下
             * @param velocityX x的移动速度
             * @param velocityY y的移动速度
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < 200) {
                    Log.i(TAG, "x 移动的太慢");
                    return true; // 消费事件，事件到此为止
                }
                if (Math.abs(e1.getRawY() - e2.getRawY()) > 100) {
                    Log.i(TAG, "y 移动的距离过大");
                    return true; // 消费事件，事件到此为止
                }
                if (e1.getRawX() - e2.getRawX() > 200) {
                    nextPage();
                    return true; // 消费事件，事件到此为止
                } else if (e1.getRawX() - e2.getRawX() < -200) {
                    prePage();
                    return true; // 消费事件，事件到此为止
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        sp = getSharedPreferences("config", MODE_PRIVATE);

        initView();
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int setLayoutResId();

    /**
     * 初始化设置
     */
    protected abstract void initView();

    /**
     * 开启下一页
     */
    public abstract void nextPage();

    /**
     * 开启上一页
     */
    public abstract void prePage();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将MotionEvent触摸时间交给手势识别器去处理
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 开启并关闭页面
     * @param cls
     */
    public void startAndFinish(Class<?> cls) {
        startActivity(new Intent(this, cls));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
