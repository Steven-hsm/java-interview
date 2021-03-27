```java
public interface Queue<E> extends Collection<E> {
	//添加元素,容量满会抛出异常，与offer对比，多了IllegalStateException
	boolean add(E e);
    //添加元素，容量满会返回false
	boolean offer(E e);
    //移除元素，没有元素时会抛出异常NoSuchElementException
	E remove();
    //移除元素，为空时返回null
	E poll();
    //查看元素，没有元素时会抛出异常NoSuchElementException
	E element();
    //查看元素，为空时返回null
	E peek();
}
```



```java
public interface BlockingQueue<E> extends Queue<E> {
    //添加元素，如果空间已满等待
	void put(E e) throws InterruptedException;
    //添加元素，空间满等待一段时间后返回false
    boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException;
    //取走元素，如果没有元素，等待有元素之后取走
    E take() throws InterruptedException;
    //取走元素，如果没有元素，等待一段时间后返回false
    E poll(long timeout, TimeUnit unit) throws InterruptedException;
}
```



```java
public interface BlockingDeque<E> extends BlockingQueue<E>, Deque<E> {

}
```

```java
public interface Future<V> {
    //暂停任务
	boolean cancel(boolean mayInterruptIfRunning);
    //暂停时否成功
    boolean isCancelled();
    //任务时否完成
    boolean isDone();
    //等待任务完成后返回结果，会阻塞
    V get() throws InterruptedException, ExecutionException;
    //等待任务返回结果，超时抛出异常
    V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
}
```



```java
public interface RunnableFuture<V> extends Runnable, Future<V> {
	void run();
}
```

