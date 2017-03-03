package com.study.studentinfosystem_serializer;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

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

        try {
            // 1、创建Xml生成器
            XmlSerializer serializer = Xml.newSerializer();
            // 2、设置输出流，指定文件路径，文件名，文件编码
            FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), name + ".xml"));
            serializer.setOutput(fos, "utf-8");

            // 3、编写xml
            serializer.startDocument("utf-8", true); // 开始xml文档，指定是独立xml
            serializer.startTag(null, "student");

            serializer.startTag(null, "name"); // 开始name节点，默认命名空间为null
            serializer.text(name); // 指定节点间的具体数据
            serializer.endTag(null, "name"); // 结束name节点

            serializer.startTag(null, "number");
            serializer.text(number);
            serializer.endTag(null, "number");

            serializer.startTag(null, "sex");
            serializer.text(sex);
            serializer.endTag(null, "sex");

            serializer.endTag(null, "student");
            serializer.endDocument(); // 结束xml文档

            fos.close();
            Toast.makeText(this, "学生数据保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "学生数据保存失败", Toast.LENGTH_SHORT).show();
        }

    }
}
