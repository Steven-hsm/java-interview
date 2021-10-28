package com.hsm.java.jvm;

import lombok.Data;

public class Test {
    public static void main(String[] args) {
        A a = new A();
        D d = a.b.d; // 1.读
        a.b.d = null; // 2.写
        System.gc();
        a.d = d; // 3.写
        System.out.println(a.d.age);
    }

}
class A{
    B b = new B();
    D d = null;
}

class B{
    C c = new C();
    D d = new D();
}
class C{

}

class D{
    public static int age;
}
