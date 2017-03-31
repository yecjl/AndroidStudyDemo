package com.study.servicestudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_open_service)
    Button btnOpenService;
    @Bind(R.id.btn_close_service)
    Button btnCloseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_open_service, R.id.btn_close_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_service:
                startService(new Intent(this, ServiceStudy.class));
                break;
            case R.id.btn_close_service:
                stopService(new Intent(this, ServiceStudy.class));
                break;
        }
    }
}
