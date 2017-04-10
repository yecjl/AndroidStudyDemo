package com.study.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_notification)
    public void onClick() {
        // 向下兼容的方法
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://18857092891"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.error)
                .setContentText("danke notification")
                .setContentTitle("Hello danke!")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.img_01))
                .setTicker("hi")
                .setContentIntent(pendingIntent)
                .build();
        nm.notify(0, notification);

        // api16以后调用的方法
//        Notification notification = new Notification.Builder(this)
//                .setContentTitle("我是标题")
//                .setContentText("我是文本")
//                .setSmallIcon(R.mipmap.error)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.img_01))
//                .build();
    }
}
