package com.study.filepermission;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.btn_private)
    Button btnPrivate;
    @Bind(R.id.btn_public)
    Button btnPublic;
    @Bind(R.id.btn_read)
    Button btnRead;
    @Bind(R.id.btn_write)
    Button btnWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_private)
    public void btnPrivate(View view) {
        try {
            File file = new File(getFilesDir(), "private.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("It's private".getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_public)
    public void btnPublic(View view) {
        try {
            FileOutputStream fos = openFileOutput("public.txt", Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
            fos.write("It's public!".getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_read)
    public void read(View view) {
        try {
            FileOutputStream fos = openFileOutput("readOnly.txt", Context.MODE_WORLD_READABLE);
            fos.write("It's readOnly!".getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_write)
    public void write(View view) {
        try {
            FileOutputStream fos = openFileOutput("writeOnly.txt", Context.MODE_WORLD_WRITEABLE);
            fos.write("It's writeOnly!".getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }
}

