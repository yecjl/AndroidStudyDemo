package com.study.studentinfosystem_db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.study.studentinfosystem_db.MyDatabaseHelper;
import com.study.studentinfosystem_db.domain.Student;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

/**
 * 功能：
 * Created by danke on 2017/2/27.
 */

public class StudentDao {
    private MyDatabaseHelper helper;

    public StudentDao(Context context) {
        this.helper = new MyDatabaseHelper(context);
    }

    /**
     * 添加学生信息
     *
     * @param name 姓名
     * @param sex  性别：male, female
     * @return 哪一行添加了数据，-1表示添加失败
     */
    public long add(String name, String sex) {
        SQLiteDatabase db = helper.getWritableDatabase();
//        db.execSQL("insert into tb_student(name, sex) values(?,?)", new Object[]{name, sex});

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sex", sex);
        long result = db.insert("tb_student", null, values);
        db.close();
        return result;
    }

    /**
     * 根据姓名删除一条学生信息
     *
     * @param name
     * @return 删除了几行，0表示删除失败
     */
    public int delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
//        db.execSQL("delete from tb_student where name = ?", new Object[]{name});

        int result = db.delete("tb_student", "name = ?", new String[]{name});
        db.close();
        return result;
    }

    /**
     * 根据姓名更改学生信息
     *
     * @param name
     * @param sex
     * @return 更新了几行，0表示更新失败
     */
    public int update(String name, String sex) {
        SQLiteDatabase db = helper.getWritableDatabase();
//        db.execSQL("update tb_student set sex = ? where name = ?", new Object[]{sex, name});

        ContentValues values = new ContentValues();
        values.put("sex", sex);
        int result = db.update("tb_student", values, "name = ?", new String[]{name});
        db.close();
        return result;
    }

    /**
     * 根据姓名查询学生性别
     *
     * @param name
     * @return
     */
    public String find(String name) {
        String sex = null;
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select sex from tb_student where name = ?", new String[]{name});
        Cursor cursor = db.query("tb_student", new String[]{"sex"}, "name = ?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {
            sex = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return sex;
    }

    /**
     * 查找所有的学生信息
     *
     * @return
     */
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select name,sex from tb_student", null);
        Cursor cursor = db.query("tb_student", new String[]{"name", "sex"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String sex = cursor.getString(1);
            Student student = new Student();
            student.setName(name);
            student.setSex(sex);
            students.add(student);
        }
        cursor.close();
        db.close();
        return students;
    }
}
