package com.hsm.string;

public class StringUtils {
    public static void main(String[] args) {
        String str = " abc abc ";
        System.out.println(str.trim());
        System.out.println(str.replaceAll("^[ ]+",""));
        System.out.println(str.replaceAll("[ ]+$",""));
        System.out.println(str.replaceAll(" ",""));
    }
}
