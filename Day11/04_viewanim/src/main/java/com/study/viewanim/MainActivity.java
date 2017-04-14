package com.study.viewanim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_translation)
    Button btnTranslation;
    @Bind(R.id.btn_rotate)
    Button btnRotate;
    @Bind(R.id.btn_scale)
    Button btnScale;
    @Bind(R.id.btn_alpha)
    Button btnAlpha;
    @Bind(R.id.iv_object)
    ImageView ivObject;
    @Bind(R.id.ck_translation)
    CheckBox ckTranslation;
    @Bind(R.id.ck_rotate)
    CheckBox ckRotate;
    @Bind(R.id.ck_scale)
    CheckBox ckScale;
    @Bind(R.id.ck_alpha)
    CheckBox ckAlpha;
    @Bind(R.id.btn_playSet)
    Button btnPlaySet;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public Animation translation() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -0.1f,
                Animation.RELATIVE_TO_SELF, 0.1f,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(50);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }

    public Animation rotate() {
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }

    public Animation scale() {
        ScaleAnimation animation = new ScaleAnimation(1, 0.5f, 1, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }

    public Animation alpha() {
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }

    @OnClick({R.id.btn_translation, R.id.btn_rotate, R.id.btn_scale, R.id.btn_alpha})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translation:
                ivObject.startAnimation(translation());
                break;
            case R.id.btn_rotate:
                ivObject.startAnimation(rotate());
                break;
            case R.id.btn_scale:
                ivObject.startAnimation(scale());
                break;
            case R.id.btn_alpha:
                ivObject.startAnimation(alpha());
                break;
        }
    }

    @OnClick(R.id.btn_playSet)
    public void playSet() {
        AnimationSet animationSet = new AnimationSet(false);
        if (ckTranslation.isChecked()) {
            animationSet.addAnimation(translation());
        }
        if (ckRotate.isChecked()) {
            animationSet.addAnimation(rotate());
        }
        if (ckScale.isChecked()) {
            animationSet.addAnimation(scale());
        }
        if (ckAlpha.isChecked()) {
            animationSet.addAnimation(alpha());
        }
        ivObject.startAnimation(animationSet);
    }
}
