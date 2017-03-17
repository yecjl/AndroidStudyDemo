package com.study.gamefight;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.id;
import static android.R.attr.scaleHeight;
import static android.R.attr.scaleWidth;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_kick)
    Button btnKick;
    @Bind(R.id.btn_fist)
    Button btnFist;
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.pb_blood)
    ProgressBar pbBlood;
    @Bind(R.id.iv_boss)
    ImageView ivBoss;
    @Bind(R.id.btn_restart)
    Button btnRestart;
    @Bind(R.id.tv_minusBlood)
    TextView tvMinusBlood;

    private int blood = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        beginGame();
    }

    @OnClick({R.id.btn_kick, R.id.btn_fist, R.id.btn_restart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_kick:
                action(R.mipmap.zj, 10);
                break;
            case R.id.btn_fist:
                action(R.mipmap.zq, 5);
                break;
            case R.id.btn_restart:
                readyGame();
                break;
        }
    }

    /**
     * fight action
     *
     * @param res        action image
     * @param minusBlood action will minus blood number
     */
    public void action(int res, final int minusBlood) {
        ivPlay.setImageResource(res);
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
        animation.setDuration(200);
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        ivPlay.setAnimation(animation);
        animation.start();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 回复初始样子
                ivPlay.setImageResource(R.mipmap.qq);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        blood -= minusBlood;
        pbBlood.setProgress(blood);
        tvMinusBlood.setText("-" + minusBlood);
        bossDied();
    }

    /**
     * boss died state
     */
    public void bossDied() {
        if (blood <= 0) {
            actionBoss(R.mipmap.die, true);
            btnKick.setEnabled(false);
            btnFist.setEnabled(false);
            btnRestart.setVisibility(View.VISIBLE);
            tvMinusBlood.setText("");
        }
    }

    /**
     * ready play game
     */
    public void readyGame() {
        beginGame();
        actionBoss(R.mipmap.boss, false);
        btnKick.setEnabled(true);
        btnFist.setEnabled(true);
    }

    /**
     * begin play game, the init game
     */
    public void beginGame() {
        blood = 100;
        pbBlood.setMax(blood);
        pbBlood.setProgress(blood);
        btnRestart.setVisibility(View.GONE);
    }

    /**
     *  the boss is action
     * @param res
     * @param isDied if died use died image and change half smaller, else use boss,
     */
    private void actionBoss(int res, boolean isDied) {
        ivBoss.setImageResource(res);
        float scale;
        if (isDied) {
            scale = 0.5f;
        } else {
            scale = 1f;
        }
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, scale, 1, scale, Animation.RELATIVE_TO_SELF, scale, Animation.RELATIVE_TO_SELF, scale);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        ivBoss.setAnimation(scaleAnimation);
        scaleAnimation.start();
    }
}
