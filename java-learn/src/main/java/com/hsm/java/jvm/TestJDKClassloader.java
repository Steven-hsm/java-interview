package com.hsm.java.jvm;

import sun.misc.Launcher;

import java.net.URL;

public class TestJDKClassloader {

    public static void main(String[] args) {
        System.out.println("String类的加载器:" + String.class.getClassLoader());
        System.out.println("ext目录下的加载器:" + com.sun.crypto.provider.DESKeyFactory.class.getClassLoader());
        System.out.println("自己编写的代码的加载器:" + TestJDKClassloader.class.getClassLoader());

        System.out.println("引导类加载器加载一下文件:");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }

        System.out.println("extClassLoader加载以下文件:");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println("appClassLoader加载以下文件:");
        System.out.println(System.getProperty("java.class.path"));
    }
}
