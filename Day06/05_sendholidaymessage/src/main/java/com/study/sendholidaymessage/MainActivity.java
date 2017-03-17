package com.study.sendholidaymessage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.btn_select_phone)
    Button btnSelectPhone;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.btn_select_message)
    Button btnSelectMessage;
    @Bind(R.id.btn_send)
    Button btnSend;

    private final static short REQUEST_CODE_MESSAGE = 1;
    private final static short REQUEST_CODE_PHONE = 2;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sp = getSharedPreferences("config", 0);
        String phone = sp.getString("phone", "");
        etPhone.setText(phone);
        String message = sp.getString("message", "");
        etMessage.setText(message);
    }

    @Override
    protected void onDestroy() {
        String phone = etPhone.getText().toString().trim();
        String message = etMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(phone)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("phone", phone);
            editor.commit();
        }
        if (!TextUtils.isEmpty(message)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("message", message);
            editor.commit();
        }
        super.onDestroy();
    }

    @OnClick({R.id.btn_select_message, R.id.btn_select_phone, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_message:
                Intent messageIntent = new Intent(this, MessageActivity.class);
                startActivityForResult(messageIntent, REQUEST_CODE_MESSAGE);
                break;

            case R.id.btn_select_phone:
                Intent phoneIntent = new Intent(this, PhoneActivity.class);
                startActivityForResult(phoneIntent, REQUEST_CODE_PHONE);
                break;
            case R.id.btn_send:
                String phone = etPhone.getText().toString().trim();
                String message = etMessage.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(message)) {
                    Toast.makeText(this, "请输入正确的手机号码和短信", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("smsto:" + phone));
                intent.putExtra("sms_body", message);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_MESSAGE:
                    if (data != null) {
                        String message = data.getStringExtra("message");
                        etMessage.setText(message);
                    }
                    break;
                case REQUEST_CODE_PHONE:
                    if (data != null) {
                        String phone = data.getStringExtra("phone");
                        etPhone.setText(phone);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

