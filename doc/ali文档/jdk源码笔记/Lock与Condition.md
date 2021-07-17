<h2><center>Lock与Condition</center></h2>

阿里巴巴2021版JDK源码笔记（2月第三版）.pdf

链接：https://pan.baidu.com/s/1XhVcfbGTpU83snOZVu8AXg 
提取码：l3gy

## 1. 互斥锁

### 1.1 锁的可重入性

当一个线程调用 object.lock（）拿到锁，进入互斥区后，再次调用object.lock（）， 仍然可以拿到该锁（否则会死锁)

### 1.2 类的继承关系

![image-20210717105011208](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210717105011208.png)

lock.java

```java
public interface Lock {
	void lock();
    void lockInterruptibly() throws InterruptedException;
    boolean tryLock();
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
    void unlock();
    Condition newCondition();
}
```

### 1.3 锁的公平性与非公平性

Sync是一个抽象类，它有两个子类FairSync与NonfairSync，分别 对应公平锁和非公平锁

* 公平锁：遵循先到者优先服务，先抢资源的先获取CPU
* 非公平锁：线程来了直接抢锁，获取CPU资源不是按照顺序获取（提高效率，减少线程切换）

### 1.4 锁实现的基本原理

Sync的父类AbstractQueuedSynchronizer经常被称作队列同步器 （AQS），这个类非常关键

AbstractOwnableSynchronizer具有阻塞线程的作用，为了实现一把具有阻塞和唤醒功能的锁，需要一下核心要素：

* 1. 需要一个state变量，标记该锁的状态，state变量至少有两个值：0,1 对state变量的操作，要确保线程安全，也就是会用到CAS
* 2. 需要记录当前是哪个线程持有锁
* 3. 需要底层支持对一个线程进行阻塞或唤醒操作
* 4. 需要有一个队列维护所有阻塞的线程。这个队列也必须是线程安全的无锁队列，也需要用到CAS

针对1,2

* state取值不仅可以是0、1，还可以大于1，就是为了支持锁的可 重入性。例如，同样一个线程，调用5次lock，state会变成5；然后调用5次unlock，state减为0。
* 当state=0时，没有线程持有锁，exclusiveOwnerThread=null；
* 当state=1时，有一个线程持有锁，exclusiveOwnerThread=该线程；
* 当state > 1时，说明该线程重入了该锁。

针对3 

* 在Unsafe类中，提供了阻塞或唤醒线程的一对操作原语，也就是park/unpark

* LockSupport对其做了简单的封装

  ```java
  public class LockSupport {
  	public static void park(Object blocker) {
          Thread t = Thread.currentThread();
          setBlocker(t, blocker);
          UNSAFE.park(false, 0L);
          setBlocker(t, null);
      }
      public static void unpark(Thread thread) {
          if (thread != null)
              UNSAFE.unpark(thread);
      }
  }
  ```

* 在当前线程中调用park（），该线程就会被阻塞；在另外一个线 程中，调用unpark（Thread t），传入一个被阻塞的线程，就可以唤醒阻塞在park（）地方的线程

* 尤其是 unpark（Thread t），它实现了一个线程对另外一个线程 的“精准唤醒”

针对4

* 在AQS中利用双向链表和CAS实现了一个阻塞队列。

  ```java
  public abstract class AbstractQueuedSynchronizer
      extends AbstractOwnableSynchronizer
      implements java.io.Serializable {
      static final class Node {
          volatile Node prev;
          volatile Node next;
          volatile Thread thread;
      }
      private transient volatile Node head;
      private transient volatile Node tail;
  }
  ```

  ![image-20210717110954416](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210717110954416.png)

### 1.5 公平与非公平的lock（）的实现差异

FairSync 公平锁

```java
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {//等于0,资源空闲，可以拿到锁
        if (!hasQueuedPredecessors() && //判断是否存在等待队列或者当前线程是否是队头
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {//被锁了，但是当前线程就是已经获取锁了（重入锁），state+1 
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```
NonfairSync 非公平锁

