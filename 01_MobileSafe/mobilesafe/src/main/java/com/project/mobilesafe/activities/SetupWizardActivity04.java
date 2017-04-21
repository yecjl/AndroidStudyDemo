package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.ui.PointView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * 功能：设置向导03界面
 * Created by danke on 2017/4/20.
 */

public class SetupWizardActivity04 extends SetupWizardBaseActivity {

    @Bind(R.id.pv_point)
    PointView pvPoint;
    @Bind(R.id.tv_setupFinish)
    TextView tvSetupFinish;

    @Override
    public int setLayoutResId() {
        return R.layout.activity_setup_wizard_04;
    }

    @Override
    protected void initView() {
        pvPoint.setListener(new PointView.OnPointClickListener() {
            @Override
            public void onPointClick(int position, int count) {
                prePage();
            }
        });
    }

    @Override
    public void nextPage() {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isSetupWizard", true);
        edit.commit();
        LostFindActivity.start(this);
        finish();
    }

    @Override
    public void prePage() {
        startAndFinish(SetupWizardActivity03.class);
        overridePendingTransition(R.anim.anim_enter_left_screen, R.anim.anim_exit_screen_right);
    }

    @OnClick(R.id.tv_setupFinish)
    public void onClick() {
        nextPage();
    }
}
