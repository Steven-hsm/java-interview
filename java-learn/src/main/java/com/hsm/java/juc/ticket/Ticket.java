package com.hsm.java.juc.ticket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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

    public Ticket(int num) {
        this.num = num;
    }

    public synchronized void sold() {
        if (this.num <= 0) {
            log.info("票已经卖完了");
            return;
        }
        log.info("{}卖出了一张票，还剩下{}张票", Thread.currentThread().getName(), --num);
    }
}
