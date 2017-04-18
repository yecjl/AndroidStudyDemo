package com.project.mobilesafe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.utils.PackageUtils;
import com.project.mobilesafe.utils.StatusBarUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    @Bind(R.id.tv_splash_version)
    TextView tvSplashVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorBackground);

        tvSplashVersion.setText("版本号：" + PackageUtils.getVersionName(this));
    }
}
