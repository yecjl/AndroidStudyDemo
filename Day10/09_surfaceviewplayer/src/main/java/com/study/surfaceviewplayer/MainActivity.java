package com.study.surfaceviewplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.width;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_play)
    Button btnPlay;
    @Bind(R.id.surfaceView)
    SurfaceView surfaceView;
    private SurfaceHolder holder;
    private int width;
    private int height;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        surfaceView.post(new Runnable() {

            @Override
            public void run() {
                width = surfaceView.getWidth() / 2;
                height = surfaceView.getHeight() / 2;
            }
        });

        holder = surfaceView.getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                Log.d("surfaceviewplayer", "surfaceCreated");

                flag = true;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100; i++) {
                            if (!flag) {
                                return;
                            }
                            Canvas canvas = holder.lockCanvas();
                            canvas.drawColor(Color.BLACK); // 初始化
                            Paint paint = new Paint();
                            paint.setColor(0xFFFF88CC);
                            float redius = 20 + i;
                            canvas.drawCircle(width, height, redius, paint);
                            holder.unlockCanvasAndPost(canvas);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d("surfaceviewplayer", "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("surfaceviewplayer", "surfaceDestroyed");
                flag = false;

            }
        });

    }

    @OnClick(R.id.btn_play)
    public void onClick() {

    }
}
