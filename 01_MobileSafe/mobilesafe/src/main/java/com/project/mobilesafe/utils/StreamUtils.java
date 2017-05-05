package com.project.mobilesafe.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 功能：
 * Created by danke on 2017/4/18.
 */

public class StreamUtils {
    /**
     * 从InputStream中读取字符串
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String readStream(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        return bos.toString();
    }

    /**
     * 读取资产目录下文件保存到\data\data\<package>\files下的路径
     *
     * @param context
     * @param assetName 对应资产文件名
     * @return
     */
    public static void readAssets(Context context, String assetName) {
        try {
            // 将资产目录下的文件写入到data目录下
            File file = new File(context.getFilesDir(), assetName);
            if (!file.exists() || file.length() == 0) {
                InputStream is = context.getAssets().open(assetName);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
