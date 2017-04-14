package com.study.readcontacts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.study.readcontacts.Util.ContactInfo;

import java.util.List;

/**
 * 功能：
 * Created by danke on 2017/4/11.
 */

public class ContactsAdapter extends BaseAdapter {
    private List<ContactInfo> mList;
    private Context mConext;

    public ContactsAdapter(Context context, List<ContactInfo> list) {
        this.mConext = context;
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
        if (convertView == null) {
            view = View.inflate(mConext, R.layout.item_contact, null);
        } else {
            view = convertView;
        }

        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);
        TextView tvEmail = (TextView) view.findViewById(R.id.tv_email);

        if (mList != null && mList.size() > 0) {
            ContactInfo contact = mList.get(position);
            tvName.setText(contact.getName());
            tvPhone.setText(contact.getPhone());
            tvEmail.setText(contact.getEmail());
        }
        return view;
    }

    public void setList(List<ContactInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }
}
