package com.hsm.java.reference;

import java.util.WeakHashMap;

/**
 * @Classname MyWeakReference
 * @Description TODO
 * @Date 2021/3/1 17:32
 * @Created by senming.huang
 */
public class MyWeakReference {
    public static void main(String[] args) {
        WeakHashMap weakHashMap = new WeakHashMap();
        Integer a = new Integer(1);
        weakHashMap.put(a,new Object());
        a = null;
        System.gc();

        System.out.println(weakHashMap.get(a));
    }
}
