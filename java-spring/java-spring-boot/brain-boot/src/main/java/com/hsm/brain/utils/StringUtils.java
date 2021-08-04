package com.hsm.brain.utils;

/**
 * @Classname StringUtils
 * @Description 字符串工具类
 * @Date 2021/8/4 17:52
 * @Created by huangsm
 */
public class StringUtils {
    /**
     * 字符串不为null且不为空
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        if(value == null || value == ""){
            return false;
        }
        return true;
    }
}
