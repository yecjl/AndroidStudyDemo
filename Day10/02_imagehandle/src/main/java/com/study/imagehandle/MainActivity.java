package com.study.imagehandle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_scale)
    Button btnScale;
    @Bind(R.id.btn_rotate)
    Button btnRotate;
    @Bind(R.id.btn_translation)
    Button btnTranslation;
    @Bind(R.id.iv_bg)
    ImageView ivBg;
    @Bind(R.id.iv_object)
    ImageView ivObject;
    @Bind(R.id.btn_verticalSymmetry)
    Button btnVerticalSymmetry;
    @Bind(R.id.btn_horizontalSymmetry)
    Button btnHorizontalSymmetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ivBg.setAlpha(0.2f);
    }

    @OnClick(R.id.btn_scale)
    public void scale() {
        Bitmap bitmap = ((BitmapDrawable) ivObject.getDrawable()).getBitmap();
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth() * 2, bitmap.getHeight() * 2, bitmap.getConfig());
        Matrix matrix = new Matrix();
        matrix.setScale(2f, 2f);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(bitmap, matrix, paint);
        ivObject.setImageBitmap(newBitmap);
    }

    @OnClick(R.id.btn_rotate)
    public void rotate() {
        Bitmap bitmap = ((BitmapDrawable) ivObject.getDrawable()).getBitmap();
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Matrix matrix = new Matrix();
        matrix.setRotate(30, newBitmap.getWidth() / 2, newBitmap.getHeight() / 2);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(bitmap, matrix, paint);
        ivObject.setImageBitmap(newBitmap);
    }

    @OnClick(R.id.btn_translation)
    public void translation() {
        Bitmap bitmap = ((BitmapDrawable) ivObject.getDrawable()).getBitmap();
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Matrix matrix = new Matrix();
        matrix.setTranslate(100, 0);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(bitmap, matrix, paint);
        ivObject.setImageBitmap(newBitmap);
    }

    @OnClick(R.id.btn_verticalSymmetry)
    public void verticalSymmetry() {
        Bitmap bitmap = ((BitmapDrawable) ivObject.getDrawable()).getBitmap();
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        matrix.postTranslate(newBitmap.getWidth(), 0);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(bitmap, matrix, paint);
        ivObject.setImageBitmap(newBitmap);
    }

    @OnClick(R.id.btn_horizontalSymmetry)
    public void horizontalSymmetry() {
        Bitmap bitmap = ((BitmapDrawable) ivObject.getDrawable()).getBitmap();
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        matrix.postTranslate(0, newBitmap.getHeight());
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(bitmap, matrix, paint);
        ivObject.setImageBitmap(newBitmap);
    }
}
