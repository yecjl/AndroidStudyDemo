package com.study.musicservice.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 功能：music 数据库的帮助类
 * Created by danke on 2017/4/1.
 */

public class MusicDBHelper extends SQLiteOpenHelper {
    public MusicDBHelper(Context context) {
        super(context, "music.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_music(" +
                "_id integer primary key autoincrement, " +
                "name varchar(20), path varchar(50), playTimes integer, isPlaying boolean, playSeek integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
