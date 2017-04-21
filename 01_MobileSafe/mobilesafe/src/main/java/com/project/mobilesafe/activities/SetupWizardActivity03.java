package com.project.mobilesafe.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobilesafe.R;
import com.project.mobilesafe.ui.PointView;

import butterknife.Bind;
import butterknife.OnClick;

import static com.project.mobilesafe.R.mipmap.phone;

/**
 * 功能：设置向导03界面
 * Created by danke on 2017/4/20.
 */

public class SetupWizardActivity03 extends SetupWizardBaseActivity {

    @Bind(R.id.pv_point)
    PointView pvPoint;
    @Bind(R.id.et_setup03_safeNumber)
    EditText etSafeNumber;
    @Bind(R.id.tv_setup03_choose_safeNumber)
    TextView tvChooseSafeNumber;

    private static final int CONTACT_CHOOSE = 1;

    @Override
    public int setLayoutResId() {
        return R.layout.activity_setup_wizard_03;
    }

    @Override
    protected void initView() {
        pvPoint.setListener(new PointView.OnPointClickListener() {
            @Override
            public void onPointClick(int position, int count) {
                switch (position) {
                    case 0:
                    case 1:
                        prePage();
                        break;
                    case 3:
                        nextPage();
                        break;
                }
            }
        });

        etSafeNumber.setText(sp.getString("safePhone", null));
    }

    /**
     * 选择安全号码
     */
    @OnClick(R.id.tv_setup03_choose_safeNumber)
    public void chooseSafeNumber() {
        ContactActivity.startForResult(this, CONTACT_CHOOSE);
    }

    @Override
    public void nextPage() {
        // 保存安全号码
        String safePhone = etSafeNumber.getText().toString().trim();
        if (!TextUtils.isEmpty(safePhone)) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("safePhone", safePhone);
            edit.commit();

            startAndFinish(SetupWizardActivity04.class);
            overridePendingTransition(R.anim.anim_enter_right_screen, R.anim.anim_exit_screen_left);
        } else {
            Toast.makeText(this, "必须设置安全号码", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void prePage() {
        startAndFinish(SetupWizardActivity02.class);
        overridePendingTransition(R.anim.anim_enter_left_screen, R.anim.anim_exit_screen_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CONTACT_CHOOSE) {
                if (data != null) {
                    String safePhone = data.getStringExtra("phone");
                    etSafeNumber.setText(safePhone);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
