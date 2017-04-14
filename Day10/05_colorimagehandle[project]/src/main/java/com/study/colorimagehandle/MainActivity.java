package com.study.colorimagehandle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ch_cyan_red)
    ColorHandler chCyanRed;
    @Bind(R.id.ch_purple_green)
    ColorHandler chPurpleGreen;
    @Bind(R.id.ch_yellow_blue)
    ColorHandler chYellowBlue;
    @Bind(R.id.iv_object)
    ImageView ivObject;

    private float[] srcFloat = new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0};
    private Paint paint;
    private ColorMatrix colorMatrix;
    private Canvas canvas;
    private Bitmap newBitmap;
    private Bitmap mBitmap;

    private static final int ID_CYAN_RED = 0;
    private static final int ID_PURPLE_GREEN = 1;
    private static final int ID_YELLOW_BLUE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        chCyanRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        chCyanRed.setListener(mOnProgressListener, ID_CYAN_RED);
        chPurpleGreen.setListener(mOnProgressListener, ID_PURPLE_GREEN);
        chYellowBlue.setListener(mOnProgressListener, ID_YELLOW_BLUE);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img02);
        newBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
        canvas = new Canvas(newBitmap);
        colorMatrix = new ColorMatrix();
        paint = new Paint();

    }

    ColorHandler.onProgressListener mOnProgressListener = new ColorHandler.onProgressListener() {

        @Override
        public void onProgressChanged(float progress, int id) {
            int index = 0;
            switch (id) {
                case ID_CYAN_RED:
                    index = 0;
                    break;
                case ID_PURPLE_GREEN:
                    index = 6;
                    break;
                case ID_YELLOW_BLUE:
                    index = 12;
                    break;
            }

            srcFloat[index] = progress;
            colorMatrix.set(srcFloat);
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            canvas.drawBitmap(mBitmap, new Matrix(), paint);
            ivObject.setImageBitmap(newBitmap);
        }

    };
}
