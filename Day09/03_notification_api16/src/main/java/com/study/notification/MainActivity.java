package com.study.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.videoView)
    VideoView2 videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_notification)
    public void onClick() {
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

//        Notification notification = new Notification.Builder(this)
//                .setContentTitle("我是标题")
//                .setContentText("我是文本")
//                .setSmallIcon(R.mipmap.error)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.img_01))
//                .build();


//        videoView.setVideoPath("https://bqf.kakasure.com/uploadify/uploads/20170406/bd933f292df840fdb472553fa4999e76.mp4");
//        MediaController mc = new MediaController(this);
//        mc.setAnchorView(videoView);
//        videoView.setMediaController(new MediaController(this));
//        videoView.setOnPreparedListener(onPreparedListener);
//        videoView.setMediaController(new MediaController(this));
//        videoView.setOnErrorListener(onErrorListener);
//        videoView.setOnInfoListener(onInfoListener); //2.3//这是第二种监听卡的方式
//        videoView.setOnSeekCompleteListener(onSeekCompleteListener);
    }

//    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
//        @Override
//        public void onPrepared(MediaPlayer mp) {
//            videoView.start();
//        }
//    };
//
//    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
//        @Override
//        public boolean onError(MediaPlayer mp, int what, int extra) {
//            if (videoView != null) {
//                videoView.stopPlayback();
//            }
//            return true;
//        }
//    };
//
//    /**
//     * 监听播放卡、监听开始拖动、监听卡完成、监听拖动缓冲完成。
//     */
//    MediaPlayer.OnInfoListener onInfoListener = new MediaPlayer.OnInfoListener() {
//        @Override
//        public boolean onInfo(MediaPlayer mp, int what, int extra) {
//            switch (what) {
//                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                    //显示卡的View
//                    //监听播放卡、监听开始拖动
//                    Toast.makeText(MainActivity.this, "开始缓冲", 1).show();
//                    break;
//
//                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                    //监听卡完成、监听拖动缓冲完成
//                    Toast.makeText(MainActivity.this, "缓冲结束", 1).show();
//                    break;
//
//                default:
//                    break;
//            }
//
//            return true;
//        }
//    };
//
//
//    /**
//     * 播放完成监听
//     */
//    MediaPlayer.OnSeekCompleteListener onSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
//        @Override
//        public void onSeekComplete(MediaPlayer mp) {
//        }
//    };
}
