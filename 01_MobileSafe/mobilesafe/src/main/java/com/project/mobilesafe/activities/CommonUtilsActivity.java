package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;

import com.project.mobilesafe.R;
import com.project.mobilesafe.ui.SetItemView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：常用工具界面
 * Created by danke on 2017/5/4.
 */

public class CommonUtilsActivity extends Activity {
    @Bind(R.id.isv_phone_location)
    SetItemView isvPhoneLocation;
    @Bind(R.id.isv_sms_backup)
    SetItemView isvSmsBackup;
    @Bind(R.id.isv_sms_restore)
    SetItemView isvSmsRestore;
    @Bind(R.id.isv_key)
    SetItemView isvKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonutils);
        ButterKnife.bind(this);

        isvPhoneLocation.setListener(onItemClickListener);
        isvSmsBackup.setListener(onItemClickListener);
        isvSmsRestore.setListener(onItemClickListener);
        isvKey.setListener(onItemClickListener);
    }

    private SetItemView.OnItemClickListener onItemClickListener = new SetItemView.OnItemClickListener() {
        @Override
        public void onClick(View v, boolean status) {
            switch (v.getId()) {
                case R.id.isv_phone_location:
                    SearchPhoneLocationActivity.start(CommonUtilsActivity.this);
                    break;
                case R.id.isv_sms_backup:
                    break;
                case R.id.isv_sms_restore:
                    break;
                case R.id.isv_key:
                    break;
            }
        }
    };

    public static void start(Context context) {
        context.startActivity(new Intent(context, CommonUtilsActivity.class));
    }
}
