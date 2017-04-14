package com.study.bankdb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 功能：bank.db 的内容提供者
 * Created by danke on 2017/4/6.
 */

public class BankContentProvider extends ContentProvider {
    private final static int SUCCESS = 1;
    static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI("com.study.bankdb", "account", SUCCESS);
    }

    private BankSQLiteHelper mHelper;

    @Override
    public boolean onCreate() {
        Log.d("BankContentProvider", "onCreate ContentProvider");
        mHelper = new BankSQLiteHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d("BankContentProvider", "query 数据被查询了");
        if (matcher.match(uri) == SUCCESS) {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            return db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
        } else {
            throw new IllegalArgumentException("口令不正确");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("BankContentProvider", "insert 数据被添加了");
        if (matcher.match(uri) == SUCCESS) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            long result = db.insert("account", null, values);
            db.close();
            // 设置内容观察者
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse(result + "");
        } else {
            throw new IllegalArgumentException("口令不正确");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("BankContentProvider", "delete 数据被删除了");
        if (matcher.match(uri) == SUCCESS) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            int result = db.delete("account", selection, selectionArgs);
            db.close();
            getContext().getContentResolver().notifyChange(uri, null);
            return result;
        } else {
            throw new IllegalArgumentException("口令不正确");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d("BankContentProvider", "update 数据被更新了");
        if (matcher.match(uri) == SUCCESS) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            int result = db.update("account", values, selection, selectionArgs);
            db.close();
            getContext().getContentResolver().notifyChange(uri, null);
            return result;
        } else {
            throw new IllegalArgumentException("口令不正确");
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
