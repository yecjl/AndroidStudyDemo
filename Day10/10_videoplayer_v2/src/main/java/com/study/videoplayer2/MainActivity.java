package com.study.videoplayer2;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.surfaceView)
    SurfaceView surfaceView;

    private SharedPreferences sp;
    private String path = Environment.getExternalStorageDirectory() + "/Danke/movie.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sp = getSharedPreferences("movie", 0);

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            private MediaPlayer mediaPlayer;

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();

                    // 获取之前保存的position
                    mediaPlayer.seekTo(sp.getInt("position", 0));

                    mediaPlayer.start();

                    mediaPlayer.setDisplay(holder);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mediaPlayer != null) {
                    // 获取播放位置并保存
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("position", mediaPlayer.getCurrentPosition());
                    editor.commit();
                    // 释放资源
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });
    }
}
