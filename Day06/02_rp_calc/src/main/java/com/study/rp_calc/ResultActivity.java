package com.study.rp_calc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：
 * Created by danke on 2017/3/14.
 */

public class ResultActivity extends Activity {
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int sex = intent.getIntExtra("sex", R.id.rb_male);
//        Bitmap bitmap = intent.getParcelableExtra("bitmap");
        int bitmap = intent.getIntExtra("bitmap", R.mipmap.img_01);

        StringBuilder sb = new StringBuilder();
        sb.append("姓名：" + name + "\r\n");
        byte[] bytes = new byte[0];
        try {
            bytes = null;
            switch (sex) {
                case R.id.rb_male:
                    bytes = name.getBytes(); // 获取名字的字节数组
                    sb.append("性别：男" + "\r\n");
                    break;
                case R.id.rb_female:
                    bytes = name.getBytes("gbk"); // 获取名字的字节数组
                    sb.append("性别：女" + "\r\n");
                    break;
                case R.id.rb_unknow:
                    bytes = name.getBytes("iso-8859-1"); // 获取名字的字节数组
                    sb.append("性别：未知" + "\r\n");
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int score = 0;
        for (int i = 0; i < bytes.length; i++) {
            // 将字节转换为int, 求出绝对值，累计起来
            score += Math.abs(bytes[i] & 0xff);
        }

        score %= 100;
        sb.append("分数：" + score + "\r\n");

        if (score > 90) {
            sb.append("人品：好到爆！你上辈子已经是拯救了全世界");
        } else if (score > 60) {
            sb.append("人品：一般般啦！打打酱油吧");
        } else if (score > 30) {
            sb.append("人品：太差！请每天烧香一支");
        } else {
            sb.append("人品：对不起，不应该和你提人品");
        }

        tv.setText(sb.toString());
//        iv.setImageBitmap(bitmap);
        iv.setImageResource(bitmap);
    }
}
