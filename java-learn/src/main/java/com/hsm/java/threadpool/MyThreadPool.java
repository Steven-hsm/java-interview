package com.hsm.java.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname MyThreadPool
 * @Description 线程池
 * @Date 2021/2/27 15:58
 * @Created by senming.huang
 */
public class MyThreadPool {

    public static void main(String[] args) {
        ExecutorService pool1 = Executors.newFixedThreadPool(5);
        ExecutorService pool2 = Executors.newSingleThreadExecutor();
        ExecutorService pool = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 10; i++) {
                pool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + "正在处理中");
                });
            }
        } catch (Exception e) {

        } finally {

        }
    }

    /**
     * 功能描述: 打印电脑信息
     *
     * @auther: senming.huang
     * @date: 2021/2/27 16:21
     */
    public void printInfo() {
        System.out.println("CPU核数:" + Runtime.getRuntime().availableProcessors());
        System.out.println("当前线程数:" + Thread.activeCount());
    }
}
