package com.hsm.java._01classloader;

public class TestDynamicLoad {
    static {
        System.out.println("*******************load TestDynamicLoad********************");
    }

    public static void main(String[] args) {
        new A();
        System.out.println("************************main******************************");
        B b = null;
    }
    //先执行构造方法，再执行静态方法
}

class  A{
    static {
        System.out.println("*******************load A********************");
    }

    public A() {
        System.out.println("*******************initial A********************");
    }
}

class  B{
    static {
        System.out.println("*******************load B********************");
    }

    public B() {
        System.out.println("*******************initial B*******************");
    }
}