package com.study.callphonereceive;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_ip)
    EditText etIp;
    @Bind(R.id.btn_save)
    Button btnSave;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sp = getSharedPreferences("config", 0);
        etIp.setText(sp.getString("ip", ""));
    }

    @OnClick(R.id.btn_save)
    public void onClick() {
        String ip = etIp.getText().toString().trim();
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("ip", ip);
        edit.commit();
        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
    }
}
