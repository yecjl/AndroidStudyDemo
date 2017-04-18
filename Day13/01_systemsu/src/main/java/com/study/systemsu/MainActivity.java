package com.study.systemsu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stericson.RootTools.RootTools;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Runtime.getRuntime().exec("su");
            RootTools.sendShell("sleep 5;reboot;", 30000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
