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
 * 功能：添加黑名单
 * Created by danke on 2017/5/2.
 */

public class AddBlackContactActivity extends Activity {
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
    private BlackListDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addblackcontact);
        ButterKnife.bind(this);

        dao = new BlackListDao(this);
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
                if (blackContact != null) {
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
                    Toast.makeText(this, "黑名单添加成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("blackContact", blackContact);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, "黑名单添加失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_negative: // 取消
                finish();
                break;
        }
    }

    public static void start(Context context, int requestCode) {
        ((Activity) context).startActivityForResult(new Intent(context, AddBlackContactActivity.class), requestCode);
    }
}
