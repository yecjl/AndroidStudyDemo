package com.study.moreadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：
 * Created by danke on 2017/3/2.
 */

public class ArrayAdapterActivity extends Activity {
    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);
        ButterKnife.bind(this);

        String[] objects = new String[]{"danke", "outa", "sister", "mother", "father", "cousin", "niece", "nephew", "aunt", "uncle", "brother", "grandfather", "grandmother"};
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.text_item, objects));
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ArrayAdapterActivity.class);
        context.startActivity(intent);
    }
}
