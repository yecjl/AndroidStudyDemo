package com.project.mobilesafe.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.project.mobilesafe.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：
 * Created by danke on 2017/4/18.
 */

public class MainActivity extends Activity {
    @Bind(R.id.iv_main_icon)
    ImageView ivMainIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(ivMainIcon, "rotationY", new float[]{0, 360});
        rotationY.setDuration(3000);
        rotationY.setRepeatMode(ObjectAnimator.RESTART);
        rotationY.setRepeatCount(ObjectAnimator.INFINITE);
        rotationY.start();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
