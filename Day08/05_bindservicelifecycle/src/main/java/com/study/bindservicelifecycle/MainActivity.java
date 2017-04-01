package com.study.bindservicelifecycle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_open_service})
    public void open_service(View view) {
        Intent intent = new Intent(this, BindService.class);
        bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("bindservicelifecycle", "MainActivity: onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("bindservicelifecycle", "MainActivity: onServiceDisconnected");
        }
    };

    @OnClick({R.id.btn_close_service})
    public void close_service(View view) {
        unbindService(mConn);
    }
}
