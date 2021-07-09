package com.hsm.java.juc.privatechat;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname OrderRun
 * @Description TODO
 * @Date 2021/7/6 20:50
 * @Created by huangsm
 */
public class OrderRun {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(()->{
            for(int i=0;i<10;i++){
                shareResource.printA5();
            }
        },"AAA").start();

        new Thread(()->{
            for(int i=0;i<10;i++){
                shareResource.printB10();
            }
        },"BBB").start();


        new Thread(()->{
            for(int i=0;i<10;i++){
                shareResource.printC15();
            }
        },"CCC").start();

    }
}
class ShareResource{
    private int flag = 1;

    private Lock lock = new ReentrantLock();

    private Condition A = lock.newCondition();
    private Condition B = lock.newCondition();
    private Condition C = lock.newCondition();

    public void printA5(){
        lock.lock();
        try {
            while (flag != 1){
                A.await();
            }
            System.out.println(Thread.currentThread().getName() + "---- print AAAAAA");
            flag =2 ;
            B.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printB10(){
        lock.lock();
        try {
            while (flag != 2){
                B.await();
            }
            System.out.println(Thread.currentThread().getName() + "---- print BBBBB");
            flag =3 ;
            C.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC15(){
        lock.lock();
        try {
            while (flag != 3){
                C.await();
            }
            System.out.println(Thread.currentThread().getName() + "---- print CCCCC");
            flag =1 ;
            A.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}