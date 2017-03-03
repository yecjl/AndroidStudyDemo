package com.study.logcat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private String tag = MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(tag, "verbose"); // 提醒
        Log.d(tag, "debug");
        Log.i(tag, "info");
        Log.w(tag, "warm");
        Log.e(tag, "error");
        Log.wtf(tag, "what fuck");
    }
}
