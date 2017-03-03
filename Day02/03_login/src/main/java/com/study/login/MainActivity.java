package com.study.login;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

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
    private String mFileName;
    private File mExternalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cbRemember.setOnClickListener(onClickListener);
        btLogin.setOnClickListener(onClickListener);

        // （方法一）将文件保存到私有的当前应用程序下面
//        mFileName = getFilesDir() + File.separator;

        /**
         * （方法二）将文件保存到sd卡外部公共存储空间
         * 1）获取外部存储空间的路径
         * 2）判断当前sd卡的状态
         * 3）判断当前sd卡的容量
         * 4）获取读写权限
         */
        mExternalFile = Environment.getExternalStorageDirectory();

        String info = readFileToInfo();
        if (!isNull(info)) {
            String[] split = info.split("##");
            etQQ.setText(split[0]);
            etPassword.setText(split[1]);
        }
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
                        saveInfoToFile(qq + "##" + password);
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

    public void saveInfoToFile(String str) {
        // 判断当前sd卡的状态
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "sd卡不可用,请检查sd卡的状态", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // 判断当前sd卡的容量
            String usableFileSize = Formatter.formatFileSize(this, mExternalFile.getUsableSpace());
            Toast.makeText(this, "可用空间为：" + usableFileSize, Toast.LENGTH_SHORT).show();

            File file = new File(mExternalFile, "info.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.close();
            toast("保存成功");
        } catch (Exception e) {
            toast("保存失败");
            e.printStackTrace();
        }
    }

    public String readFileToInfo() {
        File file = new File(mExternalFile, "info.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                return br.readLine();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}
