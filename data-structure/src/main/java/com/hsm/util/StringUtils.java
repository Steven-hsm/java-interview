package com.hsm.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    /**
     * 将字符串转换为当个字符串列表
     * @param str
     * @return
     */
    public static List<String> splitWithSingle(String str) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++){
            list.add(String.valueOf(str.charAt(i)));
        }
        return list;
    }

}
