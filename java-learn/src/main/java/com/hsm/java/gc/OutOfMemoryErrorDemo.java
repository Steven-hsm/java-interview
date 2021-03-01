package com.hsm.java.gc;

import java.util.Random;

public class OutOfMemoryErrorDemo {
    public static void main(String[] args) {
        String str = "hsm";
        while (true) {
            str += new Random().nextInt(111111);
            str.intern();
        }
    }
}
