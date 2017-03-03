package com.study.otherfilepermission;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.btn_readPrivate)
    Button btnReadPrivate;
    @Bind(R.id.btn_writePrivate)
    Button btnWritePrivate;
    @Bind(R.id.btn_readPublic)
    Button btnReadPublic;
    @Bind(R.id.btn_writePublic)
    Button btnWritePublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_readPrivate)
    public void readPrivate(View view) {
        read("/data/data/com.study.filepermission/files/private.txt");
    }

    @OnClick(R.id.btn_writePrivate)
    public void writePrivate(View view) {
        write("/data/data/com.study.filepermission/files/private.txt");
    }

    @OnClick(R.id.btn_readPublic)
    public void readPublic(View view) {
        read("/data/data/com.study.filepermission/files/public.txt");
    }

    @OnClick(R.id.btn_writePublic)
    public void writePublic(View view) {
        write("/data/data/com.study.filepermission/files/public.txt");
    }

    public void read(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = br.readLine();
            Toast.makeText(this, "读取成功" + line, Toast.LENGTH_SHORT).show();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "读取失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void write(String fileName) {
        try {
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("新的内容".getBytes());
            fos.close();
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "写入失败", Toast.LENGTH_SHORT).show();
        }
    }
}
