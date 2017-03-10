package com.study.myimagenews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.study.myimagenews.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 功能：加载网络图片
 * Created by danke on 2017/3/9.
 */

public class SmartImageView extends ImageView {

    private static final int SUCCESS = 1;
    private static final int ERROR = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    Bitmap bitmapNetwork = (Bitmap) msg.obj;
                    setImageBitmap(bitmapNetwork);
                    break;
                case ERROR:
                    Integer resId = (Integer) msg.obj;
                    if (resId != null) {
                        setImageResource(resId);
                    }
                    break;
            }
        }
    };

    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageUrl(final String path) {
        setImageUrl(path, null);
    }

    public void setImageUrl(final String path, final Integer errorId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 1、加载本地图片
                String fileName = path.replaceAll("/", "") + ".jpg";
                Bitmap bitmap = FileUtil.decodeFileBitmap(fileName);
                if (bitmap != null) {
                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                    Log.d("SmartImageView", "加载本地图片");
                    return;
                }

                // 2、加载网络图片
                InputStream is = null;
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                        Log.d("SmartImageView", "加载网络图片");

                        //3、保存网络图片到本地
                        FileUtil.saveBitmap(getContext(), fileName, bitmap);
                    } else {
                        Message msg = Message.obtain();
                        msg.what = ERROR;
                        msg.obj = errorId;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    msg.obj = errorId;
                    handler.sendMessage(msg);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Message msg = Message.obtain();
                        msg.what = ERROR;
                        msg.obj = errorId;
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }
}
