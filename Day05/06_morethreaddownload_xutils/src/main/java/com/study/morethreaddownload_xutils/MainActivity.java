package com.study.morethreaddownload_xutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_path)
    EditText etPath;
    @Bind(R.id.btn_down)
    Button btnDown;
    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.iv_result)
    ImageView ivResult;
    private String downPath = Environment.getExternalStorageDirectory() + "/Danke/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_down)
    public void onClick() {
        String path = etPath.getText().toString().trim();
        if (TextUtils.isEmpty(path) && !path.startsWith("http://")) {
            Toast.makeText(this, "下载地址不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams(path);
        params.setSaveFilePath(downPath + "xxx.jpg");
        params.setAutoResume(true); // 设置断点续传
        params.setAutoRename(false);
        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {
                ivResult.setImageBitmap(BitmapFactory.decodeFile(result.getAbsolutePath()));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                pb.setMax((int) total);
                pb.setProgress((int) current);
            }
        });
    }
}
