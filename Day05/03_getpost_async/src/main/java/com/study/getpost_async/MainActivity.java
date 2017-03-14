package com.study.getpost_async;

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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

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

        try {
            int methodID = rg.getCheckedRadioButtonId();
            String path = "http://api.k780.com:88/";
            String paramsData = "app=phone.get&phone=" + URLEncoder.encode(phone, "utf-8") + "&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml";

            // HttpClient
            AsyncHttpClient client = new AsyncHttpClient();
            switch (methodID) {
                case R.id.rb_post: // POST请求
                    RequestParams params = new RequestParams();
                    params.put("phone", phone);
                    params.put("app", "phone.get");
                    params.put("appkey", "10003");
                    params.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
                    params.put("format", "xml");
                    client.post(path, params, asyncHttpResponseHandler);
                    break;
                case R.id.rb_get: // GET请求
                    client.get(path + "?" + paramsData, asyncHttpResponseHandler);
                default:
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private AsyncHttpResponseHandler asyncHttpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            tvResult.setText(new String(responseBody));
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            tvResult.setText("请求失败： " + new String(responseBody));
        }
    };
}
