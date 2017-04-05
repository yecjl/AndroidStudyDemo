package com.study.alipayservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * 功能：支付宝的远程服务
 * Created by danke on 2017/4/5.
 */

public class AlipayService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    private class MyBinder extends IService.Stub {

        @Override
        public int callSafePay(String username, String password, int money) throws RemoteException {
            return safePay(username, password, money);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 使用支付宝的安全支付
     * @param username 用户名
     * @param password 密码
     * @param money 支付金额
     * @return 200 支付成功， 404 用户名或者密码无效， 250 账上余额不足
     */
    public int safePay(String username, String password, int money) {
        if ("danke".equals(username) && "123".equals(password)) {
            if (money > 400) {
                return 250;
            } else {
                return 200;
            }
        } else {
            return 404;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
