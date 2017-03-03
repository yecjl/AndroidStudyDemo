package com.study.dbcreate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 功能：数据库的帮助类
 * Created by danke on 2017/2/27.
 */

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {
    public MyDatabaseOpenHelper(Context context) {
        super(context, "danke.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_student(_id integer primary key autoincrement, name varchar(20), phone varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table tb_student add account varchar(20)");
    }
}
