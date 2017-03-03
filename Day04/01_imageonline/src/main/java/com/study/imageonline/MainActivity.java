package com.study.imageonline;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.type;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_url)
    EditText etUrl;
    @Bind(R.id.tb_load)
    Button tbLoad;
    @Bind(R.id.iv_result)
    ImageView ivResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tb_load)
    public void onClick() {
        final String path = etUrl.getText().toString();
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(this, "图片路径不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 设置请求的方式
                    conn.setRequestMethod("GET");
                    // 设置连接超时时间
                    conn.setConnectTimeout(5000);
                    int length = conn.getContentLength();
                    Log.d(getClass().getName().toString(), "数据长度： " + length);
                    String type = conn.getContentType();
                    Log.d(getClass().getName().toString(), "数据请求类型： " + type);
                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivResult.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
