package com.study.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * 功能：
 * Created by danke on 2017/4/1.
 */

public class BindService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "服务绑定成功", Toast.LENGTH_SHORT).show();
        return new MyBinder();
    }

    /**
     * 服务内部的中间人
     * 服务中MyBinder 不希望外部可以获取引用，做成私有的
     */
    private class MyBinder extends Binder implements IService {
        /**
         * 中间人帮忙调用服务的方法
         * @param name
         */
        public void callMethodInService(String name) {
            methodInService(name);
        }

        public void play() {
            Toast.makeText(BindService.this, "I like playing badminton", Toast.LENGTH_SHORT).show();
        }

        public void sleep() {
            Toast.makeText(BindService.this, "I like sleeping", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "服务打开成功", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "服务关闭成功", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    /**
     * 在service中的内部方法
     *
     * @param name
     */
    public void methodInService(String name) {
        Toast.makeText(this, "你的名字: " + name, Toast.LENGTH_SHORT).show();
    }
}
