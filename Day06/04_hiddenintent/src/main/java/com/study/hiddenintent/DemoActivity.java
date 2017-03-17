package com.study.hiddenintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * 功能：
 * Created by danke on 2017/3/15.
 */

public class DemoActivity extends AppCompatActivity {

    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Uri data = intent.getData();
        StringBuilder sb = new StringBuilder();
        if (data != null) {
            sb.append("【scheme】: " + data.getScheme() + "\r\n");
            sb.append("【schemeSpecificPart】: " + data.getSchemeSpecificPart() + "\r\n");
            sb.append("【all】: " + data.toString() + "\r\n");
            tvResult.setText(sb.toString());

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(this, data.toString(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                    iv.setImageBitmap(bitmap);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }

    }
}
