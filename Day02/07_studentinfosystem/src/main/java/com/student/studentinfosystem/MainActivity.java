package com.student.studentinfosystem;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_number)
    EditText etNumber;
    @Bind(R.id.radioButton1)
    RadioButton radioButton1;
    @Bind(R.id.radioButton2)
    RadioButton radioButton2;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void save(View view) {
        String name = etName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
            Toast.makeText(this, "学生的姓名或者学号不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        String sex = "male";
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.radioButton1: // 男生
                sex = "male";
                break;
            case R.id.radioButton2: // 女生
                sex = "female";
                break;
        }

        // 拼接数据
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<student>");
        sb.append("<name>");
        sb.append(name);
        sb.append("</name>");

        sb.append("<number>");
        sb.append(number);
        sb.append("</number>");

        sb.append("<sex>");
        sb.append(sex);
        sb.append("</sex>");

        sb.append("</student>");

        // 将数据保存到文件中
        try {
            File file = new File(getFilesDir(), name + ".xml");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());
            fos.close();
            Toast.makeText(this, "学生数据保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "学生数据保存失败", Toast.LENGTH_SHORT).show();
        }

    }
}
