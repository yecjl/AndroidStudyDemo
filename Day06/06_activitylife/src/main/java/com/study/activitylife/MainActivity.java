package com.study.activitylife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tv.setText("MainActivity");
        Log.d("activity_life", "MainActivity: onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("activity_life", "MainActivity: onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activity_life", "MainActivity: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activity_life", "MainActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activity_life", "MainActivity: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activity_life", "MainActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity_life", "MainActivity: onDestroy");
    }

    @OnClick(R.id.btn_open)
    public void onClick() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("activity_life", "MainActivity: onActivityResult");
    }
}
