package com.study.normaldialogue;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.checked;
import static android.webkit.WebSettings.PluginState.ON;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.okCancel, R.id.single, R.id.multiple, R.id.progress, R.id.progressBar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.okCancel:
                onCancel();
                break;
            case R.id.single:
                single();
                break;
            case R.id.multiple:
                multiple();
                break;
            case R.id.progress:
                progress();
                break;
            case R.id.progressBar:
                progressBar();
                break;
        }
    }

    /**
     * 进度条对话框
     */
    private void progressBar() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Remind");
        dialog.setMessage("There is loading data");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dialog.setProgress(i);
                }
                dialog.dismiss();
            }
        }).start();
    }

    /**
     * 进度对话框
     */
    private void progress() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Remind");
        dialog.setMessage("There is loading data");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }).start();
    }

    /**
     * 多选对话框
     */
    private void multiple() {
        final String[] items = new String[]{"peer", "banana", "apple", "cherry", "grape", "orange"};
        final boolean[] checkedItems = new boolean[]{false, true, false, true, false, false};
        new AlertDialog.Builder(this)
                .setTitle("多选对话框")
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(MainActivity.this, items[which] + " is " + isChecked, Toast.LENGTH_SHORT).show();
                        checkedItems[which] = isChecked;
                    }
                })
                .setNegativeButton("Don't choose.", null)
                .show();
    }

    /**
     * 单选对话框
     */
    private void single() {
        final String[] items = new String[]{"male", "female", "neutral"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("单选对话框")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, items[which] + " is selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Don't choose.", null);
        builder.show();
    }

    /**
     * 确定取消对话框
     */
    private void onCancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("确定取消对话框")
                .setMessage("Do you like danke?")
                .setPositiveButton("Yes, you do.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You like danke.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No, you don't.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You don't like danke.", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }
}
