package com.hsm.java.juc.atomic;

import sun.misc.Unsafe;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    static AtomicInteger total = new AtomicInteger();
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(101);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++){
                    total.getAndIncrement();
                    returnStr();
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println(total.get());
    }

    public static String returnStr(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt(1000) + "这是一个字符串";
    }

}
