package com.project.mobilesafe.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.project.mobilesafe.R;
import com.project.mobilesafe.bean.UploadInfo;
import com.project.mobilesafe.constant.Constant;
import com.project.mobilesafe.utils.ErrorCodeUtils;
import com.project.mobilesafe.utils.PackageUtils;
import com.project.mobilesafe.utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    @Bind(R.id.tv_splash_version)
    TextView tvSplashVersion;
    private String versionName;
    private String TAG = "SplashActivity";

    private final static int SHOW_UPLOAD_DIALOG = 1;
    private final static int ERROR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        versionName = PackageUtils.getVersionName(this);
        tvSplashVersion.setText("版本号：" + versionName);

        new Thread(mCheckVersionTask).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_UPLOAD_DIALOG:
                    UploadInfo uploadInfo = (UploadInfo) msg.obj;
                    showUploadDialog(uploadInfo);
                    break;
                case ERROR:
                    Toast.makeText(SplashActivity.this, "错误代码：" + msg.obj, Toast.LENGTH_SHORT).show();
                    loadMainUI();
                    break;
            }
        }
    };

    /**
     * 提示用户是否下载更新app
     *
     * @param uploadInfo
     */
    private void showUploadDialog(final UploadInfo uploadInfo) {
        new AlertDialog.Builder(this)
                .setTitle("又有新版本啦!")
                .setMessage(uploadInfo.getIntroduction())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 判断sd卡是否被挂在
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            final ProgressDialog progressDialog = new ProgressDialog(SplashActivity.this);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setMessage("正在下载中...");
                            progressDialog.show();
                            progressDialog.setCancelable(false);
                            HttpUtils httpUtils = new HttpUtils(5000);
                            httpUtils.download(uploadInfo.getAppLink(), Constant.downLoadPath + "mobile" + uploadInfo.getAppVersion() + ".apk",
                                    new RequestCallBack<File>() {
                                        @Override
                                        public void onLoading(long total, long current, boolean isUploading) {
                                            progressDialog.setMax((int) total);
                                            progressDialog.setProgress((int) current);
                                            super.onLoading(total, current, isUploading);
                                        }

                                        @Override
                                        public void onSuccess(ResponseInfo<File> responseInfo) {
                                            // 替换安装
                                            progressDialog.dismiss();
                                            Intent intent = new Intent();
                                            intent.setAction(Intent.ACTION_VIEW);
                                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                                            intent.setDataAndType(Uri.parse("file://" + responseInfo.result.getPath()), "application/vnd.android.package-archive");
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(HttpException error, String msg) {
                                            progressDialog.dismiss();
                                            // 提示用户错误代码
                                            Toast.makeText(SplashActivity.this, "错误代码：" + msg, Toast.LENGTH_SHORT).show();
                                            loadMainUI();
                                        }
                                    });
                        } else {
                            Toast.makeText(SplashActivity.this, "sd卡不可用，无法自动更新", Toast.LENGTH_SHORT).show();
                            loadMainUI();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadMainUI();
                    }
                })
                .setCancelable(false)
                .show();
    }

    /**
     * 检查版本更新的子线程任务栈
     */
    private Runnable mCheckVersionTask = new Runnable() {
        @Override
        public void run() {
            Message message = Message.obtain();
            long startTime = System.currentTimeMillis();
            try {
                String path = getResources().getString(R.string.version) + "?appVersion=" + versionName + "&typeApp=" + 1 + "&typeSys=" + 1;
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                if (conn.getResponseCode() == 200) {
                    // 获取数据
                    InputStream is = conn.getInputStream();
                    String result = StreamUtils.readStream(is);
                    Log.i(TAG, result);
                    is.close();

                    // 解析JSON字符串
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String appVersion = data.getString("appVersion");
                    String appLink = data.getString("appLink");
                    String introduction = data.getString("introduction");

                    if (versionName.equals(appVersion)) {
                        SystemClock.sleep(2000);
                        // 版本号相同，直接打开MainActivity
                        loadMainUI();
                    } else {
                        // 弹出对话框，请求下载
                        UploadInfo uploadInfo = new UploadInfo();
                        uploadInfo.setAppLink(appLink);
                        uploadInfo.setAppVersion(appVersion);
                        uploadInfo.setIntroduction(introduction);
                        message.what = SHOW_UPLOAD_DIALOG;
                        message.obj = uploadInfo;
                    }
                } else {
                    message.what = ERROR;
                    message.obj = ErrorCodeUtils.RESPONSE_ERROR;
                }
            } catch (IOException e) {
                e.printStackTrace();
                message.what = ERROR;
                message.obj = ErrorCodeUtils.IO_ERROR;
            } catch (JSONException e) {
                e.printStackTrace();
                message.what = ERROR;
                message.obj = ErrorCodeUtils.JSON_ERROR;
            } finally {
                long endTime = System.currentTimeMillis();
                long dTime = endTime - startTime;
                if (dTime < 2000 && message.what == ERROR) {
                    SystemClock.sleep(2000 - dTime);
                }
                mHandler.sendMessage(message);
            }
        }
    };

    public void loadMainUI() {
        MainActivity.start(SplashActivity.this);
        finish();
    }
}
