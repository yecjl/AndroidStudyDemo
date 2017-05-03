package com.project.mobilesafe.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.mobilesafe.beans.BlackContact;
import com.project.mobilesafe.db.BlackListSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：黑名单列表的数据库操作类
 * Created by danke on 2017/4/27.
 */

public class BlackListDao {

    private BlackListSQLiteOpenHelper mHelper;

    public BlackListDao(Context context) {
        mHelper = new BlackListSQLiteOpenHelper(context);
    }

    /**
     * 添加黑名单
     *
     * @param blackContact
     * @return 添加到哪一行
     */
    public boolean insert(BlackContact blackContact) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", blackContact.getPhone());
        values.put("mode", blackContact.getMode());
        long result = db.insert("blacklist", null, values);
        db.close();
        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除黑名单
     *
     * @param phone
     * @return 是否删除失败
     */
    public boolean delete(String phone) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int result = db.delete("blacklist", "phone=?", new String[]{phone});
        db.close();
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改黑名单的拦截模式
     *
     * @param blackContact
     * @return 是否更新成功
     */
    public boolean updateMode(BlackContact blackContact) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mode", blackContact.getMode());
        int result = db.update("blacklist", values, "phone=?", new String[]{blackContact.getPhone()});
        db.close();
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查找黑名单列表
     *
     * @return
     */
    public List<BlackContact> findAll() {
        List<BlackContact> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query("blacklist", new String[]{"phone", "mode"}, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String phone = cursor.getString(0);
                String mode = cursor.getString(1);
                BlackContact blackContact = new BlackContact();
                blackContact.setPhone(phone);
                blackContact.setMode(mode);
                list.add(blackContact);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    /**
     * 查找指定的黑名单
     *
     * @param phone
     * @return
     */
    public BlackContact findBlackContact(String phone) {
        BlackContact blackContact = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query("blacklist", new String[]{"mode"}, "phone=?", new String[]{phone}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String mode = cursor.getString(0);
                blackContact = new BlackContact();
                blackContact.setPhone(phone);
                blackContact.setMode(mode);
                cursor.close();
            }
        }
        db.close();
        return blackContact;
    }
}
