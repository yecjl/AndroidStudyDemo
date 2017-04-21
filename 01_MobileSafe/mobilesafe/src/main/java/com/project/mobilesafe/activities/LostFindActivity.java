package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.mobilesafe.R;
import com.project.mobilesafe.ui.SetItemView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：手机防盗界面
 * Created by danke on 2017/4/20.
 */

public class LostFindActivity extends Activity {

    @Bind(R.id.siv_openSetup)
    SetItemView sivOpenSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
        ButterKnife.bind(this);

        sivOpenSetup.setListener(mClickListener);
    }

    private SetItemView.OnItemClickListener mClickListener = new SetItemView.OnItemClickListener() {
        @Override
        public void onClick(View v, boolean status) {
            switch (v.getId()) {
                case R.id.siv_openSetup: // 重新打开设置向导:
                    SetupWizardActivity01.start(LostFindActivity.this);
                    finish();
                    break;
            }
        }
    };

    public static void start(Context context) {
        context.startActivity(new Intent(context, LostFindActivity.class));
    }
}
