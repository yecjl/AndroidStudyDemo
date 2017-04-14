package com.study.shootplay;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_shoot)
    Button btnShoot;
    private SoundPool soundPool;
    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(this, R.raw.shoot, 1);
    }

    @OnClick(R.id.btn_shoot)
    public void onClick() {
        soundPool.play(soundId, 1f, 1f, 0, 0, 2.0f);
    }
}
