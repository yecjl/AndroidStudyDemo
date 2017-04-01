package com.study.musicservice.model;

import android.os.Parcel;

import java.io.Serializable;

/**
 * 功能：播放音乐的Bean
 * Created by danke on 2017/3/31.
 */

public class MusicItem implements Serializable {
    /**
     * 音乐名字
     */
    private String name;
    /**
     * 音乐路径
     */
    private String path;
    /**
     * 播放次数
     */
    private int playTimes;
    /**
     * 是否正在播放
     */
    private boolean isPlaying;
    /**
     * 播放到哪个位置
     */
    private int playSeek;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getPlaySeek() {
        return playSeek;
    }

    public void setPlaySeek(int playSeek) {
        this.playSeek = playSeek;
    }

    @Override
    public String toString() {
        return "MusicItem{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", playTimes='" + playTimes + '\'' +
                ", isPlaying='" + isPlaying + '\'' +
                ", playSeek='" + playSeek + '\'' +
                '}';
    }
}
