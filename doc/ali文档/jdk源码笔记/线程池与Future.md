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

### 3.4  任务的提交过程分析

```java
public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();
    int c = ctl.get();
    //如果当前的线程小于corePoolSize，则开新的线程
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
            return;
        c = ctl.get();
    }
    //如果当前的线程大于或等于corePoolSize，则调用workQueue.offer放入到队列中
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
        if (! isRunning(recheck) && remove(command))
            reject(command);
        else if (workerCountOf(recheck) == 0)
            addWorker(null, false);
    }//放入队列失败，开启新的线程
    else if (!addWorker(command, false))
        reject(command);
}
//次函数用于开一个新的线程，如果第二个参数core为true，则用corePoolSize作为上界，如果为false，则用maxPoolSize作为上界
private boolean addWorker(Runnable firstTask, boolean core) {
}
```

### 3.5 任务的执行过程分析

在上面的任务提交过程中，可能会开启一个新的Worker，并把任 务本身作为firstTask赋给该Worker。但对于一个Worker来说，不是只 执行一个任务，而是源源不断地从队列中取任务执行，这是一个不断循环的过程

```java
final void runWorker(Worker w) {
    Thread wt = Thread.currentThread();
    Runnable task = w.firstTask;
    w.firstTask = null;
    w.unlock(); // allow interrupts
    boolean completedAbruptly = true;
    try {
        while (task != null || (task = getTask()) != null) {
            w.lock(); //在执行任务之前先上锁，
            // If pool is stopping, ensure thread is interrupted;
            // if not, ensure thread is not interrupted.  This
            // requires a recheck in second case to deal with
            // shutdownNow race while clearing interrupt
            if ((runStateAtLeast(ctl.get(), STOP) ||
                 (Thread.interrupted() &&
                  runStateAtLeast(ctl.get(), STOP))) &&
                !wt.isInterrupted())
                wt.interrupt();//拿到任务，在执行之前重新检测线程池的状态，如果发现已经关闭，自己给自己发中断信号
            try {
                beforeExecute(wt, task);//钩子函数
                Throwable thrown = null;
                try {
                    task.run();//执行任务代码
                } catch (RuntimeException x) {
                    thrown = x; throw x;
                } catch (Error x) {
                    thrown = x; throw x;
                } catch (Throwable x) {
                    thrown = x; throw new Error(x);
                } finally {
                    afterExecute(task, thrown);//钩子函数
                }
            } finally {
                task = null;
                w.completedTasks++;
                w.unlock();
            }
        }
        completedAbruptly = false;
    } finally {
        processWorkerExit(w, completedAbruptly);
    }
}
```

1. shutdown（）与任务执行过程综合分析

   把任务的执行过程和上面的线程池的关闭过程结合起来进行分析，当调用 shutdown（）的时候，可能出现以下几种场景：

   * 当调用shutdown（）的时候，所有线程都处于空闲状 态。
   * 当调用shutdown（）的时候，所有线程都处于忙碌状 态
   * 场景3：当调用shutdown（）的时候，部分线程忙碌，部分线程空闲。

2. shutdownNow（） 与任务执行过程综合分析

   和上面的 shutdown（）类似，只是多了一个环节，即清空任务队列。

### 3.7 线程池的4种拒绝策略 

RejectedExecutionHandler 是一个接口，定义了四种实现，分别 对应四种不同的拒绝策略，默认是AbortPolicy。

* 让调用者直接在自己的线程里面执行，线程池不做处理 CallerRunsPolicy
* 线程池直接抛出异常 AbortPolicy(默认)
* 线程池直接把任务丢掉，当做什么也没发生 DiscardPolicy
* 把队列中最老的任务删除掉，把该任务放到队列中 DiscardOldestPolicy

```java
public interface RejectedExecutionHandler {
	void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
}
```

## 4. Callable与Future

execute（Runnable command）接口是无返回值的，与之相对应的 是一个有返回值的接口Future submit（Callable task）

Callable也就是一个有返回值的Runnable，其定义如下所示。

```java
@FunctionalInterface
public interface Callable<V> {
    V call() throws Exception;
}
```

submit（Callable task）并不是在 ThreadPoolExecutor 里面直 接实现的，而是实现在其父类AbstractExecutorService中

```java
public Future<?> submit(Runnable task) {
    if (task == null) throw new NullPointerException();
    RunnableFuture<Void> ftask = newTaskFor(task, null);//把callable转换为runable
    execute(ftask);
    return ftask;
}
```

Callable其实是用Runnable实现的。在submit内部，把Callable通过FutureTask这个Adapter转化成Runnable，然后通过execute执行

FutureTask是一个Adapter对象。一方面，它实现了Runnable接 口，也实现了Future接口；另一方面，它的内部包含了一个Callable对象，从而实现了把Callable转换成Runnable。

## 5. ScheduledThreadPoolExecut

ScheduledThreadPoolExecutor实现了按时间调度来执行任务，具体而言有两个方面

* 延迟执行任务
* 周期执行任务

AtFixedRate：按固定频率执行，与任务本身执行时间无关。但有 个前提条件，任务执行时间必须小于间隔时间，例如间隔时间是5s，每5s执行一次任务，任务的执行时间必须小于5s。

WithFixedDelay：按固定间隔执行，与任务本身执行时间有关。 例如，任务本身执行时间是10s，间隔2s，则下一次开始执行的时间就是12s

### 5.1 延迟执行和周期性执行的原理 

ScheduledThreadPoolExecutor继承了ThreadPoolExecutor，这意味着其内部的数据结构和ThreadPooExecutor是基本一样的

周期性执行任务是执行完一个任务之后，再把该任务扔回到任务队列中，如此就可以对一个任务反复执行。

### 5.2 延迟执行 

传进去的是一个Runnable，外加延迟时间delay。在内部通过decorateTask（..）函数把Runnable包装成一个ScheduleFutureTask对 象，而DelayedWorkerQueue中存放的正是这种类型的对象，这种类型的对象一定实现了Delayed接口

schedule（）函数本身很简单，就是 把提交的 Runnable 任务加上delay时间，转换成ScheduledFutureTask对象，放入DelayedWorkerQueue中。任务的执行过程还是复用的ThreadPoolExecutor，延迟的控制是在DelayedWorkerQueue内部完成的

### 5.3 周期性执行

和schedule（..）函数的框架基本一样，也是包装一个ScheduledFutureTask对象，只是在延迟时间参数之外多了一个周期参数，然后放入DelayedWorkerQueue就结束了。

###  6. Executors工具类

concurrent包提供了Executors工具类，利用它可以创建各种不同类型的线程池

在《阿里巴巴Java开发手册》中，明确禁止使用Executors创建线 程池，并要求开发者直接使用ThreadPoolExector或ScheduledThreadPoolExecutor进行创建。这样做是为了强制开发者明确线程池的运行策 略，使其对线程池的每个配置参数皆做到心中有数，以规避因使用不当而造成资源耗尽的风险

* 单线程

  ```java
  public static ExecutorService newSingleThreadExecutor() {
      return new FinalizableDelegatedExecutorService
          (new ThreadPoolExecutor(1, 1,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>()));
  }
  ```

* 固定线程数

  ```java
  public static ExecutorService newFixedThreadPool(int nThreads) {
      return new ThreadPoolExecutor(nThreads, nThreads,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>());
  }
  ```

* 自适应线程

  ```java
  public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
      return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                    60L, TimeUnit.SECONDS,
                                    new SynchronousQueue<Runnable>(),
                                    threadFactory);
  }
  ```

  

