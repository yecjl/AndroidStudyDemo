package com.study.propertyanim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    public ObjectAnimator translationX() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(ivObject, "translationX", new float[]{-20f, 20f});
        animation.setDuration(1000);
        animation.setRepeatMode(ObjectAnimator.REVERSE);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        return animation;
    }

    public ObjectAnimator rotate() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(ivObject, "rotation", new float[]{0, 360});
        animation.setDuration(2000);
        animation.setRepeatMode(ObjectAnimator.REVERSE);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        return animation;
    }

    public ObjectAnimator scaleX() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(ivObject, "scaleX", new float[]{1f, 0.5f});
        animation.setDuration(2000);
        animation.setRepeatMode(ObjectAnimator.REVERSE);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        return animation;
    }

    public ObjectAnimator scaleY() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(ivObject, "scaleY", new float[]{1f, 0.5f});
        animation.setDuration(2000);
        animation.setRepeatMode(ObjectAnimator.REVERSE);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        return animation;
    }

    public ObjectAnimator alpha() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(ivObject, "alpha", new float[]{1f, 0f});
        animation.setDuration(2000);
        animation.setRepeatMode(ObjectAnimator.REVERSE);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        return animation;
    }

    @OnClick({R.id.btn_translation, R.id.btn_rotate, R.id.btn_scale, R.id.btn_alpha})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translation:
                translationX().start();
                break;
            case R.id.btn_rotate:
                rotate().start();
                break;
            case R.id.btn_scale:
                scaleX().start();
                scaleY().start();
                break;
            case R.id.btn_alpha:
                alpha().start();
                break;
        }
    }

    @OnClick(R.id.btn_playSet)
    public void playSet() {
        AnimatorSet animatorSet = new AnimatorSet();
        if (ckTranslation.isChecked()) {
            animatorSet.playTogether(translationX());
        }
        if (ckRotate.isChecked()) {
            animatorSet.playTogether(rotate());
        }
        if (ckScale.isChecked()) {
            animatorSet.playTogether(scaleX());
            animatorSet.playTogether(scaleY());
        }
        if (ckAlpha.isChecked()) {
            animatorSet.playTogether(alpha());
        }

        animatorSet.start();
    }
}
