package com.project.mobilesafe.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.activities.adapter.HomeAdapter;
import com.project.mobilesafe.bean.HomeContentInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能：主页面
 * Created by danke on 2017/4/18.
 */

public class HomeActivity extends Activity {
    @Bind(R.id.iv_home_icon)
    ImageView ivIcon;
    @Bind(R.id.gv_home_content)
    GridView mGridView;

    private final static String TAG = "HomeActivity";
    @Bind(R.id.iv_home_setting)
    ImageView ivHomeSetting;
    private List<HomeContentInfo> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(ivIcon, "rotationY", new float[]{0, 360});
        rotationY.setDuration(3000);
        rotationY.setRepeatMode(ObjectAnimator.RESTART);
        rotationY.setRepeatCount(ObjectAnimator.INFINITE);
        rotationY.start();

        mList = new ArrayList<>();

        // 通过TypedArray获取图片资源
        TypedArray ta = getResources().obtainTypedArray(R.array.home_icon);
        String[] titleArray = getResources().getStringArray(R.array.home_title);
        String[] descArray = getResources().getStringArray(R.array.home_desc);

        for (int i = 0; i < ta.length(); i++) {
            int resId = ta.getResourceId(i, 0);
            HomeContentInfo info = new HomeContentInfo();
            info.setResId(resId);
            info.setTitle(titleArray[i]);
            info.setDesc(descArray[i]);
            mList.add(info);
        }
        ta.recycle();

        mGridView.post(new Runnable() {
            @Override
            public void run() {
                int width = mGridView.getWidth();
                int height = mGridView.getHeight();
                Log.i(TAG, "width: " + width + ", height: " + height);

                mGridView.setAdapter(new HomeAdapter(HomeActivity.this, height, mList));
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 手机防盗的功能
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setView(R.layout.dialog_password);
                        builder.show();
                        break;
                }
            }
        });
    }

    @OnClick(R.id.iv_home_setting)
    public void setting() {
        SettingActivity.start(this);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}
