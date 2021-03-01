package com.hsm.java.gc;

public class StackOverFlowErrorDemo {
    public static void main(String[] args) {
        stackOverFlowError();
    }

    private static void stackOverFlowError() {
        stackOverFlowError();//java.lang.StackOverflowError
    }
}
