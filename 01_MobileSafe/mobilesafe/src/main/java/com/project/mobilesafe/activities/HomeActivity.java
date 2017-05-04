package com.project.mobilesafe.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobilesafe.R;
import com.project.mobilesafe.activities.adapter.HomeAdapter;
import com.project.mobilesafe.beans.HomeContentInfo;
import com.project.mobilesafe.utils.Md5Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能：主页面
 * Created by danke on 2017/4/18.
 */

public class HomeActivity extends Activity {
    @Bind(R.id.iv_home_icon)
    ImageView ivIcon;
    @Bind(R.id.gv_home_content)
    GridView mGridView;
    @Bind(R.id.iv_home_setting)
    ImageView ivHomeSetting;

    private final static String TAG = "HomeActivity";
    private List<HomeContentInfo> mList;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(ivIcon, "rotationY", new float[]{0, 360});
        rotationY.setDuration(3000);
        rotationY.setRepeatMode(ObjectAnimator.RESTART);
        rotationY.setRepeatCount(ObjectAnimator.INFINITE);
        rotationY.start();

        mList = new ArrayList<>();
        sp = getSharedPreferences("config", MODE_PRIVATE);

        // 通过TypedArray获取图片资源
        TypedArray ta = getResources().obtainTypedArray(R.array.home_icon);
        String[] titleArray = getResources().getStringArray(R.array.home_title);
        String[] descArray = getResources().getStringArray(R.array.home_desc);

        for (int i = 0; i < ta.length(); i++) {
            int resId = ta.getResourceId(i, 0);
            HomeContentInfo info = new HomeContentInfo();
            info.setResId(resId);
            info.setTitle(titleArray[i]);
            info.setDesc(descArray[i]);
            mList.add(info);
        }
        ta.recycle();

        mGridView.post(new Runnable() {
            @Override
            public void run() {
                int width = mGridView.getWidth();
                int height = mGridView.getHeight();
                Log.i(TAG, "width: " + width + ", height: " + height);

                mGridView.setAdapter(new HomeAdapter(HomeActivity.this, height, mList));
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 手机防盗的功能
                        // 1、判断用户是否设置过密码，并对应显示对话框
                        showPwdDialog(checkIsSetupPwd());
                        break;
                    case 1:
                        BlackListActivity.start(HomeActivity.this, 0);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        CommonUtilsActivity.start(HomeActivity.this);
                        break;
                }
            }

        });
    }

    private void showPwdDialog(final boolean isSetupPwd) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_password, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        final TextView tvDialogTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        final EditText etDialogPwd = (EditText) view.findViewById(R.id.et_dialog_pwd);
        final EditText etDialogRepwd = (EditText) view.findViewById(R.id.et_dialog_repwd);
        TextView tvPositive = (TextView) view.findViewById(R.id.tv_positive);
        TextView tvNegative = (TextView) view.findViewById(R.id.tv_negative);

        if (isSetupPwd) {
            tvDialogTitle.setText("密码输入对话框");
            etDialogRepwd.setVisibility(View.GONE);
        } else {
            tvDialogTitle.setText("密码设置对话框");
        }

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = etDialogPwd.getText().toString().trim();
                String pwdMd5 = Md5Utils.encode(pwd);
                if (isSetupPwd) {
                    Log.i(TAG, "显示输入密码对话框");
                    String savePwd = sp.getString("password", null);
                    if (TextUtils.isEmpty(pwd)) {
                        Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (!pwdMd5.equals(savePwd)) {
                        Toast.makeText(HomeActivity.this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                    } else {
                        alertDialog.dismiss();
                        rewardActivity();
                    }
                } else {
                    Log.i(TAG, "显示设置密码对话框");
                    String repwd = etDialogRepwd.getText().toString().trim();
                    if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(repwd)) {
                        Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (!pwd.equals(repwd)) {
                        Toast.makeText(HomeActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("password", pwdMd5);
                        edit.commit();
                        alertDialog.dismiss();
                        rewardActivity();
                    }
                }
            }
        });

        tvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    /**
     * 判断用户是否设置过密码
     *
     * @return
     */
    private boolean checkIsSetupPwd() {
        String password = sp.getString("password", null);
        return !TextUtils.isEmpty(password);
    }

    /**
     * 打开新的Activity
     */
    private void rewardActivity() {
        // 判断是否设置过设置向导
        boolean isSetupWizard = sp.getBoolean("isSetupWizard", false);
        if (isSetupWizard) {
            Log.i(TAG, "用户设置过设置向导，进入手机防盗的界面");
            LostFindActivity.start(this);
        } else {
            Log.i(TAG, "用户没有设置过设置向导，打开设置向导");
            SetupWizardActivity01.start(this);
        }
    }

    @OnClick(R.id.iv_home_setting)
    public void setting() {
        SettingActivity.start(this);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}
