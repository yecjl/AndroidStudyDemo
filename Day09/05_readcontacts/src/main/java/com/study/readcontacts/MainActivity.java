package com.study.readcontacts;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.study.readcontacts.Util.ContactInfo;
import com.study.readcontacts.Util.ContactsInfoUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.lv_contact)
    ListView lvContact;
    @Bind(R.id.btn_add)
    Button btnAdd;
    private ContactsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new ContactsAdapter(this, ContactsInfoUtils.getAllContactsInfo(this));
        lvContact.setAdapter(mAdapter);

        getContentResolver().registerContentObserver(ContactsInfoUtils.rcUri, true, mContentObserver);
        getContentResolver().registerContentObserver(ContactsInfoUtils.dataUri, true, mContentObserver);
    }

    ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            List<ContactInfo> list = ContactsInfoUtils.getAllContactsInfo(MainActivity.this);
            mAdapter.setList(list);
        }
    };

    @OnClick(R.id.btn_add)
    public void onClick() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("mama");
        contactInfo.setPhone("159");
        contactInfo.setEmail("159@qq.com");
        ContactsInfoUtils.insertContactInfo(this, contactInfo);
    }
}
