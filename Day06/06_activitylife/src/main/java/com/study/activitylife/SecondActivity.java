package com.study.activitylife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能：
 * Created by danke on 2017/3/16.
 */

public class SecondActivity extends AppCompatActivity {
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.btn_open)
    Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tv.setText("SecondActivity");
        btnOpen.setText("setResult 返回MainActivity");
        Log.d("activity_life", "SecondActivity: onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activity_life", "SecondActivity: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activity_life", "SecondActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activity_life", "SecondActivity: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activity_life", "SecondActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity_life", "SecondActivity: onDestroy");
    }

    @OnClick(R.id.btn_open)
    public void onClick() {
        setResult(0, getIntent());
        finish();
    }
}
