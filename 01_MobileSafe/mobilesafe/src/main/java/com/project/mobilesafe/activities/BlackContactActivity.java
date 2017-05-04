package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobilesafe.R;
import com.project.mobilesafe.beans.BlackContact;
import com.project.mobilesafe.db.dao.BlackListDao;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能：添加或者修改黑名单
 * Created by danke on 2017/5/2.
 */

public class BlackContactActivity extends Activity {
    @Bind(R.id.et_add_number)
    EditText etNumber;
    @Bind(R.id.rb_add_phone)
    RadioButton rbPhone;
    @Bind(R.id.rb_add_sms)
    RadioButton rbSms;
    @Bind(R.id.rb_add_all)
    RadioButton rbAll;
    @Bind(R.id.rg_add_type)
    RadioGroup rgType;
    @Bind(R.id.tv_positive)
    TextView tvPositive;
    @Bind(R.id.tv_negative)
    TextView tvNegative;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    private BlackListDao dao;

    private final static int ADD_BLACK_CONTACT = 0; // 添加黑名单
    private final static int UPDATE_BLACK_CONTACT = 1; // 修改黑名单
    private int currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addblackcontact);
        ButterKnife.bind(this);

        dao = new BlackListDao(this);

        BlackContact blackContact = (BlackContact) getIntent().getSerializableExtra("blackContact");
        if (blackContact != null) {
            currentMode = UPDATE_BLACK_CONTACT;
            tvTitle.setText("修改黑名单");
            String phone = blackContact.getPhone();
            etNumber.setText(phone);
            etNumber.setSelection(phone.length());
            String checkType = blackContact.getMode();
            switch (checkType) {
                case "1":
                    rbPhone.setChecked(true);
                    break;
                case "2":
                    rbSms.setChecked(true);
                    break;
                case "3":
                    rbAll.setChecked(true);
                    break;
            }
        } else {
            currentMode = ADD_BLACK_CONTACT;
            tvTitle.setText("添加黑名单");
        }
    }

    @OnClick({R.id.tv_positive, R.id.tv_negative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_positive: // 确定
                String number = etNumber.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(this, "请输入黑名单的电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 判断黑名单号码是否重复
                BlackContact blackContact = dao.findBlackContact(number);
                if (blackContact != null && currentMode == ADD_BLACK_CONTACT) {
                    Toast.makeText(this, "黑名单号码已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }

                String mode = "1"; // 1、电话拦截 2、短信拦截 3、全部拦截
                int checkType = rgType.getCheckedRadioButtonId();
                switch (checkType) {
                    case R.id.rb_add_phone:
                        mode = "1";
                        break;
                    case R.id.rb_add_sms:
                        mode = "2";
                        break;
                    case R.id.rb_add_all:
                        mode = "3";
                        break;
                }

                blackContact = new BlackContact();
                blackContact.setPhone(number);
                blackContact.setMode(mode);

                boolean result = dao.insert(blackContact);
                if (result) {
                    if (currentMode == ADD_BLACK_CONTACT) {
                        Toast.makeText(this, "黑名单添加成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "黑名单修改成功", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent();
                    intent.putExtra("blackContact", blackContact);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (currentMode == ADD_BLACK_CONTACT) {
                        Toast.makeText(this, "黑名单添加失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "黑名单修改失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_negative: // 取消
                finish();
                break;
        }
    }

    public static void start(Context context, int requestCode) {
        start(context, null, requestCode);
    }

    public static void start(Context context, BlackContact blackContact, int requestCode) {
        Intent intent = new Intent(context, BlackContactActivity.class);
        intent.putExtra("blackContact", blackContact);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }
}
