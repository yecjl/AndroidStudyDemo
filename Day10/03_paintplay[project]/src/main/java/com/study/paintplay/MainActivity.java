package com.study.paintplay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.color_black)
    View colorBlack;
    @Bind(R.id.color_white)
    View colorWhite;
    @Bind(R.id.color_pink)
    View colorPink;
    @Bind(R.id.color_yellow)
    View colorYellow;
    @Bind(R.id.color_red)
    View colorRed;
    @Bind(R.id.color_green)
    View colorGreen;
    @Bind(R.id.color_blue)
    View colorBlue;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    @Bind(R.id.iv_paint)
    ImageView ivPaint;
    @Bind(R.id.btn_reset)
    Button btnReset;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private String path = Environment.getExternalStorageDirectory() + File.separator + "Danke" + File.separator + "draw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK); // 设置画笔的颜色
        mPaint.setStrokeWidth(seekBar.getProgress()); // 设置画笔的粗细

        // 设置seekBar的progress改变的监听
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mPaint.setStrokeWidth(progress);
                Toast.makeText(MainActivity.this, "设置画笔宽度：" + progress, Toast.LENGTH_SHORT).show();
            }
        });

        ivPaint.post(new Runnable() {

            @Override
            public void run() {
                mWidth = ivPaint.getWidth();
                mHeight = ivPaint.getHeight();
                mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mBitmap);
                mCanvas.drawColor(Color.WHITE);
                Log.d("paintplay", "width: " + mWidth);
                Log.d("paintplay", "height: " + mHeight);
            }
        });

        // 给ImageView注册一个触摸事件
        ivPaint.setOnTouchListener(new View.OnTouchListener() {
            private float downX;
            private float downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        Log.d("paintplay", "downX: " + downX);
                        Log.d("paintplay", "downY: " + downY);
                        mCanvas.drawPoint(downX, downY, mPaint);
                        ivPaint.setImageBitmap(mBitmap);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_MOVE:
                        float moveX = event.getX();
                        float moveY = event.getY();
                        Log.d("paintplay", "moveX: " + moveX);
                        Log.d("paintplay", "moveY: " + moveY);
                        mCanvas.drawLine(downX, downY, moveX, moveY, mPaint);
                        ivPaint.setImageBitmap(mBitmap);

                        downX = moveX;
                        downY = moveY;
                        break;
                }
                return true;
            }
        });
    }

    @OnClick({R.id.color_black, R.id.color_white, R.id.color_pink, R.id.color_yellow, R.id.color_red, R.id.color_green, R.id.color_blue})
    public void setColor(View view) {
        switch (view.getId()) {
            case R.id.color_black:
                mPaint.setColor(Color.BLACK);
                Toast.makeText(this, "选择黑色画笔", Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_white:
                mPaint.setColor(Color.WHITE);
                Toast.makeText(this, "选择白色画笔", Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_pink:
                mPaint.setColor(0xffff00ff);
                Toast.makeText(this, "选择粉色画笔", Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_yellow:
                mPaint.setColor(Color.YELLOW);
                Toast.makeText(this, "选择黄色画笔", Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_red:
                mPaint.setColor(Color.RED);
                Toast.makeText(this, "选择红色画笔", Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_green:
                mPaint.setColor(Color.GREEN);
                Toast.makeText(this, "选择绿色画笔", Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_blue:
                mPaint.setColor(Color.BLUE);
                Toast.makeText(this, "选择蓝色画笔", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnClick({R.id.btn_reset, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                mCanvas.drawColor(Color.WHITE);
                ivPaint.setImageBitmap(mBitmap);
                break;
            case R.id.btn_save:
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                try {
                    File mPhotoFile = new File(file, System.currentTimeMillis() + ".jpg");
                    FileOutputStream fos = new FileOutputStream(mPhotoFile);
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();

                    // 发送开机启动完成的广播，欺骗图库加载新的图片
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Intent mediaScanIntent = new Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri contentUri = Uri.fromFile(mPhotoFile); //out is your output file
                        mediaScanIntent.setData(contentUri);
                        sendBroadcast(mediaScanIntent);
                    } else {
                        sendBroadcast(new Intent(
                                Intent.ACTION_MEDIA_MOUNTED,
                                Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                    }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "图片保存失败", Toast.LENGTH_SHORT).show();
        }
        break;
    }
}
}
