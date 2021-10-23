package com.hsm.java.jvm;

import java.io.FileInputStream;

/**
 * 打破双亲委托机制
 */
public class BreakClassloadTest {
    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader= new MyClassLoader("D:/test");
        Class clazz = classLoader.loadClass("com.hsm.thread.entity.User");
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
            byte[] data = new byte[0];
            try {
                data = loadByte(name);
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                throw new ClassNotFoundException();
            }

        }
        @Override
        public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    if (c == null) {
                        // If still not found, then invoke findClass in order
                        // to find the class.
                        long t1 = System.nanoTime();
                        if(! name.startsWith("com.hsm.thread.entity")){
                            c = this.getParent().loadClass(name);
                        }else{
                            c = findClass(name);
                        }
                        // this is the defining class loader; record the stats
                        //sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                        sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                        sun.misc.PerfCounter.getFindClasses().increment();
                    }
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
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
