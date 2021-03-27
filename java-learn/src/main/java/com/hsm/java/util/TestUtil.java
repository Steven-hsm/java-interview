package com.hsm.java.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/27
 */
public class TestUtil {
    private static final char[] baseChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String getEncode() {
        int length = (int) Math.round(Math.random() * 2.0D + 10.0D);
        char[] tem = new char[length];
        for (int i = 0; i < length; i++) {
            tem[i] = baseChar[(int) Math.round(Math.random() * 35.0D)];
        }
        return String.valueOf(tem);
    }

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        int max = 0;
        for (int i = 0; i < 5000000; i++) {
            String encode = getEncode();
            max = Math.max(max,encode.length());
            set.add(encode);
        }
        System.out.println(set.size());
        System.out.println(max);
    }

}
