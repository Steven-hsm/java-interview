package com.hsm.java._102forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * forkjoin 带任务
 * @author steven
 * @version 1.0
 * @date 2022/3/23 10:04
 */
public class LongSum extends RecursiveTask<Long> {
    static final int SEQUENTIAL_THRESHOLD = 1000;

    int low;
    int high;
    int[] array;
    LongSum(int[] arr, int lo, int hi) {
        array = arr;
        low = lo;
        high = hi;
    }

    @Override
    protected Long compute() {
        //任务被拆分到足够小时，则开始求和
        if (high - low <= SEQUENTIAL_THRESHOLD) {
            long sum = 0;
            for (int i = low; i < high; ++i) {
                sum += array[i];
            }
            return sum;
        } else {
            //如果任务任然过大，则继续拆分任务，本质就是递归拆分
            int mid = low + (high - low) / 2;
            LongSum left = new LongSum(array, low, mid);
            LongSum right = new LongSum(array, mid, high);
            left.fork();
            right.fork();
            long rightAns = right.join();
            long leftAns = left.join();
            return leftAns + rightAns;
        }
    }
}
