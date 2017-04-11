package com.study.readcontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.study.readcontacts.Util.ContactInfo;
import com.study.readcontacts.Util.ContactsInfoUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.lv_contact)
    ListView lvContact;
    @Bind(R.id.btn_add)
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        lvContact.setAdapter(new ContactsAdapter(this, ContactsInfoUtils.getAllContactsInfo(this)));
    }

    @OnClick(R.id.btn_add)
    public void onClick() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("mama");
        contactInfo.setPhone("159");
        contactInfo.setEmail("159@qq.com");
        ContactsInfoUtils.insertContactInfo(this, contactInfo);
    }
}
