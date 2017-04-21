package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.project.mobilesafe.R;
import com.project.mobilesafe.ui.SetItemView;

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

    private SetItemView.OnItemClickListener onItemClickListener = new SetItemView.OnItemClickListener() {
        @Override
        public void onClick(View v, boolean status) {
            Log.i(TAG, "id: " + v.getId() + ", status: " + status);
            switch (v.getId()) {
                case R.id.isv_autoUpload:
                    SharedPreferences.Editor edit = config.edit();
                    edit.putBoolean("autoUpdate", status);
                    edit.commit();
                    break;
                case R.id.isv_intercept:
                    break;
                case R.id.isv_locationSet:
                    break;
                case R.id.isv_locationStyle:
                    break;
                case R.id.isv_watchDog:
                    break;
            }
        }
    };

    public static void start(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }
}
