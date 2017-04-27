package com.project.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 功能：黑名单的数据库帮助类
 * Created by danke on 2017/4/27.
 */

public class BlackListSQLiteOpenHelper extends SQLiteOpenHelper {
    public BlackListSQLiteOpenHelper(Context context) {
        super(context, "danke.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // _id 主键自增长，phone 电话号码，mode 拦截模式
        db.execSQL("create table blacklist (_id integer primary key autoincrement, phone varchar(20), mode varchar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
