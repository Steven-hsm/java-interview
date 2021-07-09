package com.hsm.java.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname ConditonTest
 * @Description TODO
 * @Date 2021/7/6 20:38
 * @Created by huangsm
 */
public class ConditonTest {
    public static void main(String[] args) {
        Good good = new Good();
        new Thread(()->{
            for (int i =0;i< 10;i++){
                good.add1();
            }
        },"add11").start();

        new Thread(()->{
            for (int i =0;i< 10;i++){
                good.sub1();
            }
        },"sub11").start();

        new Thread(()->{
            for (int i =0;i< 10;i++){
                good.add1();
            }
        },"add12").start();

        new Thread(()->{
            for (int i =0;i< 10;i++){
                good.sub1();
            }
        },"sub12").start();
    }
}
class Good{
    private int num = 0;
    private Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    public void add1(){
        lock.lock();
        try {
            while (num != 0){
                condition.await();
            }
            num ++ ;
            System.out.println(Thread.currentThread().getName() + "::" + num);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void sub1(){
        lock.lock();
        try {
            while (num != 1){
                condition.await();
            }
            num -- ;
            System.out.println(Thread.currentThread().getName() + "::" + num);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
