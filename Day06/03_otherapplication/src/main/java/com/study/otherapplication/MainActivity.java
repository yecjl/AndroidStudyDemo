package com.study.otherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn01)
    Button btn01;
    @Bind(R.id.btn02)
    Button btn02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn01)
    public void btn01(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.study.twointent", "com.study.twointent.Activity01");
        startActivity(intent);
    }

    @OnClick(R.id.btn02)
    public void btn02(View view) {
        Intent intent = new Intent();
        intent.setAction("com.study.twointent.OPENACTIVITY02");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }
}
