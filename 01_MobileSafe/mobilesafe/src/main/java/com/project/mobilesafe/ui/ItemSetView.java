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

public class ItemSetView extends LinearLayout {
    private TextView tvItemContent;
    private SwitchImageView sivItemSet;
    private LinearLayout llItemContent;
    private OnItemClickListener mListener;

    public enum Location {
        TOP, MIDDLE, BOTTOM
    }

    public ItemSetView(Context context) {
        super(context);
        initView(context, null);
    }

    public ItemSetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ItemSetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        inflate(context, R.layout.item_set, this);

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
                    mListener.onClick(ItemSetView.this, sivItemSet.isStatus());
                }
            }
        });

        // 获取xml中的配置信息
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemSetView);
            for (int i = 0; i < ta.getIndexCount(); i++) {
                int attr = ta.getIndex(i);
                switch (attr) {
                    case R.styleable.ItemSetView_location:
                        int location = ta.getInt(attr, 0);
                        if (location == Location.TOP.ordinal()) {
                            llItemContent.setBackgroundResource(R.drawable.selector_gray_border_radius10_top);
                        } else if (location == Location.MIDDLE.ordinal()) {
                            llItemContent.setBackgroundResource(R.drawable.selector_gray_border_middle);
                        } else {
                            llItemContent.setBackgroundResource(R.drawable.selector_gray_border_radius10_bottom);
                        }
                        break;
                    case R.styleable.ItemSetView_itemContent:
                        tvItemContent.setText(ta.getString(attr));
                        break;
                    case R.styleable.ItemSetView_isSelected:
                        break;
                    case R.styleable.ItemSetView_hasSelect:
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
