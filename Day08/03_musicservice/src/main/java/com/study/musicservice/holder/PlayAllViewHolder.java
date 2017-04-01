package com.study.musicservice.holder;

import android.view.View;

import com.study.musicservice.model.MusicItem;

import butterknife.ButterKnife;

public class PlayAllViewHolder {

    public PlayAllViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setData(MusicItem data) {
    }
}