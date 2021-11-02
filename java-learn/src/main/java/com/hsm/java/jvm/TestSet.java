package com.hsm.java.jvm;

import java.util.HashSet;
import java.util.Set;

public class TestSet {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        System.out.println(set.add("12312"));
        System.out.println(set.add("12312"));
        System.out.println(set.add("12312"));
    }
}
