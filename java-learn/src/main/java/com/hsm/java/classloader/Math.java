package com.hsm.java.classloader;

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

    public int compute() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        Math math = new Math();
        math.compute();
    }
}
