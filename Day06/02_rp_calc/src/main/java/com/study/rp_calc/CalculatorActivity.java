package com.study.rp_calc;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能：
 * Created by danke on 2017/3/14.
 */

public class CalculatorActivity extends AppCompatActivity {
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.btn_calc)
    Button btnCalc;
    @Bind(R.id.rb_male)
    RadioButton rbMale;
    @Bind(R.id.rb_female)
    RadioButton rbFemale;
    @Bind(R.id.rb_unknow)
    RadioButton rbUnknow;
    @Bind(R.id.rg_sex)
    RadioGroup rgSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_calc)
    public void onClick() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("sex", rgSex.getCheckedRadioButtonId());
        intent.putExtra("bitmap", R.mipmap.img_01);
        startActivity(intent);

    }
}
