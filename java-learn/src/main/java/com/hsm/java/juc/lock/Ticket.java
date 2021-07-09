package com.hsm.java.juc.lock;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname Ticket
 * @Description 票
 * @Date 2021/7/6 20:00
 * @Created by huangsm
 */
@Data
@Slf4j
public class Ticket {
    /**
     * 票的数量
     */
    private int num;

    //创建可重入锁
    private final ReentrantLock lock = new ReentrantLock();

    public Ticket(int num) {
        this.num = num;
    }

    /**
     * 买票
     */
    public void sold() {
        lock.lock();
        try{
            if (this.num <= 0) {
                log.info("票已经卖完了");
                return;
            }
            log.info("{}卖出了一张票，还剩下{}张票", Thread.currentThread().getName(), --num);
        }finally {
            //需要手动释放锁
            lock.unlock();
        }
    }
}
