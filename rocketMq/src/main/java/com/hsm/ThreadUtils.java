package com.hsm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Classname ThreadUtils
 * @Description TODO
 * @Date 2021/7/1 14:39
 * @Created by huangsm
 */
@Slf4j
public class ThreadUtils {
    private static ExecutorService executorService = null;

    public static ExecutorService getInstance(){
        if(executorService == null){
            executorService = new ThreadPoolExecutor(
                    2,
                    5,
                    100,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(2000),
                    new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("个人线程池");
                    return thread;
                }
            });
        }
        return executorService;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.info("线程休眠中断" ,e);
        }
    }
}
