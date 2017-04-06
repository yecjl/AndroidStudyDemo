package com.study.bankdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 功能：
 * Created by danke on 2017/4/6.
 */

public class BankSQLiteHelper extends SQLiteOpenHelper {
    public BankSQLiteHelper(Context context) {
        super(context, "bank.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table account(_id integer primary key autoincrement, name varchar(20), money integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
