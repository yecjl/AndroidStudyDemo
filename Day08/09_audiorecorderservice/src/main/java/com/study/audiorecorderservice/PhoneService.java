package com.study.audiorecorderservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * 功能：
 * Created by danke on 2017/4/6.
 */

public class PhoneService extends Service {

    private TelephonyManager mtm;
    private MediaRecorder mRecorder;
    private String mFileName;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mtm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mtm.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mtm.listen(mListener, PhoneStateListener.LISTEN_NONE);
    }

    public PhoneService() {
        File save = new File(Environment.getExternalStorageDirectory()
                + File.separator + "Danke" + File.separator + "Recorder");
        if (!save.exists()) {
            save.mkdirs();
        }
        mFileName = save.getAbsolutePath()  + File.separator + SystemClock.uptimeMillis() + ".3gp";
    }

    private PhoneStateListener mListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: // 处于空闲状态
                    if (mRecorder != null) {
                        stopRecording();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK: // 通话中
                    startRecording();
                    break;
                case TelephonyManager.CALL_STATE_RINGING: // 响应中
                    break;
            }
        }
    };

    /**
     * 开启录音
     */
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("BootBroadcastReceiver", "prepare() failed");
        }

        mRecorder.start();
    }

    /**
     * 关闭录音
     */
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
