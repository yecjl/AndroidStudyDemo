package com.study.moreadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：
 * Created by danke on 2017/3/2.
 */

public class SimpleAdapterActivity extends Activity {
    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);
        ButterKnife.bind(this);

        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("icon", R.mipmap.ic_menu_allfriends);
        map1.put("name", "通讯录");
        data.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("icon", R.mipmap.ic_menu_archive);
        map2.put("name", "文件夹");
        data.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("icon", R.mipmap.ic_menu_call);
        map3.put("name", "打电话");
        data.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("icon", R.mipmap.ic_menu_camera);
        map4.put("name", "拍照");
        data.add(map4);

        listView.setAdapter(new SimpleAdapter(this, data, R.layout.simple_item, new String[]{"icon", "name"}, new int[]{R.id.iv_icon, R.id.tv_name}));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, SimpleAdapterActivity.class));
    }
}
