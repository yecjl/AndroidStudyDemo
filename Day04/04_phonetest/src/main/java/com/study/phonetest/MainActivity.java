package com.study.phonetest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Xml;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.btn_search)
    Button btnSearch;

    private static final int SUCCESS = 1;
    private static final int ERROR = 2;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.tv_postno)
    TextView tvPostno;
    @Bind(R.id.tv_ctype)
    TextView tvCtype;
    @Bind(R.id.tv_city)
    TextView tvCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    PhoneBean phoneBean = (PhoneBean) msg.obj;
                    tvPhone.setText(phoneBean.getPhone());
                    tvArea.setText("区号：" + phoneBean.getArea());
                    tvPostno.setText("邮编：" + phoneBean.getPostno());
                    tvCtype.setText("型号：" + phoneBean.getCtype());
                    tvCity.setText("地区：" + phoneBean.getStyle_citynm());
                    break;
                case ERROR:
                    Toast.makeText(MainActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @OnClick(R.id.btn_search)
    public void onClick() {
        final String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    URL url = new URL("http://api.k780.com:88/?app=phone.get&phone=" + phone + "&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();
                        
                        XmlPullParser parser = Xml.newPullParser();
                        parser.setInput(is, "utf-8");
                        int type = parser.getEventType();

                        PhoneBean phoneBean = new PhoneBean();
                        while (type != parser.END_DOCUMENT) {
                            if (type == parser.START_TAG) {
                                if ("phone".equals(parser.getName())) {
                                    phoneBean.setPhone(parser.nextText());
                                } else if ("area".equals(parser.getName())) {
                                    phoneBean.setArea(parser.nextText());
                                } else if ("postno".equals(parser.getName())) {
                                    phoneBean.setPostno(parser.nextText());
                                } else if ("ctype".equals(parser.getName())) {
                                    phoneBean.setCtype(parser.nextText());
                                } else if ("style_citynm".equals(parser.getName())) {
                                    phoneBean.setStyle_citynm(parser.nextText());
                                }
                            }
                            type = parser.next();
                        }

                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = phoneBean;
                        handler.sendMessage(msg);

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Message msg = Message.obtain();
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }
}
