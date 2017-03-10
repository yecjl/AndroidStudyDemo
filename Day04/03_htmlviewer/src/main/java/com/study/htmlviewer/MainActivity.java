package com.study.htmlviewer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.study.htmlviewer.Util.StreamUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_path)
    EditText etPath;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.tv_result)
    TextView tvResult;

    private static final int SUCCESS = 1;
    private static final int ERROR = 2;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mDialog.dismiss();
            switch (msg.what) {
                case SUCCESS:
                    tvResult.setText((String) msg.obj);
                    break;
                case ERROR:
                    Toast.makeText(MainActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @OnClick(R.id.btn_search)
    public void onClick() {
        final String path = etPath.getText().toString();
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(this, "网址路径不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
        }
        mDialog.setMessage("正在数据加载中...");
        mDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                InputStream is = null;
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)");
                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();

                        // 从输入流中获取字符串
                        String result = StreamUtil.readStream(is);

                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = result;
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
                    } else {
                        Message msg = Message.obtain();
                        msg.what = ERROR;
                        mHandler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }
}
