package com.study.bootcomplete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
