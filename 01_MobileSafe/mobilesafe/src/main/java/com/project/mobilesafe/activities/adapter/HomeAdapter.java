package com.project.mobilesafe.activities.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.bean.HomeContentInfo;

import java.util.List;

/**
 * 功能：
 * Created by danke on 2017/4/19.
 */

public class HomeAdapter extends BaseAdapter {
    private final Context mContext;
    private final int mHeight;
    private final List<HomeContentInfo> mList;

    public HomeAdapter(Context context, int height, List<HomeContentInfo> list) {
        this.mContext = context;
        this.mHeight = height;
        this.mList = list;
    }

    @Override
    public int getCount() {
        if (mList != null && mList.size() > 0) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = View.inflate(mContext, R.layout.item_home, null);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight / (mList.size() / 2));
            view.setLayoutParams(layoutParams);
        }

        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_home_icon);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_home_title);
        TextView tvDesc = (TextView) view.findViewById(R.id.tv_home_desc);

        HomeContentInfo info = mList.get(position);
        ivIcon.setImageResource(info.getResId());
        tvTitle.setText(info.getTitle());
        tvDesc.setText(info.getDesc());

        return view;
    }
}
