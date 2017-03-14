package com.study.threaddownload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_down)
    Button btnDown;
    @Bind(R.id.et_path)
    EditText etPath;
    @Bind(R.id.pb0)
    ProgressBar pb0;
    @Bind(R.id.pb1)
    ProgressBar pb1;
    @Bind(R.id.pb2)
    ProgressBar pb2;

    private int threadCount = 3;
    private String downPath = Environment.getExternalStorageDirectory() + "/Danke";
    private int runningThreadCount;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_down)
    public void onClick() {
        path = etPath.getText().toString().trim();
        if (TextUtils.isEmpty(path) && !path.startsWith("http://")) {
            Toast.makeText(this, "下载地址不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    if (conn.getResponseCode() == 200) {
                        int length = conn.getContentLength();
                        Log.d("MainActivity", "文件大小： " + length);
                        // 创建一个和服务器数据一样大小的空文件
                        RandomAccessFile accessFile = new RandomAccessFile(getFile(path), "rw");
                        accessFile.setLength(length);
                        accessFile.close();

                        // 设置每个线程需要下载的其实位置和结束位置
                        int blockSize = length / threadCount;
                        for (int i = 0; i < threadCount; i++) {
                            int startPosition = i * blockSize;
                            int endPosition = 0;
                            if (i == threadCount - 1) {
                                endPosition = length - 1;
                            } else {
                                endPosition = (i + 1) * blockSize - 1;
                            }

                            Log.d("MainActivity", "线程" + i + ", 需要下载：" + startPosition + " ~~ " + endPosition);

                            runningThreadCount++;
                            new DownloadThread(i, startPosition, endPosition).start();
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class DownloadThread extends Thread {
        private int threadId;
        private int startPosition; // 默认初始下载位置
        private int endPosition;
        private int currentPosition; //当前真正下载的位置

        public DownloadThread(int threadId, int startPosition, int endPosition) {
            this.threadId = threadId;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
            currentPosition = startPosition;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                // 判断是否已经下载过当前文件, 从当前的配置文件中读取下载进度
                File config = new File(downPath, threadId + ".config");
                if (config.exists() && config.length() > 0) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(config)));
                    currentPosition = Integer.parseInt(br.readLine());
                    br.close();
                }

                conn.setRequestProperty("Range", "bytes=" + currentPosition + "-" + endPosition);
                if (conn.getResponseCode() == 206) {
                    InputStream is = conn.getInputStream();
                    RandomAccessFile contentRaf = new RandomAccessFile(getFile(path), "rw");
                    contentRaf.seek(currentPosition);
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        contentRaf.write(buffer, 0, len);

                        // 更新当前的下载进度
                        currentPosition += len;
                        // 实时保存到文件中
                        RandomAccessFile configRaf = new RandomAccessFile(config, "rwd");
                        configRaf.write(String.valueOf(currentPosition).getBytes());
                        configRaf.close();
                        Log.d("MainActivity", "线程" + threadId + ", currentPosition:" + currentPosition);

                        // 更新进度条
                        int max = endPosition - startPosition;
                        int progress = currentPosition - startPosition;
                        switch (threadId) {
                            case 0:
                                pb0.setMax(max);
                                pb0.setProgress(progress);
                                break;
                            case 1:
                                pb1.setMax(max);
                                pb1.setProgress(progress);
                                break;
                            case 2:
                                pb2.setMax(max);
                                pb2.setProgress(progress);
                                break;
                        }
                    }
                    contentRaf.close();
                    is.close();
                    Log.d("MainActivity", "线程" + threadId + "下载完毕");

                    // 给下载好的线程对应的下载配置重命名
                    config.renameTo(new File(downPath, threadId + ".config.finished"));

                    synchronized (MainActivity.this) {
                        runningThreadCount--;
                        if (runningThreadCount <= 0) {
                            for (int i = 0; i < threadCount; i++) {
                                File file = new File(downPath, i + ".config.finished");
                                // 删除下载配置文件
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile(String path) {
        return new File(downPath, path.substring(path.lastIndexOf("/") + 1) + ".jpg");
    }
}
