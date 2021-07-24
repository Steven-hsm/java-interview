

<h2><center>CompletableFuture</center></h2>

阿里巴巴2021版JDK源码笔记（2月第三版）.pdf

链接：https://pan.baidu.com/s/1XhVcfbGTpU83snOZVu8AXg 
提取码：l3gy

从JDK 8开始，在Concurrent包中提供了一个强大的异步编程工具CompletableFuture。在JDK8之前，异步编程可以通过线程池和Future来实现，但功能还不够强大。CompletableFuture的出现，使Java的异步编程能力向前迈进了一大步。

## 1. CompletableFuture用法 

### 1.1  最简单的用法

CompletableFuture实现了Future接口，所以它也具有Future的特性：调用get（）方法会阻塞在那，直到结果返回。

```java
CompletableFuture<String> future = new  CompletableFuture<String>();
String s = future.get();
```

另外1个线程调用complete方法完成该Future，则所有阻塞在get（）方法的线程都将获得返回结果。

```java
future.complete("12312");
```

### 1.2 提交任务

1. runAsync

   ```java
   CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
       System.out.println("task is running");
   });
   future1.get();
   ```

2. supplyAsync

   ```java
    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(new Supplier<String>() {
        @Override
        public String get() {
            return "this is result";
        }
    });
   future2.get();
   ```

   CompletableFuture和Future很相似，都可以提交两类任务：一类是无返回值的，另一类是有返回值的。

### 1.3 链式的CompletableFuture：thenRun、thenAccept和 thenApply 

对于 Future，在提交任务之后，只能调用 get（）等结果返回； 但对于 CompletableFuture，可以在结果上面再加一个callback，当得到结果之后，再接着执行callback。

1. thenRun

```java
CompletableFuture.supplyAsync(new Supplier<String>() {
    @Override
    public String get() {
        return "this is result";
    }
}).thenRun(()->{
    System.out.println("task finish");
});
```

2. thenAccept

   ```java
   CompletableFuture.supplyAsync(new Supplier<String>() {
       @Override
       public String get() {
           return "this is result";
       }
   }).thenAccept(result->{
       System.out.println(result);
   });
   ```

3. thenApply

   ```java
   CompletableFuture.supplyAsync(new Supplier<String>() {
       @Override
       public String get() {
           return "this is result";
       }
   }).thenApply(result->{
       return result + "1231";
   });
   ```

三个例子都是在任务执行完成之后，再紧急执行一个callback，只是callback的形式有所区别：

* thenRun后面跟的是一个无参数、无返回值的方法，即Runnable，所以最终的返回值是CompletableFuture<；Void>；类型。

* thenAccept 后面跟的是一个有参数、无返回值的方法，称 为 Consumer，返回值也是CompletableFuture<；Void>；类型。 顾名思义，只进不出，所以称为Consumer；前面的Supplier，是无参数，有返回值，只出不进，和Consumer刚好相反。

* thenApply 后面跟的是一个有参数、有返回值的方法，称为 Function。返回值是CompletableFuture<；String>；类型。而参数接收的是前一个任务，即 supplyAsync（..）这个任务的 返回值。因此这里只能用supplyAsync，不能用runAsync。因为runAsync没有返回值，不能为下一个链式方法传入参数

### 1.4 CompletableFuture的组合：thenCompose与thenCombine

1.  thenCompose

   thenApply接收的是一个Function，但是这个Function的返回值是一个通常的基本数据类型或一个对象，而不是另外 一个 CompletableFuture。如果 Function 的返回值也是一个CompletableFuture，就会出现嵌套的CompletableFuture

2. thenCombine

