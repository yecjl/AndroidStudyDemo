package com.study.bankdb;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BankSQLiteHelper helper = new BankSQLiteHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.close();
    }
}
