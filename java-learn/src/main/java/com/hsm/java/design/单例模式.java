package com.hsm.java.design;

/**
 * @Classname 单例模式
 * @Description TODO
 * @Date 2021/9/23 17:24
 * @Created by huangsm
 */
public class 单例模式 {
    /**
     * 指一个类只有一个实例，且该类能自行创建这个实例的一种模式
     * 特点：
     * 1. 单例类只有一个实例对象；
     * 2. 该单例对象必须由单例类自行创建；
     * 3. 单例类对外提供一个访问该单例的全局访问点。
     * @param args
     */
    public static void main(String[] args) {
        LazySingleton instance = LazySingleton.getInstance();
        HungrySingleton instance1 = HungrySingleton.getInstance();
    }
}

/**
 * 懒汉模式
 */
class LazySingleton{
    private static volatile LazySingleton instance = null;    //保证 instance 在所有线程中同步
    private LazySingleton() {
    }
    //private 避免类在外部被实例化
    public static synchronized LazySingleton getInstance() {
        //getInstance 方法前加同步
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
/**
 * 饿汉模式
 */
class HungrySingleton {
    private static final HungrySingleton instance = new HungrySingleton();
    private HungrySingleton() {
    }
    public static HungrySingleton getInstance() {
        return instance;
    }
}


