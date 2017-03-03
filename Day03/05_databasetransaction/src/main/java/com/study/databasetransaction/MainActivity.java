package com.study.databasetransaction;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        DBOpenHelper helper = new DBOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update account set money = money - 1000 where name = 'danke'");
            // 模拟异常
            s.equals("danke");
            db.execSQL("update account set money = money + 1000 where name = 'outa'");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
    }
}
