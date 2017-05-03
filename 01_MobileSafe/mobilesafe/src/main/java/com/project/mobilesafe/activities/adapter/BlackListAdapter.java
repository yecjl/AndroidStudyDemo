package com.project.mobilesafe.activities.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobilesafe.R;
import com.project.mobilesafe.beans.BlackContact;
import com.project.mobilesafe.db.dao.BlackListDao;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：
 * Created by danke on 2017/5/2.
 */

public class BlackListAdapter extends BaseAdapter {
    private final Context mContext;
    private final BlackListDao mDao;
    private final ImageView mIvEmpty;
    private List<BlackContact> mList;

    public BlackListAdapter(Context context, BlackListDao dao, ImageView ivEmpty) {
        this.mContext = context;
        this.mDao = dao;
        this.mIvEmpty = ivEmpty;
    }

    /**
     * 设置集合
     * @param list
     */
    public void setList(List<BlackContact> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    /**
     * 添加集合数据
     * @param blackContact
     */
    public void add(BlackContact blackContact) {
        mList.add(blackContact);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // 根据mList中是否有数据来显示和隐藏 “空数据提醒”的图片
        if (mList != null && mList.size() > 0) {
            mIvEmpty.setVisibility(View.GONE);
            return mList.size();
        } else {
            mIvEmpty.setVisibility(View.VISIBLE);
            return 0;
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(mContext, R.layout.item_blacklist, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        final BlackContact blackContact = mList.get(position);
        viewHolder.tvPhone.setText(blackContact.getPhone());
        String mode = blackContact.getMode();
        switch (mode) {
            case "1":
                viewHolder.tvMode.setText("电话拦截");
                break;
            case "2":
                viewHolder.tvMode.setText("短信拦截");
                break;
            case "3":
                viewHolder.tvMode.setText("全部拦截");
                break;
        }

        viewHolder.ivRubbish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = mDao.delete(blackContact.getPhone());
                if (result) {
                    mList.remove(blackContact);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_item_black_phone)
        TextView tvPhone;
        @Bind(R.id.tv_item_black_mode)
        TextView tvMode;
        @Bind(R.id.iv_item_black_rubbish)
        ImageView ivRubbish;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
