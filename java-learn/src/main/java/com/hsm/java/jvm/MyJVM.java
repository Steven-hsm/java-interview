package com.hsm.java.jvm;

/**
 * @Classname MyJVm
 * @Description TODO
 * @Date 2021/3/1 13:45
 * @Created by senming.huang
 */
public class MyJVM {

    public static void main(String[] args) {
        printInfo();
    }

    public static void printInfo() {
        System.out.println("jvm中的内存总量:" + Runtime.getRuntime().totalMemory()/1024.0/1024.0 + "M");
        System.out.println("jvm虚拟机可以使用的最大内存:" + Runtime.getRuntime().maxMemory()/1024.0/1024.0   + "M");
    }
}
