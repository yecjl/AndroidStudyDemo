package com.project.mobilesafe.activities;

import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.ui.PointView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 功能：设置向导03界面
 * Created by danke on 2017/4/20.
 */

public class SetupWizardActivity04 extends SetupWizardBaseActivity {

    @Bind(R.id.pv_point)
    PointView pvPoint;
    @Bind(R.id.tv_setupFinish)
    TextView tvSetupFinish;
    @Bind(R.id.cb_isOpenProtect)
    CheckBox cbIsOpenProtect;

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

        cbIsOpenProtect.setChecked(sp.getBoolean("isOpenProtect", false));

        cbIsOpenProtect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isOpenProtect", isChecked);
                edit.commit();
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
