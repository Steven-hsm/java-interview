package com.hsm.java.juc.ticket;

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
        Ticket ticket = new Ticket(30);

        new Thread(() -> {
            while (ticket.getNum() > 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.sold();
            }
        },"张三").start();

        new Thread(() -> {
            while (ticket.getNum() > 0){
                ticket.sold();
            }
        },"李四").start();

        new Thread(() -> {
            while (ticket.getNum() > 0){
                ticket.sold();
            }
        },"王五").start();
    }
}
