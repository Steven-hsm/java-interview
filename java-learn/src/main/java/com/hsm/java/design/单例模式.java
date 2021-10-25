package com.hsm.java.design;

/**
 * @Classname 单例模式
 * @Description TODO
 * @Date 2021/9/23 17:24
 * @Created by huangsm
 */
public class 单例模式 {
    /**
     * 一个类智能存在一个对象实例
     *
     * @param args
     */
    public static void main(String[] args) {
        //java代码典型使用,使用的是静态变量
        Runtime runtime = Runtime.getRuntime();
    }
}

/**
 * 饿汉模式（静态变量）
 */
class SingletonStatic {
    //通过静态变量直接初始化
    private final static SingletonStatic instance = new SingletonStatic();

    //私有构造方法
    private SingletonStatic() {
    }

    //所有其他类通过此方法拿到静态变量
    public static SingletonStatic getInstance() {
        return instance;
    }
}

/**
 * 饿汉模式（静态代码块）
 */
class SingletonStaticBlock {
    //创建内部对象
    private static SingletonStaticBlock instance;

    //在静态代码块中创建单例对象
    static {
        instance = new SingletonStaticBlock();
    }

    //私有构造方法
    private SingletonStaticBlock() {
    }

    //所有其他类通过此方法拿到静态变量
    public static SingletonStaticBlock getInstance() {
        return instance;
    }
}

/**
 * 懒汉模式（线程不安全）
 */
class SingletonUnSafe {
    //创建内部对象
    private static SingletonUnSafe instance;

    //私有构造方法
    private SingletonUnSafe() {
    }

    //所有其他类通过此方法拿到静态变量
    public static SingletonUnSafe getInstance() {
        if (instance == null) {
            instance = new SingletonUnSafe();
        }
        return instance;
    }
}

/**
 * 懒汉模式（同步方法）
 */
class SingletonSync {
    //创建内部对象
    private static SingletonSync instance;

    //私有构造方法
    private SingletonSync() {
    }

    //所有其他类通过此方法拿到静态变量
    public static synchronized SingletonSync getInstance() {
        if (instance == null) {
            instance = new SingletonSync();
        }
        return instance;
    }
}

/**
 * 懒汉模式（同步代码块）
 */
class SingletonSyncBlock {
    //创建内部对象
    private static SingletonSyncBlock instance;

    //私有构造方法
    private SingletonSyncBlock() {
    }

    //所有其他类通过此方法拿到静态变量
    public static SingletonSyncBlock getInstance() {
        if (instance == null) {
            synchronized (SingletonSyncBlock.class) {
                instance = new SingletonSyncBlock();
            }
        }
        return instance;
    }
}

/**
 * 双重检查
 */
class SingletonDoubleCheck {
    //创建内部对象
    private static volatile SingletonDoubleCheck instance;

    //私有构造方法
    private SingletonDoubleCheck() {
    }

    //所有其他类通过此方法拿到静态变量
    public static SingletonDoubleCheck getInstance() {
        if (instance == null) {
            synchronized (SingletonSyncBlock.class) {
                if (instance == null) {
                    instance = new SingletonDoubleCheck();
                }
            }
        }
        return instance;
    }
}

/**
 * 静态内部类
 */
class SingletonStaticClass {
    //创建内部对象
    private static volatile SingletonStaticClass instance;

    //私有构造方法
    private SingletonStaticClass() {
    }

    //所有其他类通过此方法拿到静态变量
    public static SingletonStaticClass getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final SingletonStaticClass INSTANCE = new SingletonStaticClass();
    }
}

/**
 * 枚举类
 */
enum SingletonEnum {
    INSTANCE;
}

