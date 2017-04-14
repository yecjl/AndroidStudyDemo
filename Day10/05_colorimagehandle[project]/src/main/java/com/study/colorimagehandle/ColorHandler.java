package com.study.colorimagehandle;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * 功能：颜色处理的View
 * Created by danke on 2017/4/13.
 */

public class ColorHandler extends LinearLayout {
    private TextView tvLeft;
    private SeekBar seekBar;
    private TextView tvRight;
    private onProgressListener mListener;
    private int mId = -1; // 用于标记SeekBar

    public ColorHandler(Context context) {
        super(context);
        initView(context, null);
    }

    public ColorHandler(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ColorHandler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_color_handler, this);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tvRight = (TextView) findViewById(R.id.tv_right);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mListener != null) {
                    mListener.onProgressChanged((float) (progress * 1.0 / 50), mId);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorHandler);
            for (int i = 0; i < ta.getIndexCount(); i++) {
                int attr = ta.getIndex(i);
                switch (attr) {
                    case R.styleable.ColorHandler_leftText:
                        tvLeft.setText(ta.getString(attr));
                        break;
                    case R.styleable.ColorHandler_progressDrawable:
                        seekBar.setProgressDrawable(ta.getDrawable(attr));
                        break;
                    case R.styleable.ColorHandler_rightText:
                        tvRight.setText(ta.getString(attr));
                        break;
                }
            }
        }
    }

    public onProgressListener getListener() {
        return mListener;
    }

    public void setListener(onProgressListener listener) {
        setListener(mListener, -1);
    }

    public void setListener(onProgressListener listener, int id) {
        this.mListener = listener;
        this.mId = id;
    }

    public interface onProgressListener {
        void onProgressChanged(float progress, int id);
    }
}
