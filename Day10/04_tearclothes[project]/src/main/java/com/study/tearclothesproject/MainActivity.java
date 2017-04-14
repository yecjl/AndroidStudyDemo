package com.study.tearclothesproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.iv_tear)
    ImageView ivTear;
    private Bitmap bitmap;
    private Canvas canvas;
    private Bitmap newBitmap;
    private Paint paint;
    private Matrix matrix;
    private int paintSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pre);
        newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        canvas = new Canvas(newBitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        ivTear.setImageBitmap(newBitmap);

        ivTear.setOnTouchListener(new View.OnTouchListener() {
            private int downX;
            private int downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        downX = (int) event.getX();
                        downY = (int) event.getY();

                        for (int i = -paintSize; i <= paintSize; i++) {
                            for (int j = -paintSize; j <= paintSize; j++) {
                                if (Math.sqrt(i * i + j * j) <= paintSize) {
                                    try {
                                        newBitmap.setPixel(downX + i, downY + j, Color.TRANSPARENT);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        ivTear.setImageBitmap(newBitmap);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }
}
