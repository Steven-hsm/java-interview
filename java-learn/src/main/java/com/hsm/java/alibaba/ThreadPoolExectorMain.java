package com.hsm.java.alibaba;

import java.util.concurrent.*;

/**
 * @Classname ThreadPoolExectorMain
 * @Description TODO
 * @Date 2021/7/17 16:17
 * @Created by huangsm
 */
public class ThreadPoolExectorMain {
    public static void main(String[] args) {
        //new ThreadPoolExecutor();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                10,
                10,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10));
        threadPoolExecutor.execute(() ->{

        });
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
