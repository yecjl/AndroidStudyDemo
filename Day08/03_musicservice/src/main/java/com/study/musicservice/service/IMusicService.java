package com.study.musicservice.service;

/**
 * 功能：MusicService中binder 的接口
 * Created by danke on 2017/4/5.
 */

public interface IMusicService {
    /**
     * 播放指定的position
     * @param position
     */
    void callPlay(int position);

    /**
     * 继续播放
     */
    void callContinuePlay();

    /**
     * 暂停播放
     */
    void callPause();
}
