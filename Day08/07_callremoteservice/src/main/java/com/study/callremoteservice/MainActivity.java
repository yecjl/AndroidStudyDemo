package com.study.callremoteservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.study.remoteservice.IService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_bind)
    Button btnBind;
    @Bind(R.id.btn_call)
    Button btnCall;
    @Bind(R.id.btn_unbind)
    Button btnUnbind;
    private IService mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_bind, R.id.btn_call, R.id.btn_unbind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bind:
                Intent bindIntent = new Intent();
                bindIntent.setAction("com.study.remoteservice.REMOTESERVICE");
                bindService(bindIntent, mConn, BIND_AUTO_CREATE);
                break;
            case R.id.btn_call:
                try {
                    mBinder.callMethodInService();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_unbind:
                unbindService(mConn);
                break;
        }
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = IService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