```java
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```

公平锁和非公平锁的区别：

公平锁就多了这块代码 ```!hasQueuedPredecessors()```,看源码

```java
public final boolean hasQueuedPredecessors() {
    // The correctness of this depends on head being initialized
    // before tail and on head.next being accurate if the current
    // thread is first in queue.
    Node t = tail; // Read fields in reverse initialization order
    Node h = head;
    Node s;
    return h != t &&
        ((s = h.next) == null || s.thread != Thread.currentThread());
}
```

这里其实就是判断当前线程是否可以被公平的执行（队列为空，或者当前在队头的时候表示到当前线程处理了）

### 1.6  阻塞队列与唤醒机制

AQS类中，有尝试拿锁的方法

```java
public final void acquire(int arg) {
    if (!tryAcquire(arg) && //这里尝试去拿锁，没有拿到锁才执行下一个条件
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) 
        //将当前线程入队进入等待
        //addWaiter就是将线程加入到队列中，
        //acquireQueued该线程被阻塞。在该函数返回 的一刻，就是拿到锁的那一刻，也就是被唤醒的那一刻，此时会删除队列的第一个元素（head指针前移1个节点）
        selfInterrupt();
}

private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    if (pred != null) {
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    enq(node);
    return node;
}
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {//这里会阻塞住，直到拿到锁
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

### 1.7 unlock（）实现分析

unlock不区分公平还是非公平

```java
public void unlock() {
    sync.release(1);
}

 public final boolean release(int arg) {
     if (tryRelease(arg)) {
         Node h = head;
         if (h != null && h.waitStatus != 0)
             unparkSuccessor(h);
         return true;
     }
     return false;
 }

