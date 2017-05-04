package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.activities.adapter.BlackListAdapter;
import com.project.mobilesafe.beans.BlackContact;
import com.project.mobilesafe.db.dao.BlackListDao;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能：黑名单管理页面
 * Created by danke on 2017/4/26.
 */

public class BlackListActivity extends Activity {
    @Bind(R.id.tv_blacklist_title)
    TextView tvTitle;
    @Bind(R.id.iv_blacklist_add)
    ImageView ivAdd;
    @Bind(R.id.lv_blacklist)
    ListView mListView;
    @Bind(R.id.iv_blacklist_empty)
    ImageView ivEmpty;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    private BlackListDao dao;
    private List<BlackContact> mList;
    private BlackListAdapter mAdapter;

    private final static int REQUEST_ADD_BLACK_CONTACT = 0;
    private final static int REQUEST_UPDATE_BLACK_CONTACT = 1;
    private int currentModifyPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        ButterKnife.bind(this);

        dao = new BlackListDao(this);
        mAdapter = new BlackListAdapter(this, dao, ivEmpty);

        findAll();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
                    currentModifyPosition = position;
                    BlackContact blackContact = mList.get(position);
                    BlackContactActivity.start(BlackListActivity.this, blackContact, REQUEST_UPDATE_BLACK_CONTACT);
                }
            }
        });
    }

    public void findAll() {
        llLoading.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mList = dao.findAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setList(mList);
                        mListView.setAdapter(mAdapter);
                        llLoading.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.iv_blacklist_add)
    public void addBlackContact() {
        BlackContactActivity.start(this, REQUEST_ADD_BLACK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                BlackContact blackContact = (BlackContact) data.getSerializableExtra("blackContact");
                switch (requestCode) {
                    case REQUEST_ADD_BLACK_CONTACT:
                        mAdapter.add(blackContact);
                        break;
                    case REQUEST_UPDATE_BLACK_CONTACT:
                        mAdapter.update(blackContact, currentModifyPosition);
                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void start(Context context, int requestCode) {
        ((Activity) context).startActivityForResult(new Intent(context, BlackListActivity.class), requestCode);
    }
}
