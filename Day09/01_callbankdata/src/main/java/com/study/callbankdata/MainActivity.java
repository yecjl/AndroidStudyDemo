package com.study.callbankdata;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
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
        Uri url = Uri.parse("content://com.study.bankdb/account");

        switch (view.getId()) {
            case R.id.btn_insert:
                ContentValues insertValues = new ContentValues();
                insertValues.put("name", "danke");
                insertValues.put("money", "100");
                Uri uri = resolver.insert(url, insertValues);
                if (uri != null) {
                    Toast.makeText(this, "添加成功，添加到第" + uri.toString() + "条数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_delete:
                int deleteResult = resolver.delete(url, "name=?", new String[]{"danke"});
                if (deleteResult >= 0) {
                    Toast.makeText(this, "删除了" + deleteResult + "条数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update:
                ContentValues updateValues = new ContentValues();
                updateValues.put("money", 200);
                int updateResult = resolver.update(url, updateValues, "name=?", new String[]{"danke"});
                if (updateResult >= 0) {
                    Toast.makeText(this, "修改了" + updateResult + "条数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_search:
                Cursor cursor = resolver.query(url, new String[]{"name", "money"}, null, null, null);
                if (cursor != null) {
                    StringBuilder sb = new StringBuilder();
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(0);
                        int money = cursor.getInt(1);
                        sb.append("姓名： " + name + ", 金额: " + money + "\r\n");
                    }
                    cursor.close();
                    tvResult.setText(sb.toString());
                }
                break;
        }
    }
}
