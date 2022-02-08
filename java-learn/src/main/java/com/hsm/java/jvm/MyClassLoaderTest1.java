package com.hsm.java.jvm;

import java.io.FileInputStream;

public class MyClassLoaderTest1 {
    public static void main(String[] args) throws Exception {
        MyClassLoader1 classLoader = new MyClassLoader1("E:\\github\\java-interview\\java-learn\\target\\classes");
        Class clazz = classLoader.loadClass("com.hsm.java.jvm.MyClassLoaderTest1");
        Object obj = clazz.newInstance();
        System.out.println(clazz.getClassLoader().getClass().getName());
    }


    static class MyClassLoader1 extends ClassLoader {
        private String classPath;

        public MyClassLoader1(String classPath) {
            this.classPath = classPath;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节 数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                throw new ClassNotFoundException();
            }

        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }
    }
}