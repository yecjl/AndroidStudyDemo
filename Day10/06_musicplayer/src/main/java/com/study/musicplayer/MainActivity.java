package com.study.musicplayer;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_path)
    EditText etPath;
    @Bind(R.id.btn_play)
    Button btnPlay;
    @Bind(R.id.btn_pause)
    Button btnPause;
    @Bind(R.id.btn_stop)
    Button btnStop;
    private MediaPlayer mMediaPlayer;

    //    private String path = Environment.getExternalStorageDirectory() + "/Danke/movie.mp4";
    private String path = "http://96.f.1ting.com/58ef1ba1/e3e35f5c3bf8e4cc43d8d518f93b50de/zzzzzmp3/2017dApr/13X/13c_Will/01.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        etPath.setText(path);
    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                try {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.show();
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(path);
                    mMediaPlayer.prepareAsync();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            progressDialog.dismiss();
                            mMediaPlayer.start();
                        }
                    });
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Toast.makeText(MainActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            Toast.makeText(MainActivity.this, "播放失败：" + what, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_pause:
                if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    } else {
                        mMediaPlayer.start();
                    }
                }
                break;
            case R.id.btn_stop:
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                break;
        }
    }
}
