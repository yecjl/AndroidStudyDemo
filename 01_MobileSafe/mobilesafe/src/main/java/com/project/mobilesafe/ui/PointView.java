package com.project.mobilesafe.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.mobilesafe.R;


/**
 * 功能：点的view
 * Created by danke on 2017/4/20.
 */

public class PointView extends LinearLayout {

    private int position; // 记录当前显示的point的位置
    private final static String TAG = "PointView";
    private OnPointClickListener mListener;

    public PointView(Context context) {
        super(context);
        initView(context, null);
    }

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PointView);
            for (int i = 0; i < ta.getIndexCount(); i++) {
                int attr = ta.getIndex(i);
                switch (attr) {
                    case R.styleable.PointView_count: // Point 的个数:
                        int count = ta.getInt(attr, 0);
                        for (int j = 0; j < count; j++) {
                            ImageView view = (ImageView) inflate(context, R.layout.item_image, null);
                            setChoose(position == j, view);
                            view.setOnClickListener(mOnClickListener);
                            view.setTag(j);
                            addView(view);
                        }
                        break;
                    case R.styleable.PointView_position: // 当前显示的point的位置 从0开始
                        position = ta.getInt(attr, 0);
                        break;

                }
            }
        }
    }

    private OnClickListener mOnClickListener =  new OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            Log.i(TAG, "position: " + position + "被点击");

            if (mListener != null) {
                mListener.onPointClick(position, getChildCount());
            }
        }
    };


    /**
     * 设置position 选中
     * @param isChoose
     * @param imageView
     */
    private void setChoose(boolean isChoose, ImageView imageView) {
        imageView.setImageResource(isChoose ? R.mipmap.btn_radio_on_holo_dark : R.mipmap.btn_radio_on_disabled_holo_dark);
    }

    public OnPointClickListener getListener() {
        return mListener;
    }

    public void setListener(OnPointClickListener listener) {
        this.mListener = listener;
    }

    public interface OnPointClickListener {
        void onPointClick(int position, int count);
    }

}
