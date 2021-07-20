package com.hsm.java.alibaba;

import java.util.concurrent.*;

/**
 * @Classname SimTask
 * @Description 求和任务
 * @Date 2021/7/20 12:06
 * @Created by huangsm
 */
public class SumTask extends RecursiveTask<Long> {
    final long start;
    final long end;
    private final int threshold = 10;

    public SumTask(long n) {
        this(0, n);
    }

    public SumTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if(end -start < threshold){
            for(long l =start ; l <= end ;l++){
                sum += l;
            }
        }else{
            long mid = (start + end) >>> 1;
            SumTask sumTaskLeft = new SumTask(start, mid);
            SumTask sumTaskRight = new SumTask(mid + 1, end);
            sumTaskLeft.fork();
            sumTaskRight.fork();
            sum = sumTaskLeft.join() + sumTaskRight.join();

        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask sumTask = new SumTask(10000);
        ForkJoinPool fjPool = new ForkJoinPool();
        Future<Long> submit = fjPool.submit(sumTask);
        System.out.println(submit.get());
        fjPool.shutdown();
    }
}
