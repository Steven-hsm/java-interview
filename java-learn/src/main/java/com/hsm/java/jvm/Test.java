package com.hsm.java.jvm;

public class Test {
    public static void main(String[] args) {
        /*String s1 = new StringBuffer("go").append("od").toString();
        System.out.println(s1.hashCode());
        System.out.println(s1.intern().hashCode());

        System.out.println(s1.intern() == s1);


        String s2 = new StringBuffer("go").append("od").toString();
        System.out.println(s2.hashCode());
        System.out.println(s2.intern().hashCode());
        System.out.println(s2.intern() == s2);*/
        /*System.out.println(s1.intern() == s1);
        //s1.intern().hashCode()

        String s2 = new StringBuffer("go").append("od").toString();
        System.out.println(s2.intern() == s2);

        String s3 = "good";
        System.out.println(s3.intern() == s3);
        String s4 = "good";;
        System.out.println(s4.intern() == s4);


//        String s5 = new StringBuffer("go").append("od").toString();
//        System.out.println(s5.intern() == s5);
        String s6 = new StringBuffer("ja").append("va").toString();
        System.out.println(s6.intern() == s6);*/
        String s6 = new StringBuffer("ja").append("va").toString();
        if(s6.intern() == s6){
            int i = 10/0;
        }

    }
}
