package com.study.myimagenews.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.study.myimagenews.Constant;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 功能：
 * Created by danke on 2017/3/9.
 */

public class FileUtil {
    private static final int BUF_SIZE = 1024;

    /**
     * 从路径中读取Bitmap
     *
     * @param fileName
     * @return
     */
    public static Bitmap decodeFileBitmap(String fileName) {
        File file = new File(Constant.SAVE_IMAGE_PATH, fileName);
        if (file.exists() && file.length() > 0) {
            Log.d("FileUtil", "图片读取成功");
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        Log.d("FileUtil", "没有读取到图片");
        return null;
    }

    /**
     * 从路径中读取Bitmap
     *
     * @param fileName
     * @return
     */
    public static String decodeFile(String fileName) {
        File file = new File(Constant.SAVE_IMAGE_PATH, fileName);
        if (file.exists() && file.length() > 0) {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                Log.d("FileUtil", "文件读取成功");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("FileUtil", "文件读取失败");
            }
            return sb.toString();
        }
        return null;
    }

    public static Object decodeFileObject(String fileName) {
        Object obj = null;
        File file = new File(Constant.SAVE_IMAGE_PATH, fileName);
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                obj = ois.readObject();
                fis.close();
                ois.close();
                Log.d("FileUtil", "文件对象读取成功");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("FileUtil", "文件对象读取失败");
            }
        }
        return obj;
    }

    /**
     * 保存本地图片
     *
     * @param context
     * @param fileName
     * @param bitmap
     */
    public static void saveBitmap(Context context, String fileName, Bitmap bitmap) {
        // 判断当前sd卡的状态
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "sd卡不可用,请检查sd卡的状态", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File file = getFile(Constant.SAVE_IMAGE_PATH, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Log.d("FileUtil", "图片保存成功");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FileUtil", "图片保存失败");
        }
    }

    /**
     * 保存文件
     *
     * @param fileName
     * @param is
     * @throws IOException
     */
    public static void saveFile(String fileName, InputStream is) {
        // 判断当前sd卡的状态
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d("FileUtil", "sd卡不可用,请检查sd卡的状态");
            return;
        }
        File file = getFile(Constant.SAVE_IMAGE_PATH, fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[BUF_SIZE];
            int len = -1;
            while ((len = is.read(buffer, 0, BUF_SIZE)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            bos.close();
            Log.d("FileUtil", "文件保存成功");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FileUtil", "文件保存失败");
        }
    }

    /**
     * 保存对象到文件中
     *
     * @param fileName
     * @param object
     */
    public static void saveObject(String fileName, Object object) {
        // 判断当前sd卡的状态
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d("FileUtil", "sd卡不可用,请检查sd卡的状态");
            return;
        }

        try {
            // 将对象写到内存中
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
            // 获取对象的内存数据
            byte[] buffer = bos.toByteArray();
            // 将字节数组写到文件中
            File file = getFile(Constant.SAVE_IMAGE_PATH, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.flush();
            fos.close();
            Log.d("FileUtil", "文件对象保存成功");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FileUtil", "文件对象保存失败");
        }
    }

    /**
     * 创建文件
     *
     * @param dirName
     * @param fileName
     * @return
     */
    private static File getFile(String dirName, String fileName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return new File(dir, fileName);
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExists(String fileName) {
        File file = new File(Constant.SAVE_IMAGE_PATH, fileName);
        if (file.exists() && file.length() > 0) {
            return true;
        }

        return false;
    }
}
