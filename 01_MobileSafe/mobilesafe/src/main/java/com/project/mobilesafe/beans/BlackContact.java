package com.project.mobilesafe.beans;

import java.io.Serializable;

/**
 * 功能：黑名单联系人
 * Created by danke on 2017/4/27.
 */

public class BlackContact implements Serializable {
    private String phone;
    private String mode; // 1、电话拦截 2、短信拦截 3、全部拦截

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "BlackContact{" +
                "phone='" + phone + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
