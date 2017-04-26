package com.study.md5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity_md5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            String password = "123";
            byte[] digest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                Log.i(TAG, "byte: " + digest[i]);
                int number = digest[i] & 0xff;
                Log.i(TAG, "number: " + number);
                String result = Integer.toHexString(number);
                if (result.length() == 1) {
                    sb.append("0" + result);
                } else {
                    sb.append(result);
                }
            }
            Log.i(TAG, "result: " + sb.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
