package com.study.musicservice.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.study.musicservice.model.MusicItem;

import java.io.IOException;
import java.util.List;

/**
 * 功能：播放音乐的服务
 * Created by danke on 2017/3/31.
 */

public class MusicService2 extends Service {
    // Activity 开启服务使用的事件
    public final static String ACTION_INIT_MUSIC_LIST = "ACTION_INIT_MUSIC_LIST";
    public final static String ACTION_PLAY = "ACTION_PLAY";
    public final static String ACTION_PAUSE = "ACTION_PAUSE";
    public final static String ACTION_STOP = "ACTION_STOP";
    public final static String ACTION_NEXT = "ACTION_NEXT";

    // service发送广播使用的事件
    public final static String MUSICSERVICE_ACTION_PLAY = "MUSICSERVICE_ACTION_PLAY";

    private MediaPlayer mMediaPlayer;
    private List<MusicItem> mMusicList;
    private int mCurrentPosition = -1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        // 音乐播放完成监听
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int size = mMusicList.size();
                if (mMusicList != null && size > 0) {
                    mCurrentPosition = (mCurrentPosition + 1) % size;
                    playPrepare(mMusicList.get(mCurrentPosition).getPath());
                    // 发送播放音乐的广播
                    sendPlayBroadcast();
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            Log.d("MusicService", action);
            switch (action) {
                case ACTION_INIT_MUSIC_LIST: // 初始化音乐列表
                    // 获取播放列表
                    mMusicList = (List<MusicItem>) intent.getSerializableExtra("musicList");
                    // 判断是否在播放，发送播放音乐的广播
                    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                        sendPlayBroadcast();
                    }
                    break;
                case ACTION_PLAY: // 播放音乐
                    if (mMusicList != null && mMusicList.size() > 0) {
                        mCurrentPosition = intent.getIntExtra("position", 0);
                        playPrepare(mMusicList.get(mCurrentPosition).getPath());
                        // 发送播放音乐的广播
                        sendPlayBroadcast();
                        play();
                    }
                    break;
                case ACTION_PAUSE: // 暂停音乐
                    pause();
                    break;
                case ACTION_STOP: // 停止音乐
                    stop();
                    break;
                case ACTION_NEXT: // 播放下一个
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }



    /**
     * 播放前的准备
     *
     * @param path
     */
    public void playPrepare(String path) {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.reset();
            }
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放视频
     */
    public void play() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.stop();
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送播放音乐的广播
     */
    public void sendPlayBroadcast() {
        Intent playIntent = new Intent(MUSICSERVICE_ACTION_PLAY);
        playIntent.putExtra("position", mCurrentPosition);
        sendBroadcast(playIntent);
    }

    /**
     * 释放资源
     */
    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        super.onDestroy();
    }
}
