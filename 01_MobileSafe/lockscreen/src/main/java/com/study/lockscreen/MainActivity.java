package com.study.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_lock)
    public void lockScreen() {
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, LockScreenReceiver.class);
        boolean adminActive = dpm.isAdminActive(componentName);
        if (adminActive) {
            dpm.lockNow();
        } else {
            Toast.makeText(this, "请先激活设备管理器", Toast.LENGTH_SHORT).show();

            // Launch the activity to have the user enable our admin.
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.sample_device_admin_description));
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_uninstall)
    public void uninstall() {
        // 取消激活设备管理器
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, LockScreenReceiver.class);
        dpm.removeActiveAdmin(componentName);

        // 卸载
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_DELETE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
