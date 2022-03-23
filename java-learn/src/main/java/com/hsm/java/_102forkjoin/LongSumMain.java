package com.hsm.java._102forkjoin;

import com.sun.org.apache.xml.internal.serializer.utils.Utils;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
 * TODO
 *
 * @author steven
 * @version 1.0
 * @date 2022/3/23 10:11
 */
public class LongSumMain {
    static final int NCPU = Runtime.getRuntime().availableProcessors();

    static long calcSum;
    static final boolean reportSteals = true;

    public static void main(String[] args) throws Exception {
        int[] array = buildRandomIntArray(200000000);
        //单线程下计算数组数据总和
        calcSum = seqSum(array);
        System.out.println("普通方式计算结果：" + calcSum);
        //采用fork/join方式将数组求和任务进行拆分执行，最后合并结果
        LongSum ls = new LongSum(array, 0, array.length);
        ForkJoinPool fjp = new ForkJoinPool(10);
        //使用的线程数
        long startTime = System.currentTimeMillis();
        ForkJoinTask<Long> result = fjp.submit(ls);
        System.out.println("forkjoin计算结果：" + result.get());
        System.out.println("forkjoin计算时间：" + (System.currentTimeMillis() -startTime) );
        fjp.shutdown();
    }

    private static int[] buildRandomIntArray(int length) {
        Random random = new Random();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    static long seqSum(int[] array) {
        long startTime = System.currentTimeMillis();
        long sum = 0;
        for (int j : array) sum += j;
        System.out.println("普通方式计算时间：" + (System.currentTimeMillis() -startTime) );
        return sum;
    }
}