protected final boolean tryRelease(int releases) {
    int c = getState() - releases;
    //只有锁的拥有者才可以释放锁
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    //这里需要考虑重入锁
    if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
    }
    setState(c);
    return free;
}
```

release（）里面做了两件事：tryRelease（..）函数释放锁；unparkSuccessor（..）函数唤醒队列中的后继者。

### 1.8 lockInterruptibly（）实现分析 

当parkAndCheckInterrupt（）返回true的时候，说明有其他线程发送中断信号，直接抛出InterruptedException，跳出for循环，整个函数返回。

### 1.9 tryLock（）实现分析

tryLock（）实现基于调用非公平锁的tryAcquire（..），对state进行CAS操作，如果操作成功就拿到锁；如果操作不成功则直接返回false，也不阻塞

## 2. 读写锁

和互斥锁相比，读写锁（ReentrantReadWriteLock）就是读线程 和读线程之间可以不用互斥了。在正式介绍原理之前，先看一下相关类的继承体系。

```java
public interface ReadWriteLock {
	Lock readLock();
    Lock writeLock();
}
```

![image-20210717115444949](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210717115444949.png)

### 2.1 代码中使用

当使用 ReadWriteLock 的时候，并不是直接使用，而是获得其内部的读锁和写锁，然后分别调用lock/unlock。

```java
public static void main(String[] args) {
    ReadWriteLock rwlock = new ReentrantReadWriteLock();
    Lock rlock = rwlock.readLock();
    rlock.lock();
    rlock.unlock();
    Lock wlock = rwlock.writeLock();
    wlock.lock();
    wlock.unlock();
}
```

### 2.2 读写锁实现的基本原理 

从表面来看，ReadLock和WriteLock是两把锁，实际上它只是同一 把锁的两个视图而已

* 两个视图: 可以理解为是一把锁，线程分成两类：读线程和写线程。读线程和读线程之间不互斥（可以同时拿到这把锁），读线程和写线程互斥，写线程和写线程也互斥。

* readerLock和writerLock实际共 用同一个sync对象

  ```java
  public ReentrantReadWriteLock(boolean fair) {
      sync = fair ? new FairSync() : new NonfairSync();
      readerLock = new ReadLock(this);
      writerLock = new WriteLock(this);
  }
  ```

* 同互斥锁一样，读写锁也是用state变量来表示锁状态的。只是state变量在这里的含义和互斥锁完全不同

* 是把 state 变量拆成两半，低16位，用来记录写锁,高16位，用来“读”锁。但同一 时间既然只能有一个线程写，为什么还需要16位呢？这是因为一个写 线程可能多次重入

  ```java
   abstract static class Sync extends AbstractQueuedSynchronizer {
       static final int SHARED_SHIFT   = 16;
       static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
       static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
       static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;
       
       static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
       static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }
   }
  ```

* 为什么要把一个int类型变量拆成两半， 而不是用两个int型变量分别表示读锁和写锁的状态呢？这是因为无法 用一次CAS 同时操作两个int变量，所以用了一个int型的高16位和低16位分别表示读锁和写锁的状态。

* 当state=0时，说明既没有线程持有读锁，也没有线程持有写锁； 当state！=0时，要么有线程持有读锁，要么有线程持有写锁，两者不 能同时成立，因为读和写互斥。

### 2.3 AQS的两对模板方法

ReentrantReadWriteLock的两个内部类ReadLock和WriteLock中，是如何使用state变量的

acquire/release、acquireShared/releaseShared 是AQS里面的 两对模板方法。互斥锁和读写锁的写锁都是基于acquire/release模板 方法来实现的。读写锁的读锁是基于acquireShared/releaseShared这对模板方法来实现的

将读/写、公平/非公平进行排列组合，就有4种组合

* 读锁的公平实现：Sync.tryAccquireShared（）+FairSync中的两个覆写的子函数。
* 读锁的非公平实现：Sync.tryAccquireShared（）+NonfairSync中的两个覆写的子函数
* 写锁的公平实现：Sync.tryAccquire（）+FairSync中的两个覆写的子函数
* 写锁的非公平实现：Sync.tryAccquire（）+NonfairSync中的两个覆写的子函数。

对于公平，比较容易理解，不论是读锁，还是写锁，只要队列中 有其他线程在排队（排队等读锁，或者排队等写锁），就不能直接去抢锁，要排在队列尾部。

对于非公平，读锁和写锁的实现策略略有差异。先说写锁，写线 程能抢锁，前提是state=0，只有在没有其他线程持有读锁或写锁的情 况下，它才有机会去抢锁。或者state！=0，但那个持有写锁的线程是 它自己，再次重入。写线程是非公平的，就是不管三七二十一就去抢，即一直返回false。

因为读线程和读线程是不互斥的，假设当前线程被读线程持有，然后其他读线程还非公平地一直去抢，可能导致写线程永远拿不到锁，所 以对于读线程的非公平，要做一些“约束”

当发现队列的第1个元素 是写线程的时候，读线程也要阻塞一下，不能“肆无忌惮”地直接去抢

### 2.4 WriteLock公平与非公平实现

写锁是排他锁，实现策略类似于互斥锁，重写了tryAcquire/tryRelease方法。

```java
protected final boolean tryAcquire(int acquires) {
    Thread current = Thread.currentThread();
    int c = getState();
    int w = exclusiveCount(c);
    if (c != 0) {
        // (Note: if c != 0 and w == 0 then shared count != 0)
        if (w == 0 || current != getExclusiveOwnerThread())
            return false;
        if (w + exclusiveCount(acquires) > MAX_COUNT)
            throw new Error("Maximum lock count exceeded");
        // Reentrant acquire
        setState(c + acquires);
        return true;
    }
    if (writerShouldBlock() ||
        !compareAndSetState(c, c + acquires))
        return false;
    setExclusiveOwnerThread(current);
    return true;
}
```

* if （c！=0） and w==0，说明当前一定是读线程拿着锁，写锁一定拿不到，返回false。
* if （c！=0） and w！=0，说明当前一定是写线程拿着锁， 执行current！=getExclusive-OwnerThread（）的判断，发现ownerThread不是自己，返回false。
* c ！ =0 ， w ！ =0 ， 且 current=getExclusiveOwnerThread（），才会走到if （w+exclusive-Count（acquires）> MAX_COUNT）。判断重入次数，重入次数超过最大值，抛出异常。
* if（c=0），说明当前既没有读线程，也没有写线程持有该锁。可以通过CAS操作开抢了。

```java
protected final boolean tryRelease(int releases) {
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
    int nextc = getState() - releases;
    boolean free = exclusiveCount(nextc) == 0;
    if (free)
        setExclusiveOwnerThread(null); 
    setState(nextc);//写锁是排他的
    return free;
}
```

### 2.5 ReadLock公平与非公平实现

读锁是共享锁，重写了 tryAcquireShared/tryReleaseShared 方法，其实现策略和排他锁有很大的差异。

```java
protected final int tryAcquireShared(int unused) {
    Thread current = Thread.currentThread();
    int c = getState();
    if (exclusiveCount(c) != 0 && //写锁被某线程持有，且不是自己，读锁肯定拿不到，直接返回
        getExclusiveOwnerThread() != current)
        return -1;
    int r = sharedCount(c);
    if (!readerShouldBlock() &&//公平和非公平的差异
        r < MAX_COUNT &&
        compareAndSetState(c, c + SHARED_UNIT)) {//高位读锁+1
        if (r == 0) {
            firstReader = current;
            firstReaderHoldCount = 1;
        } else if (firstReader == current) {
            firstReaderHoldCount++;
        } else {
            HoldCounter rh = cachedHoldCounter;
            if (rh == null || rh.tid != getThreadId(current))
                cachedHoldCounter = rh = readHolds.get();
            else if (rh.count == 0)
                readHolds.set(rh);
            rh.count++;
        }
        return 1;
    }
    return fullTryAcquireShared(current);
}
```

* 低16位不等于0，说明有写线程持有锁，并且只有当ownerThread！=自己时，才返回-1。这里面有一个潜台词：如果current=ownerThread，则这段代码不会返回。这是因为一个写线程可以再次去拿读 锁！也就是说，一个线程在持有了WriteLock后，再去调用ReadLock.lock也是可以的。
* 上面的compareAndSetState（c，c+SHARED_UNIT），其实是 把state的高16位加1（读锁的状态），但因为是在高16位，必须把1左移16位再加1。
* firstReader，cachedHoldConunter 之类的变量，只是一些 统计变量，在 ReentrantRead-WriteLock对外的一些查询函数中会用 到，例如，查询持有读锁的线程列表，但对整个读写互斥机制没有影响，此处不再展开解释

```java
protected final boolean tryReleaseShared(int unused) {
    Thread current = Thread.currentThread();
    if (firstReader == current) {
        // assert firstReaderHoldCount > 0;
        if (firstReaderHoldCount == 1)
            firstReader = null;
        else
            firstReaderHoldCount--;
    } else {
        HoldCounter rh = cachedHoldCounter;
        if (rh == null || rh.tid != getThreadId(current))
            rh = readHolds.get();
        int count = rh.count;
        if (count <= 1) {
            readHolds.remove();
            if (count <= 0)
                throw unmatchedUnlockException();
        }
        --rh.count;
    }
    for (;;) {
        int c = getState();
        int nextc = c - SHARED_UNIT;
        if (compareAndSetState(c, nextc))
            // Releasing the read lock has no effect on readers,
            // but it may allow waiting writers to proceed if
            // both read and write locks are now free.
            return nextc == 0;
    }
}
```

因为读锁是共享锁，多个线程会同时持有读锁，所以对读锁的释 放不能直接减1，而是需要通过一个for循环+CAS操作不断重试。这是tryReleaseShared和tryRelease的根本差异所在。

## 3. Condition 

Condition本身也是一个接口，其功能和wait/notify类似

```java
public interface Condition {
	void await() throws InterruptedException;
    void signal();
    void signalAll();
}
```

### 3.1  Condition与Lock的关系

在讲多线程基础的时候，强调wait（）/notify（）必须和synchronized一起使用，Condition也是如此，必须和Lock一起使用。因此，在Lock的接口中，有一个与Condition相关的接口：

```java
public interface Lock {
    Condition newCondition();
}
```

### 3.2 Condition的使用场景 

为一个用数组实现的阻塞 队列，执行put（..）操作的时候，队列满了，生成者线程被阻塞；执行take（）操作的时候，队列为空，消费者线程被阻塞。

```java
public class ArrayBlockingQueue<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, java.io.Serializable {
    //核心就是一把锁，两个条件
	final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    
    public ArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();
    }
    
    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return dequeue();
        } finally {
            lock.unlock();
        }
    }
}
```

### 3.3  Condition实现原理 

使用很简洁，避免了 wait/notify 的生成者通知生 成者、消费者通知消费者的问题。

因为Condition必须和Lock一起使用，所以Condition的实现也是Lock的一部分

### 3.4  await（）实现分析

```java
public final void await() throws InterruptedException {
    if (Thread.interrupted())//正要执行await操作，收到了中断信号，抛出异常
        throw new InterruptedException();
    Node node = addConditionWaiter();//加入condition等待队列
    long savedState = fullyRelease(node);//阻塞在condition之前必须释放锁，否则会释放锁
    int interruptMode = 0;
    while (!isOnSyncQueue(node)) {
        LockSupport.park(this);//自己阻塞自己
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
    }
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)//重新拿锁
        interruptMode = REINTERRUPT;
    if (node.nextWaiter != null) // clean up if cancelled
        unlinkCancelledWaiters();
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
}
```

* 线程调用 await（）的时候，肯定已经先拿到了锁。所以， 在 addConditionWaiter（）内部，对这个双向链表的操作不需要执行CAS操作，线程天生是安全的
* 在线程执行wait操作之前，必须先释放锁。也就是fullyRelease（node），否则会发生死锁。这个和wait/notify与synchronized的配合机制一样。
* 线程从wait中被唤醒后，必须用acquireQueued（node，savedState）函数重新拿锁。
* checkInterruptWhileWaiting（node）代码在park（this） 代码之后，是为了检测在park期间是否收到过中断信号。当线程从park中醒来时，有两种可能：一种是其他线程调用了unpark，另一种是收 到中断信号。这里的await（）函数是可以响应中断的，所以当发现自 己是被中断唤醒的，而不是被unpark唤醒的时，会直接退出while循环，await（）函数也会返回。
* isOnSyncQueue（node）用于判断该Node是否在AQS的同步队 列里面。初始的时候，Node只在Condition的队列里，而不在AQS的队列里。但执行notity操作的时候，会放进AQS的同步队列。

### 3.5 awaitUninterruptibly（）实现分析

与await（）不同，awaitUninterruptibly（）不会响应中断，其 函数的定义中不会有中断异常抛出，下面分析其实现和await（）的区别

```java
 public final void awaitUninterruptibly() {
     Node node = addConditionWaiter();
     long savedState = fullyRelease(node);
     boolean interrupted = false;
     while (!isOnSyncQueue(node)) {
         LockSupport.park(this);
         if (Thread.interrupted())//从park中醒来，收到中断，不退出，继续执行循环
             interrupted = true;
     }
     if (acquireQueued(node, savedState) || interrupted)
         selfInterrupt();
 }
