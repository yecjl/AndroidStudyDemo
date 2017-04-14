package com.study.frameanim;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.iv_anim_list)
    ImageView ivAnimList;
    private AnimationDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        drawable = (AnimationDrawable) ivAnimList.getDrawable();
        drawable.start();
    }

    @Override
    protected void onDestroy() {
        drawable.stop();
        super.onDestroy();
    }
}
