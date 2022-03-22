package com.hsm.java._101scheduled;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyScheduledThreadPoolTest {

    public static void main(String[] args) {

        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(10);
        schedule.scheduleAtFixedRate(() ->{
            System.out.println(System.currentTimeMillis());
        },1000,1000, TimeUnit.MILLISECONDS);
    }
}
