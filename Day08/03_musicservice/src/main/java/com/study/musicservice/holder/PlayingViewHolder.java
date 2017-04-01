package com.study.musicservice.holder;

import android.view.View;
import android.widget.TextView;

import com.study.musicservice.model.MusicItem;
import com.study.musicservice.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayingViewHolder {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_play_times)
    TextView tvPlayTimes;

    public PlayingViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setData(MusicItem data) {
        tvName.setText(data.getName());
        tvPlayTimes.setText("播放" + data.getPlayTimes() + "0次");
    }
}