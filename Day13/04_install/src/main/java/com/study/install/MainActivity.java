package com.study.install;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stericson.RootTools.RootTools;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_install)
    public void onClick() {
        try {
            RootTools.sendShell("pm install data/local/tmp/11.apk; " +
                    "am start -n com.study.disableapp/com.study.disableapp.MainActivity" +
                    "pm uninstall com.study.disableapp", 30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
