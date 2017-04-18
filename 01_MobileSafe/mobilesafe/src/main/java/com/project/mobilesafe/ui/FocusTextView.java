package com.project.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 功能：获取到焦点的TextView，可以实现marquee
 * Created by danke on 2017/4/18.
 */

public class FocusTextView extends TextView {
    public FocusTextView(Context context) {
        super(context);
    }

    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 欺骗框架，默认直接是获取焦点的
     * @return
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}
