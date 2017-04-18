package com.project.mobilesafe.bean;

import java.io.Serializable;

/**
 * 功能：
 * Created by danke on 2017/4/18.
 */

public class UploadInfo implements Serializable {
    private String appLink;
    private String appVersion;
    private String introduction;

    public String getAppLink() {
        return appLink;
    }

    public void setAppLink(String appLink) {
        this.appLink = appLink;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "appLink='" + appLink + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
