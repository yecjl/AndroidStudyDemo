package com.study.myimagenews.service;

import android.util.Log;
import android.util.Xml;

import com.study.myimagenews.domain.NewsItem;
import com.study.myimagenews.util.FileUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by danke on 2017/3/7.
 */

public class NewInfoService {


    /**
     * 获取新闻列表
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<NewsItem> getNewsInfo(String path) throws Exception {
        // 1、从本地读取数据
        String fileName = path.replaceAll("/", "");
        List<NewsItem> newsItemList = (List<NewsItem>) FileUtil.decodeFileObject(fileName);
        if (newsItemList != null) {
            Log.d("NewInfoService", "从本地加载List<NewsItem>数据");
            return newsItemList;
        }
        // 2、从网络读取数据
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == 200) {
            // **** 流只能被读取一次
            InputStream is = conn.getInputStream();
            Log.d("NewInfoService", "从网络加载List<NewsItem>数据");
            newsItemList = parserData(is);
            // 3、保存数据到本地
            FileUtil.saveObject(fileName, newsItemList);
            Log.d("NewInfoService", "保存List<NewsItem>数据到本地");
            is.close();
            return newsItemList;
        } else {
            return null;
        }
    }

    /**
     * 解析数据
     * @param is
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private static List<NewsItem> parserData(InputStream is) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "gb2312");
        int type = parser.getEventType();
        List<NewsItem> newsItemList = new ArrayList<>();
        NewsItem newsItem = null;
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("item".equals(parser.getName())) {
                        newsItem = new NewsItem();
                    } else if ("title".equals(parser.getName())) {
                        if (newsItem != null) {
                            newsItem.setTitle(parser.nextText());
                        }
                    } else if ("link".equals(parser.getName())) {
                        if (newsItem != null) {
                            newsItem.setLink(parser.nextText());
                        }
                    } else if ("author".equals(parser.getName())) {
                        if (newsItem != null) {
                            newsItem.setAuthor(parser.nextText());
                        }
                    } else if ("pubDate".equals(parser.getName())) {
                        if (newsItem != null) {
                            newsItem.setPubDate(parser.nextText());
                        }
                    } else if ("comments".equals(parser.getName())) {
                        if (newsItem != null) {
                            newsItem.setComments(parser.nextText() + 1);
                        }
                    } else if ("description".equals(parser.getName())) {
                        if (newsItem != null) {
                            newsItem.setDescription(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(parser.getName())) {
                        int condition = newsItemList.size() % 5;
                        switch (condition) {
                            case 0:
                                newsItem.setImageUrl("http://img.hb.aicdn.com/d1df0e6039238b5a7bb3521f6c42c101ad0774fc1b843-zpKhij_sq320");
                                break;
                            case 1:
                                newsItem.setImageUrl("http://img.hb.aicdn.com/4fcff73fbc102a1ed6a4623b1f0728f64f99671f3e7d-7EYZEl_sq320");
                                break;
                            case 2:
                                newsItem.setImageUrl("http://img.hb.aicdn.com/78d74f260c0f93205104a5199e197ad4da3cae242acab-AXVtIs_sq320");
                                break;
                            case 3:
                                newsItem.setImageUrl("http://img.hb.aicdn.com/a72709acda4d3e30ea5a61b6ee344c8d5e10e162e3c1-NpXMHM_sq320");
                                break;
                            case 4:
                                newsItem.setImageUrl("http://img.hb.aicdn.com/50bce97a4f6df275be7bf6e4315fdb290df56041e1d6-C2RcgT_sq320");
                                break;
                        }
                        newsItemList.add(newsItem);
                        newsItem = null;
                    }
                    break;
            }
            type = parser.next();
        }
        return newsItemList;
    }
}
