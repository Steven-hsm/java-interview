package com.hsm.java.juc;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < 5; i++) {
            new Thread(new Task(semaphore, "yangguo+" + i)).start();
        }

    }

    static class Task extends Thread {
        Semaphore semaphore;

        public Task( Semaphore semaphore,String name) {
            super(name);
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try{
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+":aquire()at time:"+System.currentTimeMillis());
                Thread.sleep(1000);
                semaphore.release();
                System.out.println(Thread.currentThread().getName()+":release()at time:"+System.currentTimeMillis());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

