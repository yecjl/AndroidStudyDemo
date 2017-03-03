package com.study.testphone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.btn_test)
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test)
    public void testPhone(View view) {
        try {
            InputStream is = getAssets().open("result.xml");
            XmlPullParser pullParser = Xml.newPullParser();
            pullParser.setInput(is, "gbk");
            int eventType = pullParser.getEventType();
            StringBuilder sb = new StringBuilder();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("phonenum".equals(pullParser.getName())) {
                        sb.append("手机号：" + pullParser.nextText() + "\n");
                    } else if ("location".equals(pullParser.getName())) {
                        sb.append("地点：" + pullParser.nextText() + "\n");
                    } else if ("phoneJx".equals(pullParser.getName())) {
                        sb.append("测试：" + pullParser.nextText());
                    }
                }
                eventType = pullParser.next();
            }
            tvContent.setText(sb.toString());
            is.close();
        } catch (Exception e) {
            tvContent.setText("手机号测试不正确！");
            e.printStackTrace();
        }
    }
}
