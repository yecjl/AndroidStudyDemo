package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mobilesafe.R;

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
    ListView lvlist;
    @Bind(R.id.iv_blacklist_empty)
    ImageView ivEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_blacklist_add)
    public void addBlackContact() {

    }

    public static void start(Context context, int requestCode) {
        ((Activity)context).startActivityForResult(new Intent(context, BlackListActivity.class), requestCode);
    }
}