```

可以看出，整体代码和 await（）类似，区别在于收到异常后，不会抛出异常，而是继续执行while循环。

### 3.6 signal（）实现分析

```java
public final void signal() {
    if (!isHeldExclusively())//只有持有锁的队列才可以调用signal
        throw new IllegalMonitorStateException();
    Node first = firstWaiter;
    if (first != null)
        doSignal(first);
}
private void doSignal(Node first) {//唤醒队列的第一个线程
    do {
        if ( (firstWaiter = first.nextWaiter) == null)
            lastWaiter = null;
        first.nextWaiter = null;
    } while (!transferForSignal(first) &&
             (first = firstWaiter) != null);
}

final boolean transferForSignal(Node node) {
    if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
        return false;
    Node p = enq(node);//先把Node放入互斥锁的同步队列中，再调用下面的unpark方法
    int ws = p.waitStatus;
    if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
        LockSupport.unpark(node.thread);
    return true;
}
```

* 同 await（）一样，在调用 notify（）的时候，必须先拿到锁 （否则就会抛出上面的异常），是因为前面执行await（）的时候，把锁释放了。
* 从队列中取出firstWait，唤醒它。在通过调用unpark唤醒 它之前，先用enq（node）函数把这个Node放入AQS的锁对应的阻塞队 列中

## 4. StampedLock

JDK8引入

### 4.1 为什么要引入？

* ReentrantLock： 读与读互斥，写与写互斥，读与写互斥
* ReentrantReadWriteLock：读与读不互斥，写与写互斥，读与写互斥
* StampedLock：读与读不互斥，写与写不互斥，读与写互斥

StampedLock引入了“乐观读”策略，读的时候不加读锁，读出来发现数据被修改 了，再升级为“悲观读”，相当于降低了“读”的地位，把抢锁的天平往“写”的一方倾斜了一下，避免写线程被饿死。

### 4.2 使用场景 

```java
public class Point {
    private double x, y;
    private final StampedLock s1 = new StampedLock();

