package com.study.loadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.graphics.BitmapFactory.decodeFile;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_load)
    Button btnLoad;
    @Bind(R.id.iv_image1)
    ImageView ivImage1;
    @Bind(R.id.iv_image2)
    ImageView ivImage2;
    @Bind(R.id.iv_image3)
    ImageView ivImage3;
    @Bind(R.id.iv_image4)
    ImageView ivImage4;
    @Bind(R.id.iv_image5)
    ImageView ivImage5;
    @Bind(R.id.iv_image6)
    ImageView ivImage6;

    private String path = Environment.getExternalStorageDirectory() + File.separator + "Danke" + File.separator + "big.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_load)
    public void onClick() {
        ivImage1.setImageBitmap(BitmapUtil.pressBitmap(this, path));
        ivImage2.setImageBitmap(BitmapUtil.pressBitmap(this, path));
        ivImage3.setImageBitmap(BitmapUtil.pressBitmap(this, path));
        ivImage4.setImageBitmap(BitmapUtil.pressBitmap(this, path));
        ivImage5.setImageBitmap(BitmapUtil.pressBitmap(this, path));
        ivImage6.setImageBitmap(BitmapUtil.pressBitmap(this, path));
    }

}
