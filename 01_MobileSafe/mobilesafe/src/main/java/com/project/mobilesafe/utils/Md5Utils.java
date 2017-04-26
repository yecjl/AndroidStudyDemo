package com.project.mobilesafe.utils;

import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.ContentValues.TAG;

/**
 * 功能：Md5 加密
 * Created by danke on 2017/4/26.
 */

public class Md5Utils {

    /**
     * 密码的Md5加密
     *
     * @param password
     * @return
     */
    public static String encode(String password) {
        StringBuilder sb = new StringBuilder();
        try {
            if (!TextUtils.isEmpty(password)) {
                MessageDigest md = MessageDigest.getInstance("md5");
                byte[] digest = md.digest(password.getBytes());
                for (int i = 0; i < digest.length; i++) {
                    Log.i(TAG, "byte: " + digest[i]);
                    int number = digest[i] & 0xff - 3; // 加盐
                    Log.i(TAG, "number: " + number);
                    String result = Integer.toHexString(number);
                    if (result.length() == 1) {
                        sb.append("0" + result);
                    } else {
                        sb.append(result);
                    }
                }
                Log.i(TAG, "result: " + sb.toString());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
