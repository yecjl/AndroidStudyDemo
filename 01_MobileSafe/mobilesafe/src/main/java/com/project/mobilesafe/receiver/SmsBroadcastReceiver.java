package com.project.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import com.project.mobilesafe.R;
import com.project.mobilesafe.services.LocationService;

/**
 * 功能：短信接受者
 * Created by danke on 2017/4/25.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Object[] puds = (Object[]) intent.getExtras().get("pdus");
        for (Object pud : puds) {
            SmsMessage sm = SmsMessage.createFromPdu((byte[]) pud);
            String messageBody = sm.getMessageBody();
            switch (messageBody) {
                case "#*location*#":
                    Log.i(TAG, "GPS追踪");
                    context.startService(new Intent(context, LocationService.class));
                    abortBroadcast();
                    break;
                case "#*alarm*#":
                    Log.i(TAG, "播放报警音乐");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.flashman);
                            mediaPlayer.setLooping(true);
                            mediaPlayer.start();
                            abortBroadcast();
                        }
                    }).start();
                    break;
                case "#*wipedata*#":
                    Log.i(TAG, "远程删除数据");
                    abortBroadcast();
                    break;
                case "#*lockscreen*#":
                    Log.i(TAG, "远程锁屏");
                    abortBroadcast();
                    break;
            }
        }
    }
}
