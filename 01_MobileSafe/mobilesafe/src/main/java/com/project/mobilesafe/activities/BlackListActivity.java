package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.activities.adapter.BlackListAdapter;
import com.project.mobilesafe.bean.BlackContact;
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
    private BlackListDao dao;
    private List<BlackContact> mList;
    private BlackListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        ButterKnife.bind(this);

        dao = new BlackListDao(this);
        mAdapter = new BlackListAdapter(this, dao, ivEmpty);
        mListView.setAdapter(mAdapter);

        findAll();
    }

    public void findAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList = dao.findAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setList(mList);
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.iv_blacklist_add)
    public void addBlackContact() {
        AddBlackContactActivity.start(this, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            findAll();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void start(Context context, int requestCode) {
        ((Activity) context).startActivityForResult(new Intent(context, BlackListActivity.class), requestCode);
    }
}
