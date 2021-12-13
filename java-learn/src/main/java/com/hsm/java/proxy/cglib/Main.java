package com.hsm.java.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class Main {
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"E:\\github\\java-interview\\java-learn");
        //目标对象
        Enhancer en = new Enhancer();
        en.setSuperclass(UserDao.class);
        en.setCallback(new ProxyMethodInterceptor());
        UserDao userDao = (UserDao) en.create();
        userDao.save();
    }
}
