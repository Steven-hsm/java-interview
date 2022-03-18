package com.hsm.java._01classloader;

import org.apache.tools.ant.taskdefs.Classloader;
import sun.misc.Launcher;

import java.net.URL;

public class TestJDKClassloader {

    public static void main(String[] args) {
        System.out.println("String类的加载器:" + String.class.getClassLoader());
        System.out.println("ext目录下的加载器:" + com.sun.crypto.provider.DESKeyFactory.class.getClassLoader());
        System.out.println("自己编写的代码的加载器:" + TestJDKClassloader.class.getClassLoader());

        System.out.println("引导类加载器加载以下文件:");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }

        System.out.print("extClassLoader加载以下文件:");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.print("appClassLoader加载以下文件:");
        System.out.println(System.getProperty("java.class.path"));


    }

    //引导类加载器；负责加载支持JVM运行的位于JRE的lib目录下的核心类库，比如rt.jar,charsets.jar
    //扩展类加载器：负载加载支撑JVM运行位于JRE的lib目录下的ext扩展目录中的JAR包
    //应用程序类加载器：负载加载classPath路径下的类包，主要就是加载用户自己写的类
    //自定义加载器:负责加载用户自定义路径下的类包
}
