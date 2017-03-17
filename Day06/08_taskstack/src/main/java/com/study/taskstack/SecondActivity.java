package com.study.taskstack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SecondActivity extends AppCompatActivity {

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.btn_first)
    Button btnFirst;
    @Bind(R.id.btn_second)
    Button btnSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tv.setText("I'm SecondActivity");

        Log.d("taskstack", "SecondActivity: onCreate");
    }

    @OnClick({R.id.btn_first, R.id.btn_second})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_first:
                Intent intent1 = new Intent(SecondActivity.this, FirstActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_second:
                Intent intent2 = new Intent(SecondActivity.this, SecondActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("taskstack", "SecondActivity: onDestroy");
    }
}
