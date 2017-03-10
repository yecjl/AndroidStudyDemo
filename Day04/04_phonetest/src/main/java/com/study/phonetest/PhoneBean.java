package com.study.phonetest;

/**
 * 功能：
 * Created by danke on 2017/3/7.
 */

public class PhoneBean {
    private String phone;
    private String area;
    private String postno;
    private String ctype;
    private String style_citynm;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPostno() {
        return postno;
    }

    public void setPostno(String postno) {
        this.postno = postno;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getStyle_citynm() {
        return style_citynm;
    }

    public void setStyle_citynm(String style_citynm) {
        this.style_citynm = style_citynm;
    }

    @Override
    public String toString() {
        return "PhoneBean{" +
                "phone='" + phone + '\'' +
                ", area='" + area + '\'' +
                ", postno='" + postno + '\'' +
                ", ctype='" + ctype + '\'' +
                ", style_citynm='" + style_citynm + '\'' +
                '}';
    }
}
