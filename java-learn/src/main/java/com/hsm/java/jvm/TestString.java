package com.hsm.java.jvm;

public class TestString {
    public static void main(String[] args) {
        /*String a = "hello";
        String b = "hello";

        String c = new String("hello");
        String d = new String();
        d = "hello";
        String e = c;


        System.out.println("a==b ? " + (a== b));
        System.out.println("a==c ? " + (c== b));
        System.out.println("a==d ? " + (a== d));
        System.out.println("a==e ? " + (a== e));

        System.out.println("c==d ? " + (c== d));
        System.out.println("c==e ? " + (c== e));*/

       /* String a = new String("hello");
        System.out.println(a.intern() == a);*/

        /*String s1 = new String("he") + new String("llo");
        String s2 = new String("h") + new String("ello");
        System.out.println(s1 == s1.intern());
        System.out.println(s2 == s2.intern());
        System.out.println(s1.intern() == s2.intern());*/

        String s1 = "abc";
        System.out.println(s1.intern() == s1);

        String s2 = "abc";
        System.out.println(s2.intern() == s2);

    }
}
