package com.study.musicservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.musicservice.adapter.MusicAdapter;
import com.study.musicservice.db.MusicDB;
import com.study.musicservice.model.MusicItem;
import com.study.musicservice.service.FindMusicListService;
import com.study.musicservice.service.IMusicService;
import com.study.musicservice.service.MusicService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.lv_music)
    ListView mLvMusic;
    @Bind(R.id.iv_pic)
    ImageView ivPic;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.iv_play_pause)
    ImageView ivPlayPause;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.ll_progress)
    LinearLayout llProgress;
    private boolean isFirst = true;
    private MusicAdapter mAdapter;
    private List<MusicItem> mMusicList;

    private final static int MESSAGE_FIND_SUCCESS = 0;
    private final static int MESSAGE_FIND_ERROE = 1;
    private SharedPreferences sp;
    private MusicDB mMusicDB;
    private FindMusicBroadcastReceiver mFindMusicReceiver;
    private PlayMusicBroadcastReceiver mPlayMusicReceiver;
    private int mCurrentPosition = -1;
    private IMusicService mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new MusicAdapter(this);
        mLvMusic.setAdapter(mAdapter);

        // 显示加载中...
        llProgress.setVisibility(View.VISIBLE);

        // 判断是否遍历过文件系统
        sp = getSharedPreferences("music", 0);
        boolean isLoopMusicFile = sp.getBoolean("isLoopMusicFile", true);

        if (isLoopMusicFile) {
            // 开启音乐查找的Service
            Intent intent = new Intent(this, FindMusicListService.class);
            startService(intent);

            // 注册找到音乐列表的广播接收器
            mFindMusicReceiver = new FindMusicBroadcastReceiver();
            IntentFilter findMusicFilter = new IntentFilter();
            findMusicFilter.addAction(FindMusicListService.FINDMUSIC_COMPLETION);
            registerReceiver(mFindMusicReceiver, findMusicFilter);

        } else {
            mMusicDB = new MusicDB(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mMusicList = mMusicDB.findMusicAll();
                    Message message = mHandler.obtainMessage(MESSAGE_FIND_SUCCESS);
                    mHandler.sendMessage(message);
                }
            }).start();
        }

        // MusicService播放音乐: 开启服务 + bind服务
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
        bindService(intent, mConn, BIND_AUTO_CREATE);

        mLvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mMusicList != null && mMusicList.size() > 0) {
                    // 设置当前播放的position
                    int currentPosition = 0;
                    if (position != 0) {
                        currentPosition = position - 1;
                    }
                    if (mBinder != null) {
                        mBinder.callPlay(currentPosition);
                    }
                }
            }
        });

        // 注册播放广播
        mPlayMusicReceiver = new PlayMusicBroadcastReceiver();
        IntentFilter playMusicFilter = new IntentFilter();
        playMusicFilter.addAction(MusicService.MUSICSERVICE_ACTION_PLAY);
        registerReceiver(mPlayMusicReceiver, playMusicFilter);
    }

    /**
     * 消息处理
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_FIND_SUCCESS: // 数据获取完毕
                    // 隐藏正在加载， 设置adapter数据
                    llProgress.setVisibility(View.GONE);
                    mAdapter.setList(mMusicList);

                    // 当数据填充完成后，开启播放音乐的服务，初始化音乐列表
                    Intent intent = new Intent(MainActivity.this, MusicService.class);
                    intent.setAction(MusicService.ACTION_INIT_MUSIC_LIST);
                    intent.putExtra("musicList", (ArrayList) mMusicList);
                    startService(intent);
                    break;
            }
        }
    };

    /**
     * 绑定MusicService的Binder
     */
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (IMusicService) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 找到音乐列表的广播接收器
     */
    private class FindMusicBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取传递的数据
            mMusicList = (List<MusicItem>) intent.getSerializableExtra("musicList");
            // 发送给Handler 统一处理
            Message message = mHandler.obtainMessage(MESSAGE_FIND_SUCCESS);
            mHandler.sendMessage(message);
        }
    }

    /**
     * 播放音乐的广播接受者
     */
    private class PlayMusicBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case MusicService.MUSICSERVICE_ACTION_PLAY: // 播放音乐
                    // 将上一个数据设置为不播放状态
                    if (mCurrentPosition != -1) {
                        MusicItem musicItem = mMusicList.get(mCurrentPosition);
                        musicItem.setPlaying(false);
                    }
                    // 更新新的正在播放的position
                    mCurrentPosition = intent.getIntExtra("position", 0);
                    // 更新Adapter数据
                    MusicItem musicItem = mMusicList.get(mCurrentPosition);
                    musicItem.setPlaying(true);
                    mAdapter.notifyDataSetChanged();
                    // 底部数据更新
                    tvName.setText(musicItem.getName());
                    ivPlayPause.setImageResource(R.mipmap.pause2);
                    break;
                case MusicService.MUSICSERVICE_ACTION_PAUSE: // 暂停音乐:
                    ivPlayPause.setImageResource(R.mipmap.play2);
                    break;
            }
        }
    }


    @OnClick({R.id.iv_play_pause, R.id.iv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play_pause:
                if (mBinder != null && mMusicList != null && mMusicList.size() > 0) {
                    MusicItem musicItem = mMusicList.get(mCurrentPosition);
                    if (musicItem.isPlaying()) {
                        mBinder.callPause();
                    } else {
                        mBinder.callContinuePlay();
                    }
                }
                break;
            case R.id.iv_next:
                if (mBinder != null && mMusicList != null && mMusicList.size() > 0) {
                    int size = mMusicList.size();
                    mCurrentPosition = (mCurrentPosition + 1) % size;
                    mBinder.callPlay(mCurrentPosition);
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        if (mFindMusicReceiver != null) {
            unregisterReceiver(mFindMusicReceiver);
        }

        if (mConn != null) {
            unbindService(mConn);
            mBinder = null;
        }
        super.onDestroy();
    }
}
