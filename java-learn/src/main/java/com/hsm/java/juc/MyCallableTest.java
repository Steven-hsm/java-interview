package com.hsm.java.juc;

import com.hsm.java.util.ThreadUtils;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Classname JucTest
 * @Description JUC 包测试
 * @Date 2021/2/27 14:44
 * @Created by senming.huang
 */
public class MyCallableTest {

    public static void main(String[] args) throws Exception{
        Thread myRun = new Thread(new MyRun());
        myRun.start();

        FutureTask<String> myCallableFutureTask = new FutureTask(new MyCallable());
        Thread myCallable = new Thread(myCallableFutureTask);
        myCallable.start();

        //callable获取结果会阻塞,可以设置时间
        long startTime = System.currentTimeMillis();
        String result = myCallableFutureTask.get();
        System.out.println((System.currentTimeMillis()- startTime) + "毫秒Callable执行结果:" + result);
    }

}

class MyRun implements Runnable{
    @Override
    public void run() {
        System.out.println("Runnable正在执行");
    }
}

/**
 * 区别
 * 1.Runnable 没有参数返回
 *   callable 有参数返回
 * 2.Runnable 不抛异常
 *   callable 抛异常
 * 3.Thread方法没有callable接口得构造方法
 */
class MyCallable implements Callable<String>{
    @Override
    public String call() throws Exception {
        System.out.println("Callable正在执行");
        ThreadUtils.sleep(2000);
        return "true";
    }
}