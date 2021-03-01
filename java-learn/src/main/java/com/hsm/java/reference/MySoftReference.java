package com.hsm.java.reference;

import java.lang.ref.SoftReference;

/**
 * @Classname MySoftReference
 * @Description TODO
 * @Date 2021/3/1 17:09
 * @Created by senming.huang
 */
public class MySoftReference {
    public static void main(String[] args) {
        Object o1 = new Object();
        SoftReference<Object> objectSoftReference = new SoftReference<Object>(o1);
    }


}
