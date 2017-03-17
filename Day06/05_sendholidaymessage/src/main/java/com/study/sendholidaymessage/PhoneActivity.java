package com.study.sendholidaymessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：
 * Created by danke on 2017/3/15.
 */
public class PhoneActivity extends AppCompatActivity {
    @Bind(R.id.listView)
    ListView listView;
    private String[] phones = {
            "18857090000",
            "18857090001",
            "18857090002",
            "18857090003",
            "18857090004",
            "18857090005",
            "18857090006",
            "18857090007"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        ButterKnife.bind(this);

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item, phones));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = phones[position];
                Intent intent = getIntent();
                intent.putExtra("phone", phone);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}