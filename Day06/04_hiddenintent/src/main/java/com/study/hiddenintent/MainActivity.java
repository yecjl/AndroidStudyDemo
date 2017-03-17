package com.study.hiddenintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_open)
    public void open() {
        Intent intent = new Intent();
        intent.setAction("com.study.hiddenintent.OPENACTIVITY");
        intent.setDataAndType(Uri.parse("http://img.hb.aicdn.com/db9897ea2252633b037799c9664506f04b122da8540f1-jsmdjx_fw658"), "text/html");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    @OnClick(R.id.btn_open_brower)
    public void openBrower() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.parse("http://www.baidu.com"), "text/html");
//        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_open_call)
    public void openCall() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("tel://15957092891"));
        startActivity(intent);
    }

    @OnClick(R.id.btn_sendMessage)
    public void sendMessage() {
        // 方式一
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra("address", "15957099656");
        intent.putExtra("sms_body", "You are intelligent girl");
        startActivity(intent);

        // 方式二
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.SENDTO");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addCategory("android.intent.category.BROWSABLE");
//        intent.setData(Uri.parse("smsto:15957099656"));
//        intent.putExtra("sms_body", "You are intelligent girl");
//        startActivity(intent);
    }

    @OnClick(R.id.btn_open_photos)
    public void openPhotos() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/image");
        startActivity(intent);

        // 挑选相册
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.PICK");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setType("image/*");
//        startActivity(intent);
    }

    @OnClick(R.id.btn_open_camera)
    public void openCamera() {
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }
}
