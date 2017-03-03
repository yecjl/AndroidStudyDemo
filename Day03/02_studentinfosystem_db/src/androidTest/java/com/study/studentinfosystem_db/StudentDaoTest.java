package com.study.studentinfosystem_db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.study.studentinfosystem_db.dao.StudentDao;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 功能：
 * Created by danke on 2017/2/27.
 */
@RunWith(AndroidJUnit4.class)
public class StudentDaoTest {

    @Test
    public void testAdd() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        StudentDao studentDao = new StudentDao(appContext);
        for (int i = 5000; i < 10000; i++) {
            studentDao.add("test" + i, "female");
        }
    }

    @Test
    public void testDelete() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        StudentDao studentDao = new StudentDao(appContext);
        studentDao.delete("danke");
    }

    @Test
    public void testUpdate() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        StudentDao studentDao = new StudentDao(appContext);
        studentDao.update("danke", "male");
    }

    @Test
    public void testFind() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        StudentDao studentDao = new StudentDao(appContext);
        String sex = studentDao.find("danke");
        Log.d("StudentDaoTest", "sex: " + sex);
    }
}
