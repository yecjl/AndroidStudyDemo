package com.project.mobilesafe.activities;

import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.project.mobilesafe.R;
import com.project.mobilesafe.ui.PointView;
import com.project.mobilesafe.ui.SetItemView;

import butterknife.Bind;

/**
 * 功能：设置向导02界面
 * Created by danke on 2017/4/20.
 */

public class SetupWizardActivity02 extends SetupWizardBaseActivity {

    @Bind(R.id.pv_point)
    PointView pvPoint;
    @Bind(R.id.siv_setup02_bind)
    SetItemView sivBind;
    private TelephonyManager tm;
    private final static String TAG = "SetupWizardActivity02";

    @Override
    public int setLayoutResId() {
        return R.layout.activity_setup_wizard_02;
    }

    @Override
    protected void initView() {
        pvPoint.setListener(new PointView.OnPointClickListener() {
            @Override
            public void onPointClick(int position, int count) {
                switch (position) {
                    case 0:
                        prePage();
                        break;
                    case 2:
                    case 3:
                        nextPage();
                        break;
                }
            }
        });

        String sim = sp.getString("sim", null);
        if (TextUtils.isEmpty(sim)) {
            sivBind.setStatus(false);
        } else {
            sivBind.setStatus(true);
        }

        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        sivBind.setListener(new SetItemView.OnItemClickListener() {
            @Override
            public void onClick(View v, boolean status) {
                SharedPreferences.Editor edit = sp.edit();
                if (status) {
                    // 绑定sim
                    Log.i(TAG, "sim: " + tm.getSimSerialNumber());
                    edit.putString("sim", tm.getSimSerialNumber());
                } else {
                    // 解绑sim
                    edit.putString("sim", null);
                }
                edit.commit();
            }
        });
    }

    @Override
    public void nextPage() {
        String sim = sp.getString("sim", null);
        if (!TextUtils.isEmpty(sim)) {
            // 只有绑定sim卡才可以进行手机防盗，所以必须设置sim卡绑定才可以进入下一个页面
            startAndFinish(SetupWizardActivity03.class);
            overridePendingTransition(R.anim.anim_enter_right_screen, R.anim.anim_exit_screen_left);
        } else {
            Toast.makeText(this, "手机防盗生效，必须先绑定SIM卡", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void prePage() {
        startAndFinish(SetupWizardActivity01.class);
        overridePendingTransition(R.anim.anim_enter_left_screen, R.anim.anim_exit_screen_right);
    }
}
