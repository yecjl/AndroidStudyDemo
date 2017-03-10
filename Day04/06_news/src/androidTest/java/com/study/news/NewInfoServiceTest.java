package com.study.news;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.study.news.domain.NewsItem;
import com.study.news.service.NewInfoService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * 功能：
 * Created by danke on 2017/3/7.
 */

@RunWith(AndroidJUnit4.class)
public class NewInfoServiceTest {
    @Test
    public void testGetNewsInfo() throws Exception {
        // Context of the app under test.

        new Thread(new Runnable() {
            @Override
            public void run() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                try {
                    List<NewsItem> newsInfo = NewInfoService.getNewsInfo(appContext.getResources().getString(R.string.path));
                    for (NewsItem newsItem : newsInfo) {
                        Log.d("testGetNewsInfo", newsItem.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
