package com.hsm.java._02jvm;

public class StackOverflowTest {

    static int count = 0;

    static void redo(){
        count ++;
        redo();
    }
}