    void move(double deltaX, double deltaY) {
        //多个线程调用，修改x,y的值
        long stamp = s1.writeLock();
        try {
            x = deltaX;
            y = deltaY;
        } finally {
            s1.unlock(stamp);
        }
    }

    double distanceFromOrigin() {

        long stamp = s1.tryOptimisticRead();//使用乐观锁
        double currentX = x, currentY = y;
        if (!s1.validate(stamp)) {
            /**
             * 上面这三行关键代码对顺序非常敏感，不能有重排序。 因
             * 为 state 变量已经是volatile，所以可以禁止重排序，但stamp并 不是volatile的。
             * 为此，在validate（stamp）函数里面插入内存屏 障。
             */
            stamp = s1.readLock();//升级悲观锁
            try {
                currentX = x;
                currentY = y;
            } finally {
                s1.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
```

### 4.3 “乐观读”的实现原理 

StampedLock是一个读写锁，因此也会像读写锁那样，把一 个state变量分成两半，分别表示读锁和写锁的状态。同时，它还需要 一个数据的version。但正如前面所说，一次CAS没有办法操作两个变 量，所以这个state变量本身同时也表示了数据的version。下面先分析state变量。

* 用最低的8位表示读和写的状态，其中第8位表 示写锁的状态，最低的7位表示读锁的状态。因为写锁只有一个bit位，所以写锁是不可重入的。

### 4.4 悲观读/写：“阻塞”与“自旋”策略实现差异

同ReadWriteLock一样，StampedLock也要进行悲观的读锁和写锁 操作。不过，它不是基于AQS实现的，而是内部重新实现了一个阻塞队列

```java
public class StampedLock implements java.io.Serializable {
	static final class WNode {
        volatile WNode prev;
        volatile WNode next;
        volatile WNode cowait;    // list of linked readers
        volatile Thread thread;   // non-null while possibly parked
        volatile int status;      // 0, WAITING, or CANCELLED
        final int mode;           // RMODE or WMODE
        WNode(int m, WNode p) { mode = m; prev = p; }
    }
}
```

这个阻塞队列和 AQS 里面的很像。刚开始的时候，whead=wtail=NULL，然后初始化，建一个空节点，whead和wtail都指向这个空节 点，之后往里面加入一个个读线程或写线程节点。但基于这个阻塞队 列实现的锁的调度策略和AQS很不一样，也就是“自旋”。在AQS里 面，当一个线程CAS state失败之后，会立即加入阻塞队列，并且进入 阻塞状态。但在StampedLock中，CAS state失败之后，会不断自旋， 自旋足够多的次数之后，如果还拿不到锁，才进入阻塞状态。为此， 根据CPU的核数，定义了自旋次数的常量值。如果是单核的CPU，肯定不能自旋，在多核情况下，才采用自旋策略。
