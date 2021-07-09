package com.hsm.java.juc.threadchat;

/**
 * @Classname Main
 * @Description TODO
 * @Date 2021/7/6 20:01
 * @Created by huangsm
 */
public class Main {

    public static void main(String[] args) {
        //1 创建资源类，在资源类中创建属性和方法
        /////在资源类操作方法
        /**
         * 1. 判断
         * 2. 干活
         * 3. 通知
         */
        //2. 创建多个线程，调用资源类的操作方法
        /////防止虚假唤醒问题 if -> while
        Good good = new Good(0);

        new Thread(() -> {
           for(int i =0;i<10;i++){
               try {
                   good.increment();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        },"加一1").start();

        new Thread(() -> {
            for(int i =0;i<10;i++){
                try {
                    good.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"减一1").start();

        new Thread(() -> {
            for(int i =0;i<10;i++){
                try {
                    good.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"加一2").start();

        new Thread(() -> {
            for(int i =0;i<10;i++){
                try {
                    good.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"减一2").start();
    }
}
