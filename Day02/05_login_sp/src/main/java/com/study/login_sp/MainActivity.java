package com.study.login_sp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_qq)
    EditText etQQ;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.cb_remember)
    CheckBox cbRemember;
    @Bind(R.id.bt_login)
    Button btLogin;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cbRemember.setOnClickListener(onClickListener);
        btLogin.setOnClickListener(onClickListener);

        // 通过SharedPreference来保存数据，会自动生成config.xml文件，在shared_prefs文件夹下
        mSharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);

        // 数据回显
        etQQ.setText(mSharedPreferences.getString("qq", ""));
        etPassword.setText(mSharedPreferences.getString("password", ""));
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_login: // 登录
                    String qq = getInput(etQQ);
                    String password = getInput(etPassword);
                    if (isNull(qq) || isNull(password)) {
                        toast("账号或者密码不能为空");
                        return;
                    }

                    if (cbRemember.isChecked()) {
                        // 获取编辑器
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString("qq", qq);
                        editor.putString("password", password);
                        // 提交保存
                        editor.commit();
                    }
                    break;

                case R.id.cb_remember: // 记住密码
                    log(cbRemember.isChecked() ? "记住密码" : "不记住密码");
                    break;
            }
        }
    };

    public String getInput(EditText editText) {
        if (editText == null) {
            return null;
        }

        return editText.getText().toString();
    }

    public boolean isNull(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void log(String str) {
        Log.d(getClass().toString(), str);
    }
}
