package com.study.readdb;

import java.io.Serializable;

/**
 * 功能：
 * Created by danke on 2017/4/17.
 */

public class SmsInfo implements Serializable {
    private String address;
    private String date;
    private String body;
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SmsInfo{" +
                "address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
