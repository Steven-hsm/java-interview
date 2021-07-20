package com.hsm.java.alibaba;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @Classname SortTask
 * @Description 快排
 * @Date 2021/7/20 11:49
 * @Created by huangsm
 */
public class SortTask extends RecursiveAction {
    final long[] array;
    final int lo;
    final int hi;
    private int threshold = 0;

    public SortTask(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    public SortTask(long[] array) {
        this.array = array;
        this.lo = 0;
        this.hi = array.length - 1;
    }

    @Override
    protected void compute() {
        if (lo < hi) {
            int pivot = partition(array, lo, hi);
            SortTask left = new SortTask(array, lo, pivot - 1);
            SortTask right = new SortTask(array, pivot - 1, hi);
            left.fork();
            right.fork();

            left.join();
            right.join();
        }

    }

    private int partition(long[] array, int lo, int hi) {
        long x = array[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j] < x) {
                i++;
                swap(array, i, j);
            }
            swap(array, i + 1, hi);
        }
        return i + 1;
    }

    private void swap(long[] array, int i, int j) {
        if (i != j) {
            long temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long[] array = {1, 2, 3, 5, 7, 2, 6};
        SortTask sortTask = new SortTask(array);
        ForkJoinPool fjPool = new ForkJoinPool();
        fjPool.submit(sortTask);
        fjPool.shutdown();
        fjPool.awaitTermination(20, TimeUnit.SECONDS);
        for (long l : array) {
            System.out.println(l);
        }

    }
}
