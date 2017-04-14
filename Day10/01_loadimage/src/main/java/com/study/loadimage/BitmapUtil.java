package com.study.loadimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.ExifInterface;
import android.util.Log;
import android.view.WindowManager;

import java.io.IOException;

import static android.content.Context.WINDOW_SERVICE;
import static android.graphics.BitmapFactory.decodeFile;

/**
 * 功能：图像处理
 * Created by danke on 2017/4/12.
 */

public class BitmapUtil {
    /**
     * 压缩图片
     *
     * @param context
     * @param path
     * @return
     */
    public static Bitmap pressBitmap(Context context, String path) {
        // 获取手机屏幕的宽高
        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Point screenPoint = new Point();
        wm.getDefaultDisplay().getSize(screenPoint);
        int screenWidth = screenPoint.x;
        int screenHeight = screenPoint.y;

        Log.d("loadimage", "screenWidth: " + screenWidth);
        Log.d("loadimage", "screenHeight: " + screenHeight);

        // 获取bitmap的真实宽高
        Point bitmapSize = getBitmapSizeExif(path);
        int realWidth = bitmapSize.x;
        int realHeight = bitmapSize.y;

        // 重新设置bitmap的长宽
        BitmapFactory.Options options = new BitmapFactory.Options();
        float ratioWidth = (float) (realWidth * 1.0 / screenWidth);
        float ratioHeight = (float) (realHeight * 1.0 / screenHeight);

        Log.d("loadimage", "ratioWidth: " + ratioWidth);
        Log.d("loadimage", "realHeight: " + ratioHeight);

        if (ratioWidth > 1 && ratioHeight > 1) {
            if (ratioWidth > ratioHeight) {
                options.inSampleSize = Math.round(ratioWidth); // 设置缩放比：四舍五入
            } else {
                options.inSampleSize = Math.round(ratioHeight);
            }
        } else {
            if (ratioWidth > 1) {
                options.inSampleSize = Math.round(ratioWidth);
            } else if (realHeight > 1) {
                options.inSampleSize = Math.round(ratioHeight);
            }
        }
        Log.d("loadimage", "options.inSampleSize: " + options.inSampleSize);

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        // 获取bitmap的新的宽高
        int newWidth = bitmap.getWidth();
        int newHeight = bitmap.getHeight();

        Log.d("loadimage", "newWidth: " + newWidth);
        Log.d("loadimage", "newHeight: " + newHeight);
        Log.d("loadimage", "---------------------------------------");
        return bitmap;
    }

    /**
     * 获取图片的真实宽高
     *
     * @param path
     * @return
     */
    public static Point getBitmapSize(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeFile(path, options);
        int realWidth = options.outWidth;
        int realHeight = options.outHeight;

        Log.d("loadimage", "realWidth: " + realWidth);
        Log.d("loadimage", "realHeight: " + realHeight);

        return new Point(realWidth, realHeight);
    }

    /**
     * 获取图片的真实宽高（根据图片的头信息）
     *
     * @param path
     * @return
     */
    public static Point getBitmapSizeExif(String path) {
        Point point = new Point();
        try {
            // 获取图片的头信息
            ExifInterface ei = new ExifInterface(path);
            point.x = ei.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
            point.y = ei.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("loadimage", "realWidth: " + point.x);
        Log.d("loadimage", "realHeight: " + point.y);
        return point;
    }
}
