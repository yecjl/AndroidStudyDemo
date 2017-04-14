package com.study.viewanimgetheart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.iv_object)
    ImageView ivObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final Random random = new Random();

        ivObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击到了我！", Toast.LENGTH_SHORT).show();
            }
        });

        ivObject.setOnTouchListener(new View.OnTouchListener() {
            int pastX = 0;
            int pastY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int newX = random.nextInt(5);
                int newY = random.nextInt(5);

                TranslateAnimation animation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, pastX,
                        Animation.RELATIVE_TO_SELF, newX,
                        Animation.RELATIVE_TO_SELF, pastY,
                        Animation.RELATIVE_TO_SELF, newY);
                animation.setDuration(100);
                animation.setFillAfter(true);
                ivObject.startAnimation(animation);

                pastX = newX;
                pastY = newY;
                return true;
            }
        });

    }
}
