package com.study.gps_demo;

public class PointDouble {
    double x, y;

    /**
     * @param x 经度
     * @param y 维度
     */
    PointDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "x=" + x + ", y=" + y;
    }
}