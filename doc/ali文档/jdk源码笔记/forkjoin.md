<h2><center>forkJoin</center></h2>

阿里巴巴2021版JDK源码笔记（2月第三版）.pdf

链接：https://pan.baidu.com/s/1XhVcfbGTpU83snOZVu8AXg 
提取码：l3gy

在大学的算法课本中，都有一种基本算法：分治。其基本思路 是：将一个大的任务分为若干个子任务，这些子任务分别计算，然后合并出最终结果，在这个过程中通常会用到递归。而ForkJoinPool就是JDK7提供的一种“分治算法”的多线程并行 计算框架。Fork意为分叉，Join意为合并，一分一合，相互配合，形 成分治算法。此外，也可以将ForkJoinPool看作一个单机版的Map/Reduce，只不过这里的并行不是多台机器并行计算，而是多个线程并行计算。

相比于ThreadPoolExecutor，ForkJoinPool可以更好地实现计算 的负载均衡，提高资源利用率。假设有5个任务，在ThreadPoolExecutor中有5个线程并行执行，其中一个任务的计算量很大，其余4个任务 的计算量很小，这会导致1个线程很忙，其他4个线程则处于空闲状 态。而利用ForkJoinPool，可以把大的任务拆分成很多小任务，然后这些小任务被所有的线程执行，从而实现任务计算的负载均衡。

 ###  7.1  ForkJoinPool用法 

主要继承RecursiveAction 和 RecursiveTask 两个 类，它们都继承自抽象类ForkJoinTask，用到了其中关键的接口 fork（）、join（）。二者的区别是一个有返回值，一个没有返回值。

```java
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
```

### 7.2 核心数据结构

