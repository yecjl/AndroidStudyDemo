package com.study.bindservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_open_service)
    Button btnOpenService;
    @Bind(R.id.btn_call_method)
    Button btnCalledMethod;
    @Bind(R.id.btn_close_service)
    Button btnCloseService;
    private IService mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_open_service)
    public void openService(View view) {
        Intent intent = new Intent(this, BindService.class);
        bindService(intent, mSconn, BIND_AUTO_CREATE);
    }

    /**
     * 服务连接
     */
    private ServiceConnection mSconn =  new ServiceConnection() {
        /**
         * 当服务被成功连接的时候调用的方法
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (IService) service;
        }

        /**
         * 当服务失去连接的时候调用的方法
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @OnClick(R.id.btn_call_method)
    public void callMethod(View view) {
        if (mBinder != null) {
            mBinder.callMethodInService("danke");
        }
    }

    @OnClick(R.id.btn_close_service)
    public void closeService(View view) {
        unbindService(mSconn);
    }
}
