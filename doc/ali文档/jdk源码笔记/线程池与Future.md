<h2><center>线程池与Future</center></h2>

阿里巴巴2021版JDK源码笔记（2月第三版）.pdf

链接：https://pan.baidu.com/s/1XhVcfbGTpU83snOZVu8AXg 
提取码：l3gy

## 1. 线程池的实现原理

调用方不断地向线程池中提交任 务；线程池中有一组线程，不断地从队列中取任务，这是一个典型的生产者—消费者模型。

实现一个线程池，主要需要考虑一下几个方面：

* 队列设置多长？如果是无界的，调用方不断地往队列中放任 务，可能导致内存耗尽。如果是有界的，当队列满了之后，调用方如何处理？
* 线程池中的线程个数是固定的，还是动态变化的？
* 每次提交新任务，是放入队列？还是开新线程？
* 当没有任务的时候，线程是睡眠一小段时间？还是进入阻塞？如果进入阻塞，如何唤醒？
  * 不使用阻塞队列，只使用一般的线程安全的队列，也 无阻塞—唤醒机制。当队列为空时，线程池中的线程只能睡眠一会儿，然后醒来去看队列中有没有新任务到来，如此不断轮询。
  * 不使用阻塞队列，但在队列外部、线程池内部实现了阻塞—唤醒机制。
  * 使用阻塞队列。

### 2. 线程池的类继承体系

![image-20210717161537983](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210717161537983.png)

* 有两个核心的类：ThreadPoolExector和ScheduledThreadPoolExecutor，后者不仅可以执行某个任务，还可以周期性地执行任务

* 向线程池中提交的每个任务，都必须实现Runnable接口，通过最 上面的Executor接口中的execute（Runnable command）向线程池提交任务
* 在 ExecutorService 中，定义了线程池的关闭接口 shutdown（），还定义了可以有返回值的任务，也就是Callable

### 3. ThreadPoolExector 

### 3.1 核心数据结构 

Worker继承于AQS，也就是说Worker本身就是一把 锁

```java
public class ThreadPoolExecutor extends AbstractExecutorService {
	private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private final BlockingQueue<Runnable> workQueue;
    private final HashSet<Worker> workers = new HashSet<Worker>();
    private final ReentrantLock mainLock = new ReentrantLock();
}

private final class Worker
        extends AbstractQueuedSynchronizer
        implements Runnable
    {
    final Thread thread;
    Runnable firstTask;
    volatile long completedTasks;
}
```

### 3.2 核心配置参数解释

ThreadPoolExecutor在其构造函数中提供了几个核心配置参数，来配置不同策略的线程 池

```java
 public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
}
```

* corePoolSize：在线程池中始终维护的线程个数。
* maxPoolSize：在corePooSize已满、队列也满的情况下，扩充线程至此值。
* keepAliveTime/TimeUnit：maxPoolSize 中的空闲线程，销毁所需要的时间，总线程数收缩回corePoolSize。
* blockingQueue：线程池所用的队列类型。
* threadFactory：线程创建工厂，可以自定义，也有一个默认的
* RejectedExecutionHandler：corePoolSize 已满，队列已满，maxPoolSize 已满，最后的拒绝策略。

### 3.3 线程池的优雅关闭 

而线程池的关闭，较之线程的关闭更加复杂。当关闭一个线程池的时 候，有的线程还正在执行某个任务，有的调用者正在向线程池提交任 务，并且队列中可能还有未执行的任务。因此，关闭过程不可能是瞬 时的，而是需要一个平滑的过渡，这就涉及线程池的完整生命周期管理

**生命周期**

在JDK 7中，把线程数量（workerCount）和线程池状态（runState）这两个变量打包存储在一个字段里面，即ctl变量。最高的3位存储线程池状态，其余29位存储线程个数

线程池的状态有五种，分别是RUNNING、SHUTDOWN、STOP、TIDYING和TERMINATED。

![image-20210717162716044](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210717162716044.png)

线程池有两个关闭函数，shutdown（）和shutdownNow（），这两 个函数会让线程池切换到不同的状态。在队列为空，线程池也为空之 后，进入 TIDYING 状态；最后执行一个钩子函数terminated（），进入TERMINATED状态，线程池才“寿终正寝”。

**正确关闭线程池的步骤 **

线程池并不会立即关 闭，接下来需要调用 awaitTermination 来等待线程池关闭。

**shutdown（）与shutdownNow（）的区别**

* 前者不会清空任务队列，会等所有任务执行完成，后者会直接清空任务队列
* 前者只会中断空闲的线程，后者会中断所有线程。
* 