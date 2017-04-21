package com.project.mobilesafe.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.project.mobilesafe.R;
import com.project.mobilesafe.ui.PointView;

import butterknife.Bind;

/**
 * 功能：设置向导01界面
 * Created by danke on 2017/4/20.
 */

public class SetupWizardActivity01 extends SetupWizardBaseActivity {

    @Bind(R.id.pv_point)
    PointView pvPoint;

    @Override
    public int setLayoutResId() {
        return R.layout.activity_setup_wizard_01;
    }

    @Override
    protected void initView() {
        pvPoint.setListener(new PointView.OnPointClickListener() {
            @Override
            public void onPointClick(int position, int count) {
                nextPage();
            }
        });
    }

    @Override
    public void nextPage() {
        startAndFinish(SetupWizardActivity02.class);
        overridePendingTransition(R.anim.anim_enter_right_screen, R.anim.anim_exit_screen_left);
    }

    @Override
    public void prePage() {
        Toast.makeText(this, "这已经是第一页", Toast.LENGTH_SHORT).show();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, SetupWizardActivity01.class));
    }
}
