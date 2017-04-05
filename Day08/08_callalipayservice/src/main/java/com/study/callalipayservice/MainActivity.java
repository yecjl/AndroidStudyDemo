package com.study.callalipayservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.study.alipayservice.IService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private IService mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Intent intent = new Intent();
        intent.setAction("com.study.alipayservice.ALIPAY");
        bindService(intent, mConn, BIND_AUTO_CREATE);

    }

    @OnClick(R.id.btn_pay)
    public void onClick() {
        if (mBinder != null) {
            try {
                    int resultCode = mBinder.callSafePay("danke", "123", 200);
                    switch (resultCode) {
                        case 200:
                            Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            break;
                        case 250:
                            Toast.makeText(MainActivity.this, "账户余额不足", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(MainActivity.this, "用户名或者密码无效", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
            }
        }
    }

    private ServiceConnection mConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = IService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
