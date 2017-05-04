package com.project.mobilesafe.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mobilesafe.R;

/**
 * 功能：条目 - setting
 * Created by danke on 2017/4/19.
 */

public class SetItemView extends LinearLayout {
    private TextView tvItemContent;
    private SwitchImageView sivItemSet;
    private LinearLayout llItemContent;
    private OnItemClickListener mListener;

    public enum Location {
        TOP, MIDDLE, BOTTOM, ONLY_CIRCLE, ONLY_RECTANGLE
    }

    public SetItemView(Context context) {
        super(context);
        initView(context, null);
    }

    public SetItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SetItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_set, this);

        llItemContent = (LinearLayout) findViewById(R.id.ll_item_content);
        tvItemContent = (TextView) findViewById(R.id.tv_item_content);
        sivItemSet = (SwitchImageView) findViewById(R.id.siv_item_set);

        llItemContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开关：如果显示状态在设置点击开关
                if (sivItemSet.getVisibility() == VISIBLE) {
                    sivItemSet.pressed();
                }
                if (mListener != null) {
                    mListener.onClick(SetItemView.this, sivItemSet.isStatus());
                }
            }
        });

        // 获取xml中的配置信息
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SetItemView);
            for (int i = 0; i < ta.getIndexCount(); i++) {
                int attr = ta.getIndex(i);
                switch (attr) {
                    case R.styleable.SetItemView_showStyle:
                        int location = ta.getInt(attr, 0);
                        if (location == Location.TOP.ordinal()) {
                            llItemContent.setBackgroundResource(R.drawable.selector_gray_border_radius10_top);
                        } else if (location == Location.MIDDLE.ordinal()) {
                            llItemContent.setBackgroundResource(R.drawable.selector_gray_border_middle);
                        } else if (location == Location.BOTTOM.ordinal()) {
                            llItemContent.setBackgroundResource(R.drawable.selector_gray_border_radius10_bottom);
                        } else if (location == Location.ONLY_CIRCLE.ordinal()) {
                            llItemContent.setBackgroundResource(R.drawable.selector_gray_border_only_circle);
                        } else if (location == Location.ONLY_RECTANGLE.ordinal()) {
                            llItemContent.setBackgroundResource(R.drawable.selector_gray_border_only_rectangle);
                        }
                        break;
                    case R.styleable.SetItemView_itemContent:
                        tvItemContent.setText(ta.getString(attr));
                        break;
                    case R.styleable.SetItemView_setStatus:
                        setStatus(ta.getBoolean(attr, false));
                        break;
                    case R.styleable.SetItemView_hasSelect:
                        boolean hasSelect = ta.getBoolean(attr, true);
                        sivItemSet.setVisibility(hasSelect ? VISIBLE : INVISIBLE);
                        if (!hasSelect) {
                            sivItemSet.setStatus(false);
                        }
                        break;
                }
            }
        }
    }

    /**
     * 设置switch状态
     *
     * @param status
     */
    public void setStatus(boolean status) {
        sivItemSet.setStatus(status);
    }

    public OnItemClickListener getListener() {
        return mListener;
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        /**
         * 点击事件的回调
         *
         * @param status 是否被选中
         */
        void onClick(View v, boolean status);
    }
}
