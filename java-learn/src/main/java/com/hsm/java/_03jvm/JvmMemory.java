package com.hsm.java._03jvm;

import com.hsm.java.threadlocal.User;

/**
 * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
 *
 * @author steven
 * @version 1.0
 * @date 2022/3/22 15:02
 */
public class JvmMemory {

    public static void main(String[] args) {
        /**
         * 减少临时对象在堆上的分配，减少垃圾回收的频率，优先考虑在栈上分配
         * JVM通过逃逸分析确定该对象不会被外部访问。如果不会逃逸可以将该对象在栈上分配内存，这样该对象所占用的 内存空间就可以随栈帧出栈而销毁，就减轻了垃圾回收的压力
         * 标量替换：在栈上分配时，可能没有连续的内存空间。对象成员变量分解若干个被这个方法使用的成员变量所代替
         */
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    private static void alloc() {
        User user = new User("hsm");
        System.out.println(user);
    }
}
