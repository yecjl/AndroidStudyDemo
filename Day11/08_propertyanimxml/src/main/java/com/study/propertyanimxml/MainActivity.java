package com.study.propertyanimxml;

import android.animation.Animator;
import android.animation.AnimatorInflater;
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
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.translationx);
        animator.setTarget(ivObject);
        return animator;
    }

    public ObjectAnimator rotate() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotation);
        animator.setTarget(ivObject);
        return animator;
    }

    public ObjectAnimator scaleX() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.scalex);
        animator.setTarget(ivObject);
        return animator;
    }

    public ObjectAnimator scaleY() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.scaley);
        animator.setTarget(ivObject);
        return animator;
    }

    public ObjectAnimator alpha() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.alpha);
        animator.setTarget(ivObject);
        return animator;
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
