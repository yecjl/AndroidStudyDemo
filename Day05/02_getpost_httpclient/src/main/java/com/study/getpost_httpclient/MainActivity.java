package com.study.getpost_httpclient;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

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

                    // HttpClient
                    DefaultHttpClient client = new DefaultHttpClient();
                    // 相应数据
                    HttpResponse response;
                    switch (methodID) {
                        case R.id.rb_post: // POST请求
                            // 创建Post请求对象
                            HttpPost post = new HttpPost(path);
                            // 设置提交的参数
                            ArrayList<NameValuePair> parameters = new ArrayList<>();
                            parameters.add(new BasicNameValuePair("phone", phone));
                            parameters.add(new BasicNameValuePair("app", "phone.get"));
                            parameters.add(new BasicNameValuePair("appkey", "10003"));
                            parameters.add(new BasicNameValuePair("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4"));
                            parameters.add(new BasicNameValuePair("format", "xml"));
                            post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
                            // 执行post请求，获取相应数据
                            response = client.execute(post);
                            break;
                        case R.id.rb_get: // GET请求
                        default:
                            // 执行get请求，获取相应数据
                            response = client.execute(new HttpGet(path + "?" + paramsData));
                            break;
                    }

                    // 判断状态码是否是200
                    if (response.getStatusLine().getStatusCode() == 200) {
                        // 获取数据流
                        InputStream is = response.getEntity().getContent();
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
