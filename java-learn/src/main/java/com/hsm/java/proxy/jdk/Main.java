package com.hsm.java.proxy.jdk;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        //这里基于1.8，用于保存jdk动态生成的代理类
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        RealObject realObject = new RealObject();
        ObjectInvocationHandler objectInvocationHandler = new ObjectInvocationHandler(realObject);

        ClassLoader loader = realObject.getClass().getClassLoader();
        Class[] interfaces = realObject.getClass().getInterfaces();
        //这里的功能其实很明显，就是为了获取最终的代理类，那么具体的逻辑就可以去看看代理类的执行逻辑了
        InterfaceObject interfaceObject = (InterfaceObject) Proxy.newProxyInstance(loader, interfaces, objectInvocationHandler);
        interfaceObject.sayhello();
    }
}