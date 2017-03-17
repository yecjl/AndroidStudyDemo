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

public class MessageActivity extends AppCompatActivity {
    @Bind(R.id.listView)
    ListView listView;
    private String[] messages = {
            "愿春节的欢声笑语和欢乐气氛永远萦绕着你。请记得，有一个朋友一直在祝福你！期待你一切都如意！新年快乐！",
            "愿春节的欢声笑语和欢乐气氛永远萦绕着你。请记得，有一个朋友一直在祝福你！期待你一切都如意！新年快乐！",
            "快乐千金…卿家若无幸福快乐之微笑，则满门抄斩！接旨！",
            "在这个特别的日子里，祝心想事成，每天都有一个好心情。我的朋友，我的老师，祝你节日快乐！",
            "多年以前我就已在我的生命里邀约了你，所以今天的相遇，我很珍惜！就算距离再长，也有我的牵挂！",
            "升职有理，祝贺无罪，所以文送，不能武对，全心全意，不求回馈，你若报答，我不反对，摆上一桌，大家陶醉，共同举杯，掏心掏肺，恭喜高升，舍你其谁！",
            "正月十五月儿圆，正月十五汤圆圆，短信祝福到身边，祝你事业一年胜一年，好运好事喜连连，小日子过得甜又甜，元宵佳节幸福又团圆。",
            "新年好；好事全来了！朋友微微笑；喜气围你绕！欢庆节日里；生活美满又如意！喜气！喜气！一生平安如意！"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        ButterKnife.bind(this);

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item, messages));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = messages[position];
                Intent intent = getIntent();
                intent.putExtra("message", message);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
