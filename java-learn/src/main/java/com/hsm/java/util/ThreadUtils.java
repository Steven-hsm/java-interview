package com.hsm.java.util;

/**
 * @Classname ThreadUtils
 * @Description 线程管理工具
 * @Date 2021/2/27 15:38
 * @Created by senming.huang
 */

public class ThreadUtils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
