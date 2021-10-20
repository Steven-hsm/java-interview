package com.hsm.common;

/**
 * @Classname StringUtils
 * @Description TODO
 * @Date 2021/9/13 10:33
 * @Created by huangsm
 */
public class StringUtils {

    public static boolean isNotEmpty(String value) {
        if (value == null || value == "") {
            return false;
        }
        return true;
    }

    public static boolean isNotBlank(String value) {
        value = value.trim();
        if (value == null || value == "") {
            return false;
        }
        return true;
    }
}
