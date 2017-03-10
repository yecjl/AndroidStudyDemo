package com.study.imageonline;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_url)
    EditText etUrl;
    @Bind(R.id.tb_load)
    Button tbLoad;
    @Bind(R.id.iv_result)
    ImageView ivResult;

    private static final int SUCCESS = 1;
    private static final int ERROR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    ivResult.setImageBitmap((Bitmap) msg.obj);
                    break;
                case ERROR:
                    Toast.makeText(MainActivity.this, "图片加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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

                        // 复用历史消息，而不是每次都创建新的消息
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    mHandler.sendMessage(msg);

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
