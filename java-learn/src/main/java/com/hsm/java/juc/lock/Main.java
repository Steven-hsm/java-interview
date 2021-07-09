package com.hsm.java.juc.lock;

/**
 * @Classname Main
 * @Description TODO
 * @Date 2021/7/6 20:01
 * @Created by huangsm
 */
public class Main {

    public static void main(String[] args) {
        Ticket ticket = new Ticket(20);

        new Thread(() -> {
            while (ticket.getNum() > 0){
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
