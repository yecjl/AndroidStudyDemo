package com.study.orderbroadgovernment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_broad)
    Button btnBroad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_broad)
    public void onClick() {
        Intent intent = new Intent();
        intent.setAction("com.study.orderbroadgovernment.GIVEMASHROOM");
        sendOrderedBroadcast(intent, null, new ResultBroadcastReceiver(), null, 1, btnBroad.getText().toString(), null);
//        sendBroadcast(intent);  // Exception: trying to return result during a non-ordered broadcast
    }
}
