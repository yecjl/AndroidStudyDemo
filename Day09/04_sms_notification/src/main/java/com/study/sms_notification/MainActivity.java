package com.study.sms_notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.btn_send)
    Button btnSend;
    private NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_send)
    public void onClick() {
        // 利用内容提供者，向短信数据库中添加一条数据
        String content = "获得财产10000000元，账户余额还剩233400000000元";
        ContentResolver resolver = getContentResolver();
        Uri url = Uri.parse("content://sms");
        ContentValues values = new ContentValues();
        values.put("address", "95955");
        values.put("date", System.currentTimeMillis());
        values.put("type", 1);
        values.put("body", content);
        resolver.insert(url, values);

        // 打开短信界面的Intent
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/mms");

        // 远程延迟意图，让Notification打开短信界面
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // 创建Notification
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(content)
                .setTicker(content)
                .setSmallIcon(R.mipmap.error)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.error))
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults = Notification.DEFAULT_SOUND; // 设置默认的声音
        // 获取系统服务NotificationManager来显示Notification
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);
    }
}
