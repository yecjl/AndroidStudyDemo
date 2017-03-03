package com.study.settingcenter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.cb)
    CheckBox cb;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sp = getSharedPreferences("config", MODE_PRIVATE);

        // seekBar 需要设置最大值
        seekBar.setMax(100);

        // 设置回显(需要在setOnCheckedChangeListener这个监听之前，否则会调用onCheckedChanged的方法)
        cb.setChecked(sp.getBoolean("isChecked", false));
        seekBar.setProgress(sp.getInt("progress", 0));

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isChecked", isChecked);
                editor.commit();
                Toast.makeText(MainActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动过程中调用的方法
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            /**
             * 开始拖动调用的方法
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * 结束拖动调用的方法
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("progress", seekBar.getProgress());
                editor.commit();
            }
        });
    }
}
