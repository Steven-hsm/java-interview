package com.hsm.brain.utils;

/**
 * @Classname SqlUtils
 * @Description sql工具类处理
 * @Date 2021/7/12 15:17
 * @Created by huangsm
 */
public class SqlUtils {
    /**
     * 左右都加上%,%name%
     *
     * @param name
     * @return
     */
    public static String contactLike(String name) {
        if (name == null) {
            name = "";
        }
        return "%" + name + "%";
    }
}
