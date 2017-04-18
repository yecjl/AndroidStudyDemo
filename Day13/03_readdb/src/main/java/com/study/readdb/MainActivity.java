package com.study.readdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.listView)
    ListView listView;

    private MyAdapter mAdapter;
    private List<SmsInfo> mNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new MyAdapter(this);
        listView.setAdapter(mAdapter);

        startService(new Intent(this, SmsService.class));

        IntentFilter filter = new IntentFilter();
        filter.addAction(SmsService.READ_SMS_SUCCESS);
        registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mNameList = (ArrayList<SmsInfo>) intent.getSerializableExtra("nameList");
            mAdapter.setList(mNameList);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
