package com.hsm.java.alibaba;

import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Classname AtomicMain
 * @Description TODO
 * @Date 2021/7/17 9:48
 * @Created by huangsm
 */
public class AtomicMain {
    private volatile int a;
    public static void main(String[] args) {
   /*     AtomicInteger atomicInteger = new AtomicInteger(10);
        atomicInteger.addAndGet(10);
        AtomicStampedReference<Integer> intNum = new AtomicStampedReference(10,0);
        //intNum.compareAndSet()
        System.out.println(atomicInteger.get());*/

        AtomicMain atomicMain = new AtomicMain();
        atomicMain.a = 10;
        AtomicIntegerFieldUpdater<AtomicMain> a = AtomicIntegerFieldUpdater.newUpdater(AtomicMain.class, "a");
        a.compareAndSet(atomicMain,10,11);
        System.out.println(a.get(atomicMain));

        ReadWriteLock rwlock = new ReentrantReadWriteLock();
        Lock rlock = rwlock.readLock();
        rlock.lock();
        rlock.unlock();
        Lock wlock = rwlock.writeLock();
        wlock.lock();
        wlock.unlock();

    }
}
