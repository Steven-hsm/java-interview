package com.hsm.java.alibaba;

import java.util.concurrent.locks.StampedLock;

/**
 * @Classname Point
 * @Description TODO
 * @Date 2021/7/17 14:35
 * @Created by huangsm
 */
public class Point {
    private double x, y;
    private final StampedLock s1 = new StampedLock();

    void move(double deltaX, double deltaY) {
        //多个线程调用，修改x,y的值
        long stamp = s1.writeLock();
        try {
            x = deltaX;
            y = deltaY;
        } finally {
            s1.unlock(stamp);
        }
    }

    double distanceFromOrigin() {

        long stamp = s1.tryOptimisticRead();//使用乐观锁
        double currentX = x, currentY = y;
        if (!s1.validate(stamp)) {
            /**
             * 上面这三行关键代码对顺序非常敏感，不能有重排序。 因
             * 为 state 变量已经是volatile，所以可以禁止重排序，但stamp并 不是volatile的。
             * 为此，在validate（stamp）函数里面插入内存屏 障。
             */
            stamp = s1.readLock();//升级悲观锁
            try {
                currentX = x;
                currentY = y;
            } finally {
                s1.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
