package com.study.smsobserve;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_backup)
    Button btnBackup;
    @Bind(R.id.btn_restore)
    Button btnRestore;
    private Uri mUri;
    private ContentResolver mResolver;

    private String path = Environment.getExternalStorageDirectory() + File.separator + "Danke" + File.separator + "Backup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mUri = Uri.parse("content://sms");
        mResolver = getContentResolver();
        mResolver.registerContentObserver(mUri, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                Log.d("smsobserve", "短信数据发生变化");
            }
        });
    }

    @OnClick(R.id.btn_backup)
    public void backup() {
        try {
            XmlSerializer serializer = Xml.newSerializer();
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(new File(file, "sms.xml"));
            serializer.setOutput(fos, "utf-8");
            Cursor cursor = mResolver.query(mUri, new String[]{"type", "address", "date", "body"}, null, null, null);
            if (cursor != null) {
                serializer.startDocument("utf-8", true);
                serializer.startTag(null, "root");
                while (cursor.moveToNext()) {
                    String type = cursor.getString(0);
                    String address = cursor.getString(1);
                    String date = cursor.getString(2);
                    String body = cursor.getString(3);

                    serializer.startTag(null, "sms");
                    serializer.startTag(null, "type").text(type).endTag(null, "type");
                    serializer.startTag(null, "address").text(address).endTag(null, "address");
                    serializer.startTag(null, "date").text(date).endTag(null, "date");
                    serializer.startTag(null, "body");

                    // 内容因为无法转换为utf-8可能会抛出异常
                    try {
                        serializer.text(body);
                    } catch (Exception e) {
                        Log.d("sms", address + ": 短信内容无法转换为utf-8 抛出异常");
                    }
                    serializer.endTag(null, "body");
                    serializer.endTag(null, "sms");
                }
                cursor.close();
                serializer.endTag(null, "root");
                serializer.endDocument();
            }
            fos.close();
            Toast.makeText(this, "短信备份成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "短信备份失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_restore)
    public void restore() {
        try {
            XmlPullParser pullParser = Xml.newPullParser();
            FileInputStream fis = new FileInputStream(new File(path, "sms.xml"));
            pullParser.setInput(fis, "utf-8");

            int type = pullParser.getEventType();
            ContentValues values = null;
            while (type != XmlPullParser.END_DOCUMENT) {
                if (type == XmlPullParser.START_TAG) {
                    String name = pullParser.getName();
                    switch (name) {
                        case "sms":
                            values = new ContentValues();
                            break;
                        case "type":
                            values.put("type", pullParser.nextText());
                            break;
                        case "address":
                            values.put("address", pullParser.nextText());
                            break;
                        case "date":
                            values.put("date", pullParser.nextText());
                            break;
                        case "body":
                            values.put("body", pullParser.nextText());
                            break;
                    }
                } else if (type == XmlPullParser.END_TAG) {
                    String name = pullParser.getName();
                    switch (name) {
                        case "sms":
                            mResolver.insert(mUri, values);
                            values = null;
                            break;
                    }

                }
                type = pullParser.next();
            }

            fis.close();
            Toast.makeText(this, "短信还原成功", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "短信还原失败", Toast.LENGTH_SHORT).show();
        }
    }
}