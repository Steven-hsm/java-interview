package com.hsm.java.jvm;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import org.openjdk.jol.info.ClassLayout;

public class JOLSample {
    public static void main(String[] args) {
       ClassLayout layout = ClassLayout.parseInstance(new Object());
        System.out.println(layout.toPrintable());

        try {
            Class.forName("java.lang.String");
            Class<?> clazz = JOLSample.class.getClassLoader().loadClass("java.lang.String");
            Object o = clazz.newInstance();
            System.out.println(o.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
