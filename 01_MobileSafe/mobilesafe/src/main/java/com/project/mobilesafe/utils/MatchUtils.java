package com.project.mobilesafe.utils;

/**
 * 功能：正则表达式的工具类
 * Created by danke on 2017/5/5.
 */

public class MatchUtils {
    /**
     * 匹配是否是一个手机号
     * @param number
     * @return
     */
    public static boolean matchPhone(String number) {
        String regex = "^1[3|4|5|7|8][0-9]{9}$";
        return number.matches(regex);
    }

    /**
     * 匹配是否是一个固定电话
     * @param number
     * @return
     */
    public static boolean matchTelephone(String number) {
        String regex = "^(0\\d{2}\\d{8}(\\d{1,4})?)|(0\\d{3}\\d{7,8}(\\d{1,4})?)$";
        return number.matches(regex);
    }
}
