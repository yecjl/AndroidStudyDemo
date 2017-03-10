package com.study.weather;

/**
 * 功能：
 * Created by danke on 2017/3/7.
 */

public class WeatherBean {
    private String fengxiang;
    private String fengli;
    private String high;
    private String type;
    private String low;
    private String date;
    private TypeDate typeDate; // 状态：昨天，今天，未来

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TypeDate getTypeDate() {
        return typeDate;
    }

    public void setTypeDate(TypeDate typeDate) {
        this.typeDate = typeDate;
    }

    public enum TypeDate {
        YESTERDAY, TODAY, FUTURE
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "fengxiang='" + fengxiang + '\'' +
                ", fengli='" + fengli + '\'' +
                ", high='" + high + '\'' +
                ", type='" + type + '\'' +
                ", low='" + low + '\'' +
                ", date='" + date + '\'' +
                ", typeDate=" + typeDate +
                '}';
    }
}
