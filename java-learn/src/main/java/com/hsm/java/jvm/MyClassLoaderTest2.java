package com.hsm.java.jvm;

import java.io.FileInputStream;

public class MyClassLoaderTest2 {
    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("E:\\github\\java-interview\\java-learn\\target\\classes");
        Class clazz = classLoader.loadClass("com.hsm.java.jvm.MyClassLoaderTest2");
        Object obj = clazz.newInstance();
        System.out.println(clazz.getClassLoader().getClass().getName());
    }


    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
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

        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    long t1 = System.nanoTime();

                    if(!name.startsWith("com.hsm")){
                        //非自定义的类还是走双亲委派加载
                        c = this.getParent().loadClass(name);
                    }else{
                        c = findClass(name);
                    }
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();

                } 
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }
    }
}
