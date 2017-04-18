package com.study.readdb;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 功能：
 * Created by danke on 2017/4/17.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<SmsInfo> mNameList;

    public MyAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mNameList != null && mNameList.size() > 0) {
        return mNameList.size();

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
            view = View.inflate(mContext, R.layout.item, null);
        }

        TextView tvName = (TextView) view.findViewById(R.id.tv_name);

        final String name = mNameList.get(position).toString();
        tvName.setText(name);
        return view;
    }

    public void setList(List<SmsInfo> nameList) {
        this.mNameList = nameList;
        notifyDataSetChanged();
    }
}
