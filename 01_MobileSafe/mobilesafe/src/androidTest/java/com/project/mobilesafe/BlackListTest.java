package com.project.mobilesafe;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.project.mobilesafe.beans.BlackContact;
import com.project.mobilesafe.db.dao.BlackListDao;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BlackListTest {
    private static final String TAG = "BlackListTest";

    @Test
    public void testInsert() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        BlackListDao blackListDao = new BlackListDao(appContext);
        for (int i = 1000; i < 10000; i++) {
            BlackContact blackContact = new BlackContact();
            blackContact.setPhone("159" + i);
            blackContact.setMode("2");
            boolean result = blackListDao.insert(blackContact);
            Log.i(TAG, "insert: " + result);
        }
    }

    @Test
    public void testDelete() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        BlackListDao blackListDao = new BlackListDao(appContext);
        boolean result = blackListDao.delete("159");
        Log.i(TAG, "delete: " + result);
    }

    @Test
    public void testUpdate() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        BlackListDao blackListDao = new BlackListDao(appContext);
        BlackContact blackContact = new BlackContact();
        blackContact.setPhone("159");
        blackContact.setMode("2");
        boolean result = blackListDao.updateMode(blackContact);
        Log.i(TAG, "update: " + result);
    }

    @Test
    public void testFindAll() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        BlackListDao blackListDao = new BlackListDao(appContext);
        List<BlackContact> list = blackListDao.findAll();
        for (int i = 0; i < list.size(); i++) {
            BlackContact blackContact = list.get(i);
            Log.i(TAG, "findAll: " + blackContact);
        }
    }

    @Test
    public void testFind() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        BlackListDao blackListDao = new BlackListDao(appContext);
        BlackContact blackContact = blackListDao.findBlackContact("159");
        Log.i(TAG, "find: " + blackContact);
    }
}
