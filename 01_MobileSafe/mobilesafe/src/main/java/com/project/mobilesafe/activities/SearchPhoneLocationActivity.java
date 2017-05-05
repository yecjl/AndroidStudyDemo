package com.project.mobilesafe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.project.mobilesafe.R;
import com.project.mobilesafe.db.dao.AddressDao;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能：号码归属地查询的界面
 * Created by danke on 2017/5/5.
 */

public class SearchPhoneLocationActivity extends Activity {
    @Bind(R.id.et_search_phone)
    EditText etPhone;
    @Bind(R.id.tv_search_result)
    TextView tvResult;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    private AddressDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelocation);
        ButterKnife.bind(this);

        dao = new AddressDao(this);

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = s.toString();
                if (TextUtils.isEmpty(phone)) {
                    tvResult.setText("");
                } else {
                    tvResult.setText("归属地：" + dao.findLocation(phone));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, SearchPhoneLocationActivity.class));
    }

    @OnClick(R.id.tv_search)
    public void onClick() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etPhone.startAnimation(shake);
        } else {
            tvResult.setText("归属地：" + dao.findLocation(phone));
        }
    }
}
