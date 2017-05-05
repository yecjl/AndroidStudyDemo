package com.project.mobilesafe.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.mobilesafe.utils.MatchUtils;

import java.io.File;

/**
 * 功能：操作address.db 号码归属地
 * Created by danke on 2017/5/5.
 */

public class AddressDao {
    private String path;

    public AddressDao(Context context) {
        File file = new File(context.getFilesDir(), "address.db");
        path = file.getAbsolutePath();
    }

    /**
     * 根据号码查询归属地
     *
     * @param number
     * @return
     */
    public String findLocation(String number) {
        String location = "";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);


        if (MatchUtils.matchPhone(number)) { // 匹配手机号
            Cursor cursor = db.rawQuery("select location from data2 where id = (select outkey from data1 where id=?);", new String[]{number.substring(0, 7)});
            if (cursor.moveToNext()) {
                location = cursor.getString(0);
            } else {
                location = "查无此号";
            }
            cursor.close();

        } else if (MatchUtils.matchTelephone(number)) { // 匹配电话号码
            Cursor cursor2Number = db.rawQuery("select location from data2 where area=?", new String[]{number.substring(1, 3)});
            if (cursor2Number.moveToNext()) {
                location = cursor2Number.getString(0);
                location = location.substring(0, location.length() - 2);
            } else {
                Cursor cursor3Number = db.rawQuery("select location from data2 where area=?", new String[]{number.substring(1, 4)});
                if (cursor3Number.moveToNext()) {
                    location = cursor3Number.getString(0);
                    location = location.substring(0, location.length() - 2);
                } else {
                    location = "查无此号";
                }
                cursor3Number.close();
            }
            cursor2Number.close();
        } else {
            location = "查询中...";
        }

        db.close();
        return location;
    }
}
