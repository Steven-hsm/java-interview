package com.hsm.java.alibaba;

import java.util.concurrent.*;

/**
 * @Classname SyncToolsMain
 * @Description TODO
 * @Date 2021/7/17 14:57
 * @Created by huangsm
 */
public class SyncToolsMain {
    public static void main(String[] args) throws Exception {
        //初始化10个资源，第2个参数是公平和非公平选项
        Semaphore available = new Semaphore(10,true);
        available.acquire();//每次获取一个，如果获取不到，线程就会阻塞
        available.release();//用完释放

        //初始化为10，没有公平和非公平的概念
        CountDownLatch countDownLatch = new CountDownLatch(10);
        countDownLatch.await();//主线程调用该方法会阻塞在这里
        countDownLatch.countDown();//减 1，挡减为0时，主线程会被唤醒

        CyclicBarrier cb = new CyclicBarrier(10);
        cb.await();

        Exchanger<String> exchange = new Exchanger<>();

        //初始化10
        Phaser phaser = new Phaser(10);
        phaser.awaitAdvance(phaser.getPhase());//主线程调用该方法是，阻塞在这
        phaser.arrive();//每个线程工作完成之后。调用一次arrive
        phaser.arriveAndAwaitAdvance();

        phaser.register();//注册1个
        phaser.bulkRegister(10);//注册10个
        phaser.arriveAndDeregister();//借注册


    }
}
