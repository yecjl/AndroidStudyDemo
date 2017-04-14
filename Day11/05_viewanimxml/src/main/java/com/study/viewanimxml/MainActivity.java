package com.study.viewanimxml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
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
        return AnimationUtils.loadAnimation(this, R.anim.translation);
    }

    public Animation rotate() {
        return AnimationUtils.loadAnimation(this, R.anim.rotate);
    }

    public Animation scale() {
        return AnimationUtils.loadAnimation(this, R.anim.scale);
    }

    public Animation alpha() {
        return AnimationUtils.loadAnimation(this, R.anim.alpha);
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
