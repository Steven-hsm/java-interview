package com.hsm.java._03jvm;

import org.openjdk.jol.info.ClassLayout;
/**
 * 对象创建的过程：
 * 1. 类加载检查
 * 2. 分配内存
 *  2.1划分内存的方法
 *      指针碰撞
 *      空闲列表
 *  2.2解决并发问题的方法
 *      CAS
 *      本地线程分配缓存
 * 3.初始化：初始化为零值
 * 4.设置对象头
 * 5.执行java的init方法
 */
public class JOLSample {
    public static void main(String[] args) {
        ClassLayout layout = ClassLayout.parseInstance(new Object());
        System.out.println(layout.toPrintable());

        System.out.println();
        ClassLayout layout1 = ClassLayout.parseInstance(new int[]{});
        System.out.println(layout1.toPrintable());

        System.out.println();
        ClassLayout layout2 = ClassLayout.parseInstance(new A());
        System.out.println(layout2.toPrintable());

    }

    public static class A {
        //8B mark word 26
        //4B Klass Pointer 如果关闭压缩‐XX:‐UseCompressedClassPointers或‐XX:‐UseCompressedOops，则占用8B
        int id; //4B 28
        String name; //4B 如果关闭压缩‐XX:‐UseCompressedOops，则占用8B
        byte b; //1B 30
        Object o; //4B 如果关闭压缩‐XX:‐UseCompressedOops，则占用8B 31

    }
    /**
     * 指针压缩。 默认开启指针压缩，内存小于4G不需要开启指针压缩，超过32G，压缩指针会失效，强制使用64位来寻址
     * 计算机一般以 8bit为最小寻址单元，即1byte，32位就可以寻址 2^32 * 8 = 4G
     * 64位机寻址为 2^64 * 8，这里可以看到，寻址所占空间非常大，估计是32位寻址空间的1.5倍，为了减少空间地址的使用，就采用了指针压缩方案
     * 什么是指针压缩呢： java仿照计算机内存的方式，将8 byte作为基本的内存单元，也就是在原基础上扩充了8 倍即 8 * 4G = 32G
     */
}
