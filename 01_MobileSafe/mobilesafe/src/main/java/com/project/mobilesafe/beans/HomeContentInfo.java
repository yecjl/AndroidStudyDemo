package com.project.mobilesafe.beans;

/**
 * 功能：主界面的内容显示条目
 * Created by danke on 2017/4/19.
 */

public class HomeContentInfo {
    private int resId;
    private String title;
    private String desc;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "HomeContentInfo{" +
                "resId=" + resId +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
