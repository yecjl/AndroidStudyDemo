package com.project.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.project.mobilesafe.R;

/**
 * 功能：开关的自定义view
 * Created by danke on 2017/4/19.
 */

public class SwitchImageView extends ImageView {
    // 开关的状态
    private boolean mStatus = true;

    public SwitchImageView(Context context) {
        super(context);
        invalidateStatus();
    }

    public SwitchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        invalidateStatus();
    }

    public SwitchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        invalidateStatus();
    }

    /**
     * 按下控件，修改开关的状态
     */
    public void pressed() {
        setStatus(!mStatus);
    }

    /**
     * 设置开关的状态
     *
     * @param status
     */
    public void setStatus(boolean status) {
        this.mStatus = status;
        invalidateStatus();
    }

    /**
     * 获取开关的状态
     * @return
     */
    public boolean isStatus() {
        return mStatus;
    }


    /**
     * 更新状态
     */
    private void invalidateStatus() {
        if (mStatus) {
            setImageResource(R.mipmap.btn_on);
        } else {
            setImageResource(R.mipmap.btn_off);
        }
    }
}
