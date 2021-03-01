package com.hsm.java.reference;

import com.hsm.java.util.ThreadUtils;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @Classname MyPhantomReference
 * @Description 虚引用
 * @Date 2021/3/1 17:38
 * @Created by senming.huang
 */
public class MyPhantomReference {
    public static void main(String[] args) {
        Object o1 = new Object();

        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o1,referenceQueue);

        System.out.println(o1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("==============================");
        o1 = null;
        System.gc();
        ThreadUtils.sleep(1000);

        System.out.println(o1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

    }
}
