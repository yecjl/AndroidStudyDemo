package com.study.musicservice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.study.musicservice.model.MusicItem;
import com.study.musicservice.R;
import com.study.musicservice.holder.MusicViewHolder;
import com.study.musicservice.holder.PlayAllViewHolder;
import com.study.musicservice.holder.PlayingViewHolder;

import java.util.List;

/**
 * 功能：显示Music列表的数据适配器
 * Created by danke on 2017/3/31.
 */

public class MusicAdapter extends BaseAdapter {
    private Context mContext;
    private List<MusicItem> mMusicList;

    // 全部播放
    private static final int TYPE_PLAY_ALL = 0;
    // 正在播放
    private static final int TYPE_PLAYING = 1;
    // 显示条目
    private static final int TYPE_MUSIC_ITEM = 2;

    public MusicAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<MusicItem> musicList) {
        this.mMusicList = musicList;
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_PLAY_ALL;
        }
        MusicItem musicItem = mMusicList.get(position - 1);
        if (musicItem.isPlaying()) {
            return TYPE_PLAYING;
        }
        return TYPE_MUSIC_ITEM;
    }

    @Override
    public int getCount() {
        if (mMusicList != null && mMusicList.size() > 0) {
            return mMusicList.size() + 1;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        View view = null;
        PlayAllViewHolder playAllViewHolder = null;
        MusicViewHolder musicViewHolder = null;
        PlayingViewHolder playingViewHolder = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_PLAY_ALL:
                    view = View.inflate(mContext, R.layout.item_music_playall, null);
                    playAllViewHolder = new PlayAllViewHolder(view);
                    view.setTag(playAllViewHolder);
                    break;
                case TYPE_PLAYING:
                    view = View.inflate(mContext, R.layout.item_music_playing, null);
                    playingViewHolder = new PlayingViewHolder(view);
                    view.setTag(playingViewHolder);
                    break;
                case TYPE_MUSIC_ITEM:
                    view = View.inflate(mContext, R.layout.item_music, null);
                    musicViewHolder = new MusicViewHolder(view);
                    view.setTag(musicViewHolder);
                    break;
            }
        } else {
            view = convertView;
            switch (type) {
                case TYPE_PLAY_ALL:
                    playAllViewHolder = (PlayAllViewHolder) view.getTag();
                    break;
                case TYPE_PLAYING:
                    playingViewHolder = (PlayingViewHolder) view.getTag();
                    break;
                case TYPE_MUSIC_ITEM:
                    musicViewHolder = (MusicViewHolder) view.getTag();
                    break;
            }
        }
        if (mMusicList != null && mMusicList.size() > 0 && playAllViewHolder != null) {
            playAllViewHolder.setData(null);
        }
        if (mMusicList != null && mMusicList.size() > 0 && playingViewHolder != null) {
            playingViewHolder.setData(mMusicList.get(position - 1));
        }
        if (mMusicList != null && mMusicList.size() > 0 && musicViewHolder != null) {
            musicViewHolder.setData(mMusicList.get(position - 1));
        }
        return view;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
