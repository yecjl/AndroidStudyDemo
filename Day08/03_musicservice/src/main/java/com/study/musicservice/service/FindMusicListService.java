package com.study.musicservice.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.study.musicservice.db.MusicDB;
import com.study.musicservice.model.MusicItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：播放音乐的服务
 * Created by danke on 2017/3/31.
 */

public class FindMusicListService extends Service {
    private List<String> fileTypeList = new ArrayList<>();
    private List<MusicItem> mMusicList = new ArrayList<>();

    public final static String FINDMUSIC_COMPLETION = "FINDMUSIC_COMPLETION";
    private SharedPreferences sp;
    private MusicDB mMusicDB;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sp = getSharedPreferences("music", 0);
        mMusicDB = new MusicDB(this);

        // 设置文件类型
        fileTypeList.add("mp3");
        fileTypeList.add("mp4");
        // 查找文件列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                findMusicList(Environment.getExternalStorageDirectory());

                Intent intent = new Intent(FINDMUSIC_COMPLETION);
                intent.putExtra("musicList", (ArrayList) mMusicList);
                sendBroadcast(intent);

                // 设置为非第一次进入
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLoopMusicFile", false);
                editor.commit();
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 查找所有的 符合类型的文件， 添加到MusicList中，并添加到数据库中
     *
     * @param dirFile
     */
    public void findMusicList(File dirFile) {
        File[] list = dirFile.listFiles();
        for (int i = 0; i < list.length; i++) {
            File file = list[i];
            if (file.exists() && file.length() > 0) {
                if (file.isFile()) {
                    String name = file.getName();
                    String[] nameSplit = name.split("\\.");
                    if (fileTypeList.contains(nameSplit[nameSplit.length - 1])) {
                        MusicItem item = new MusicItem();
                        item.setName(file.getName());
                        item.setPath(file.getAbsolutePath());
                        item.setPlayTimes(0);
                        item.setPlaying(false);
                        item.setPlaySeek(0);
                        // 添加到集合中
                        mMusicList.add(item);
                        // 保存到数据库中
                        mMusicDB.insert(item);

                        Log.d("musicService", "FindMusicListService: MusicItem: " + item.toString());
                    }
                } else if (file.isDirectory()) {
                    findMusicList(file);
                }
            }
        }
    }
}
