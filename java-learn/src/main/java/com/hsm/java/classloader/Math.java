package com.hsm.java.classloader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
 * TODO
 *
 * @author steven
 * @version 1.0
 * @date 2022/1/18 9:56
 */
public class Math {
    public static final int initData = 666;

    //一个方法对应一块栈帧内存区域
    public int compute() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Math math = new Math();
        System.out.println(math.compute());

        /*String s = new String("abc");
        Field value = s.getClass().getDeclaredField("value");
        value.setAccessible(true);
        value.set(s,"abcd".toCharArray());
        System.out.println(s);*/


        List<Integer> intList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                intList.add(10);
            });
        }
        Thread.sleep(1000);
        System.out.println(intList.size());
    }

}
