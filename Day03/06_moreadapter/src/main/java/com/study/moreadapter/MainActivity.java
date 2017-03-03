package com.study.moreadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ArrayAdapter, R.id.SimpleAdapter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ArrayAdapter:
                ArrayAdapterActivity.start(this);
                break;
            case R.id.SimpleAdapter:
                SimpleAdapterActivity.start(this);
                break;
        }
    }
}
