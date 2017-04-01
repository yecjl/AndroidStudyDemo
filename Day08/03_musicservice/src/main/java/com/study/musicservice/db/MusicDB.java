package com.study.musicservice.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.study.musicservice.model.MusicItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：music 数据库操作
 * Created by danke on 2017/4/1.
 */

public class MusicDB {
    private MusicDBHelper mHelper;

    public MusicDB(Context context) {
        mHelper = new MusicDBHelper(context);
    }

    /**
     * 增加一条数据信息
     *
     * @param musicItem
     * @return
     */
    public long insert(MusicItem musicItem) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", musicItem.getName());
        values.put("path", musicItem.getPath());
        values.put("playTimes", musicItem.getPlayTimes());
        values.put("isPlaying", musicItem.isPlaying());
        values.put("playSeek", musicItem.getPlaySeek());
        long result = db.insert("tb_music", null, values);
        db.close();
        return result;
    }

    /**
     * 根据路径删除一条信息。因为路径是唯一的
     *
     * @param path
     * @return
     */
    public int delete(String path) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int result = db.delete("tb_music", "path=?", new String[]{path});
        db.close();
        return result;
    }

    /**
     * 修改是否正在播放
     *
     * @param path
     * @param isPlaying
     * @return
     */
    public int updateIsPlaying(String path, boolean isPlaying) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isPlaying", isPlaying);
        int result = db.update("tb_music", values, "path=?", new String[]{path});
        db.close();
        return result;
    }

    /**
     * 修改播放次数
     *
     * @param path
     * @param playTimes
     * @return
     */
    public int updatePlayTims(String path, int playTimes) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("playTimes", playTimes);
        int result = db.update("tb_music", values, "path=?", new String[]{path});
        db.close();
        return result;
    }

    /**
     * 修改播放位置
     *
     * @param path
     * @param playSeek
     * @return
     */
    public int updatePlaySeek(String path, int playSeek) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("playSeek", playSeek);
        int result = db.update("tb_music", values, "path=?", new String[]{path});
        db.close();
        return result;
    }

    /**
     * 查找所有的Music列表
     *
     * @return
     */
    public List<MusicItem> findMusicAll() {
        ArrayList<MusicItem> musicItems = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query("tb_music", new String[]{"name", "path", "playTimes", "isPlaying", "playSeek"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String path = cursor.getString(1);
            int playTimes = cursor.getInt(2);
            boolean isPlaying = cursor.getInt(3) == 0 ? false : true;
            int playSeek = cursor.getInt(4);

            MusicItem musicItem = new MusicItem();
            musicItem.setName(name);
            musicItem.setPath(path);
            musicItem.setPlayTimes(playTimes);
            musicItem.setPlaying(isPlaying);
            musicItem.setPlaySeek(playSeek);
            musicItems.add(musicItem);
        }
        cursor.close();
        db.close();
        return musicItems;
    }

    /**
     * 根据路径查找Music数据
     *
     * @param path
     * @return
     */
    public MusicItem findMusicByPath(String path) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query("tb_music", new String[]{"name", "playTimes", "isPlaying", "playSeek"}, "path=?", new String[]{path}, null, null, null);
        MusicItem musicItem = null;
        if (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int playTimes = cursor.getInt(1);
            boolean isPlaying = cursor.getInt(2) == 0 ? false : true;
            int playSeek = cursor.getInt(3);

            musicItem = new MusicItem();
            musicItem.setName(name);
            musicItem.setPath(path);
            musicItem.setPlayTimes(playTimes);
            musicItem.setPlaying(isPlaying);
            musicItem.setPlaySeek(playSeek);
        }
        cursor.close();
        db.close();

        return musicItem;
    }

    /**
     * 根据是否正在播放查找Music数据
     *
     * @param isPlaying
     * @return
     */
    public MusicItem findMusicByIsPlaying(boolean isPlaying) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query("tb_music", new String[]{"name", "path", "playTimes", "playSeek"}, "isPlaying=?", new String[]{isPlaying ? "1" : "0"}, null, null, null);
        MusicItem musicItem = null;
        if (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String path = cursor.getString(1);
            int playTimes = cursor.getInt(2);
            int playSeek = cursor.getInt(3);

            musicItem = new MusicItem();
            musicItem.setName(name);
            musicItem.setPath(path);
            musicItem.setPlayTimes(playTimes);
            musicItem.setPlaying(isPlaying);
            musicItem.setPlaySeek(playSeek);
        }
        cursor.close();
        db.close();

        return musicItem;
    }
}
