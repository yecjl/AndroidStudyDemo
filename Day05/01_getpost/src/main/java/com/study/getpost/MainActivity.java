package com.study.getpost;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Xml;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int SUCCESS = 1;
    private static final int ERROR = 2;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.rb_get)
    RadioButton rbGet;
    @Bind(R.id.rb_post)
    RadioButton rbPost;
    @Bind(R.id.rg)
    RadioGroup rg;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.tv_result)
    TextView tvResult;

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
                    tvResult.setText((String) msg.obj);
                    break;
                case ERROR:
                    Toast.makeText(MainActivity.this, "数据访问失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @OnClick(R.id.btn_search)
    public void onClick() {
        final String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int methodID = rg.getCheckedRadioButtonId();
                    String path = "http://api.k780.com:88/";
                    String paramsData = "app=phone.get&phone=" + URLEncoder.encode(phone, "utf-8") + "&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml";
                    String method;
                    switch (methodID) {
                        case R.id.rb_post:
                            method = "POST";
                            break;
                        case R.id.rb_get:
                        default:
                            method = "GET";
                            path += "?" + paramsData;
                            break;
                    }
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod(method);
                    conn.setConnectTimeout(5000);
                    if ("POST".equals(method)) {
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("Content-Length", String.valueOf(paramsData.length()));
                        // 指定要给服务器写数据
                        conn.setDoOutput(true);
                        // 开始向服务器写数据
                        conn.getOutputStream().write(paramsData.getBytes());
                    }

                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        String result = StreamUtil.readStream(is);
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = result;
                        handler.sendMessage(msg);
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
            }
        }).start();

    }
}
