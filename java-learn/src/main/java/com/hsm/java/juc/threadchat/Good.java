package com.hsm.java.juc.threadchat;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Classname Ticket
 * @Description 物品
 * @Date 2021/7/6 20:00
 * @Created by huangsm
 */
@Data
@Slf4j
public class Good {
    /**
     * 物品数量
     */
    private int num = 0 ;

    public Good(int num) {
        this.num = num;
    }

    //+1
    public synchronized void increment() throws InterruptedException {
        while (num != 0){
            this.wait();
        }
        num ++;
        System.out.println(Thread.currentThread().getName() + "::" + num);
        this.notifyAll();
    }

    //-1
    public synchronized void decr() throws InterruptedException {
        while(num != 1){
            this.wait();
        }
        num --;
        System.out.println(Thread.currentThread().getName() + "::" + num);
        this.notifyAll();
    }
}
