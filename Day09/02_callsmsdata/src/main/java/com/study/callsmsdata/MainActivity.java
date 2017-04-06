package com.study.callsmsdata;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_insert)
    Button btnInsert;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_update)
    Button btnUpdate;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_insert, R.id.btn_delete, R.id.btn_update, R.id.btn_search})
    public void onClick(View view) {
        ContentResolver resolver = getContentResolver();
        Uri url = Uri.parse("content://sms");
        switch (view.getId()) {
            case R.id.btn_insert:
                ContentValues values = new ContentValues();
                values.put("address", "18857092891");
                values.put("date", System.currentTimeMillis());
                values.put("body", "love you");
                resolver.insert(url, values);
                break;
            case R.id.btn_delete:
                resolver.delete(url, "address=?", new String[]{"18857090002"});
                break;
            case R.id.btn_update:
                break;
            case R.id.btn_search:
                Cursor cursor = resolver.query(url, new String[]{"address", "date", "body", "type"}, null, null, null);
                if (cursor != null) {
                    StringBuilder sb = new StringBuilder();
                    while (cursor.moveToNext()) {
                        String address = cursor.getString(0);
                        long date = cursor.getLong(1);
                        String body = cursor.getString(2);
                        int type = cursor.getInt(3);
                        sb.append("address: " + address + ", date: " + parseDate(date) + ", body: " + body + ", type: " + type + "\r\n");
                    }
                    cursor.close();
                    tvResult.setText(sb.toString());
                }
                break;
        }
    }

    public String parseDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
