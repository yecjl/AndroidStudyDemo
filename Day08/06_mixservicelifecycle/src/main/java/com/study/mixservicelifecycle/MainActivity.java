package com.study.mixservicelifecycle;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    @Bind(R.id.btn_bind_service)
    Button btnBindService;
    @Bind(R.id.btn_call_method)
    Button btnCallMethod;
    @Bind(R.id.btn_unbind_service)
    Button btnUnbindService;

    private IService mIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("bindservicelifecycle", "MainActivity: onServiceConnected");
            mIService = (IService) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("bindservicelifecycle", "MainActivity: onServiceDisconnected");
        }
    };

    @OnClick({R.id.btn_bind_service})
    public void bind_service(View view) {
        Intent intent = new Intent(this, BindService.class);
        bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    @OnClick({R.id.btn_call_method})
    public void call_method(View view) {
        if (mIService != null) {
            mIService.callMethodInService();
        }
    }

    @OnClick({R.id.btn_unbind_service})
    public void unbind_service(View view) {
        if (mConn != null) {
            unbindService(mConn);
            mIService = null;
        }
    }

    @OnClick({R.id.btn_open_service})
    public void open_service(View view) {
        Intent intent = new Intent(this, BindService.class);
        startService(intent);
    }

    @OnClick({R.id.btn_close_service})
    public void close_service(View view) {
        Intent intent = new Intent(this, BindService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConn != null) {
            unbindService(mConn);
            mIService = null;
        }
    }
}
