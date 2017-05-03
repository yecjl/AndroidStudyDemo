package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.activities.adapter.ContactAdapter;
import com.project.mobilesafe.beans.ContactInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：显示安全号码界面
 * Created by danke on 2017/4/21.
 */

public class ContactActivity extends Activity {

    @Bind(R.id.lv_contact)
    ListView mListView;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    @Bind(R.id.ll_no_data)
    LinearLayout llNoData;
    private List<ContactInfo> mContactList;
    private static final int READ_SUCCEED = 1;
    private static final int READ_EMPTY = 2;

    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        mContactList = new ArrayList<>();
        mAdapter = new ContactAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mContactList != null && mContactList.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("phone", mContactList.get(position).getPhone());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        getContacts();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            llLoading.setVisibility(View.GONE);
            switch (msg.what) {
                case READ_SUCCEED:
                    mAdapter.setList(mContactList);
                    break;
                case READ_EMPTY:
                    llNoData.setVisibility(View.VISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 获取联系人列表
     */
    private void getContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                ContentResolver resolver = getContentResolver();
                Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
                Uri dataUri = Uri.parse("content://com.android.contacts/data");
                // 查找出有多少个联系人
                Cursor rcCursor = resolver.query(rawContactsUri, new String[]{"contact_id"}, null, null, null);
                if (rcCursor != null) {
                    while (rcCursor.moveToNext()) {
                        // 获取联系人的id
                        String contact_id = rcCursor.getString(0);
                        // 根据联系人的id，查找联系人的数据
                        Cursor dataCursor = resolver.query(dataUri, new String[]{"mimetype", "data1"}, "raw_contact_id=?", new String[]{contact_id}, null);
                        if (dataCursor != null) {
                            ContactInfo contactInfo = new ContactInfo();
                            while (dataCursor.moveToNext()) {
                                String mimetype = dataCursor.getString(0);
                                String data1 = dataCursor.getString(1);
                                if ("vnd.android.cursor.item/name".equals(mimetype)) {
                                    contactInfo.setName(data1);
                                } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                                    contactInfo.setPhone(data1);
                                }
                            }
                            dataCursor.close();

                            mContactList.add(contactInfo);
                            message.what = READ_SUCCEED;
                        } else {
                            message.what = READ_EMPTY;
                        }
                    }
                    rcCursor.close();
                } else {
                    message.what = READ_EMPTY;
                }

                mHandler.sendMessage(message);
            }
        }).start();
    }


    public static void startForResult(Context context, int requestCode) {
        ((Activity)context).startActivityForResult(new Intent(context, ContactActivity.class), requestCode);
    }
}
