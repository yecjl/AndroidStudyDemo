package com.project.mobilesafe.activities.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.beans.ContactInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：联系人的adapter
 * Created by danke on 2017/4/21.
 */

public class ContactAdapter extends BaseAdapter {
    private final Context mContext;
    private List<ContactInfo> mContactList;

    public ContactAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<ContactInfo> contactList) {
        this.mContactList = contactList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mContactList != null && mContactList.size() > 0) {
            return mContactList.size();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item_contact, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.setData(mContactList.get(position));

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

    static class ViewHolder {
        @Bind(R.id.tv_contact_name)
        TextView tvName;
        @Bind(R.id.tv_contact_phone)
        TextView tvPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(ContactInfo info) {
            tvName.setText(info.getName());
            tvPhone.setText(info.getPhone());
        }
    }
}
