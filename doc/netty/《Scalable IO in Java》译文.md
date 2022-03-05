原文连接：http://gee.cs.oswego.edu/dl/cpjslides/nio.pdf

链接：https://pan.baidu.com/s/1FIn3XiJXuZl_aSvPtJf77A 
提取码：j0cr

### 1. 大纲

* 可伸缩网络服务
* 基于事件驱动处理
* 响应式模式
  * 基本版本
  * 多线程版本
  * 其他变种
* 分析一下 java.nio nonblocking IO API

### 2. 网络服务

在一般的网络或分布式服务等应用程序中，大都有一些相同的基本机构

* 读取请求数据
* 对请求数据进行解码
* 处理请求数据
* 对响应数据进行编码
* 发送响应数据

当然在实际应用中每一步的运行效率都是不同的，例如其中可能涉及到xml解析、文件传输、web页面的加载、计算服务等不同功能。

#### 2.1 传统的服务设计

![image-20220305113953146](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220305113953146.png)

每一个处理器都有自己的线程中执行

**传统的ServerScket循环**

```java
class Server implements Runnable {
	public void run() {
 		try {
 			ServerSocket ss = new ServerSocket(PORT);
			while (!Thread.interrupted())
 				new Thread(new Handler(ss.accept())).start();
 				// or, single-threaded, or a thread pool
 		} catch (IOException ex) { /* ... */ }
 	}
 static class Handler implements Runnable {
 	final Socket socket;
 	Handler(Socket s) { socket = s; }
 	public void run() {
		try {
 			byte[] input = new byte[MAX_INPUT];
 			socket.getInputStream().read(input);
 			byte[] output = process(input);
 			socket.getOutputStream().write(output);
 		} catch (IOException ex) { /* ... */ }
 	}
 	private byte[] process(byte[] cmd) { /* ... */ }
 }
}
Note: most exception handling elided from code examples
```

#### 2.2 构建可伸缩的目标

* 在高并发的情况下能优雅的降级

* 能够随着硬件资源（CPU,内存，硬盘，带宽）的增加，性能持续改进；

* 高可用、高性能的目标

  * 短延迟

  * 高吞吐

  * 可调节的服务质量

    分发处理就是实现可伸缩目标的一个最佳方式。

#### 2.3 分发

* 将处理过程拆分为小任务（每个任务单独执行没有阻塞）

* 当存在任务时才执行（这里，一个I/O事件就类似于一个触发器）

  ![image-20220305115131183](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220305115131183.png)

* Java  NIO 提供了以下机制

  * 非阻塞读写
  * 根据关联的I/O事件分发任务

* 无尽的可能性（一系列事件驱动的设计）

### 3.基于事件驱动设计

* 一般比其他方案更有效率
  * 资源更少（不需要一个客户端一个线程）
  * 开销更小（上下文切换变少，锁互斥更少）
  * 任务分发可能会变慢（必须手动将动作绑定到事件）
* 编程复杂度变高
  * 必须分解成简单的非阻塞动作
    * 和GUI事件编程很类似
    * 不可能把所有阻塞都消除掉，特别是GC， page faults(内存缺页中断)等
  * 必须跟踪服务的逻辑状态

**基于时间驱动的AWT**

​	![image-20220305120208772](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220305120208772.png)

I/O事件驱动和AWT类似，但是设计不同

### 4. 响应式编程

* **Reactor**：基于I/O事件，将响应分发给合适的处理器（类似于AWT线程）
* **handler**: 执行非阻塞操作(类似于AWT的事件监听器)
* 通过将handler和事件绑定进行管理（类似于AWT addActionListener 添加事件监听）

#### 4.1 传统的响应式编程

单线程模型

![image-20220305121205952](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220305121205952.png)

**Java NIO支持**

### **Channels**

支持于文件、socket进行连接，且支持非阻塞读

### **Buffers**

用于被Channels读写的字节数组对象

### **Selectors**

用于判断channle发生IO事件的选择器

### **SelectionKeys**

负责IO事件的状态与绑定 

第一步 Rector线程的初始化

```java
class Reactor implements Runnable { 
    final Selector selector;
    final ServerSocketChannel serverSocket;
    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT); //注册accept事件
        sk.attach(new Acceptor()); //调用Acceptor()为回调方法
    }
    
    public void run() { 
        try {
            while (!Thread.interrupted()) {//循环
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext())
                    dispatch((SelectionKey)(it.next()); //dispatch分发事件
                selected.clear();
            }
        } catch (IOException ex) { /* ... */ }
    }
    
    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)(k.attachment()); //调用SelectionKey绑定的调用对象
        if (r != null)
            r.run();
    }
    
    // Acceptor 连接处理类
    class Acceptor implements Runnable { // inner
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null)
                new Handler(selector, c);
            }
            catch(IOException ex) { /* ... */ }
        }
    }
}
```

第二步 Handler处理类的初始化

```java
final class Handler implements Runnable {
    final SocketChannel socket;
    final SelectionKey sk;
    ByteBuffer input = ByteBuffer.allocate(MAXIN);
    ByteBuffer output = ByteBuffer.allocate(MAXOUT);
    static final int READING = 0, SENDING = 1;
    int state = READING;
    
    Handler(Selector sel, SocketChannel c) throws IOException {
        socket = c;
        c.configureBlocking(false);
        // Optionally try first read now
        sk = socket.register(sel, 0);
        sk.attach(this); //将Handler绑定到SelectionKey上
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }
    boolean inputIsComplete() { /* ... */ }
    boolean outputIsComplete() { /* ... */ }
    void process() { /* ... */ }
    
    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (IOException ex) { /* ... */ }
    }
    
    void read() throws IOException {
        socket.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            // Normally also do first write now
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }
    void send() throws IOException {
        socket.write(output);
        if (outputIsComplete()) sk.cancel(); 
    }
}
```

下面是基于GoF状态对象模式对Handler类的一个优化实现，不需要再进行状态的判断。

```java
class Handler { // ...
    public void run() { // initial state is reader
        socket.read(input);
        if (inputIsComplete()) {
            process();
            sk.attach(new Sender()); 
            sk.interest(SelectionKey.OP_WRITE);
            sk.selector().wakeup();
        }
    }
    class Sender implements Runnable {
        public void run(){ // ...
            socket.write(output);
            if (outputIsComplete()) sk.cancel();
        }
    }
}
```

### 2、多线程设计模式

在多处理器场景下，为实现服务的高性能我们可以有目的的采用多线程模式：

* 增加Worker线程，专门用于处理非IO操作，因为通过上面的程序我们可以看到，反应器线程需要迅速触发处理流程，而如果处理过程也就是process()方法产生阻塞会拖慢反应器线程的性能，所以我们需要把一些非IO操作交给Woker线程来做；
* 拆分并增加反应器Reactor线程，一方面在压力较大时可以饱和处理IO操作，提高处理能力；另一方面维持多个Reactor线程也可以做负载均衡使用；线程的数量可以根据程序本身是CPU密集型还是IO密集型操作来进行合理的分配；

#### 2.1 多线程模式

Reactor多线程设计模式具备以下几个特点：

* 通过卸载非IO操作来提升Reactor 线程的处理性能，这类似与POSA2 中Proactor的设计；
* 比将非IO操作重新设计为事件驱动的方式更简单；
* 但是很难与IO重叠处理，最好能在第一时间将所有输入读入缓冲区；（这里我理解的是最好一次性读取缓冲区数据，方便异步非IO操作处理数据）
* 可以通过线程池的方式对线程进行调优与控制，一般情况下需要的线程数量比客户端数量少很多；

下面是Reactor多线程设计模式的一个示意图与示例代码（我们可以看到在这种模式中在Reactor线程的基础上把非IO操作放在了Worker线程中执行）：

![img](https://img2018.cnblogs.com/blog/780676/201907/780676-20190727174207652-780995378.png)

```java
    class Handler implements Runnable {
        // uses util.concurrent thread pool
        static PooledExecutor pool = new PooledExecutor(...);//声明线程池
        static final int PROCESSING = 3;

        // ...
        synchronized void read() { // ...
            socket.read(input);
            if (inputIsComplete()) {
                state = PROCESSING;
                pool.execute(new Processer());//处理程序放在线程池中执行
            }
        }

        synchronized void processAndHandOff() {
            process();
            state = SENDING; // or rebind attachment
            sk.interest(SelectionKey.OP_WRITE);
        }

        class Processer implements Runnable {
            public void run() {
                processAndHandOff();
            }
        }
    }
```

当你把非IO操作放到线程池中运行时，你需要注意以下几点问题：

* 任务之间的协调与控制，每个任务的启动、执行、传递的速度是很快的，不容易协调与控制；
* 每个hander中dispatch的回调与状态控制；
* 不同线程之间缓冲区的线程安全问题；
* 需要任务返回结果时，任务线程等待和唤醒状态间的切换；

为解决上述问题可以使用PooledExecutor线程池框架，这是一个可控的任务线程池，主函数采用execute(Runnable r)，它具备以下功能，可以很好的对池中的线程与任务进行控制与管理：

* 可设置线程池中最大与最小线程数；
* 按需要判断线程的活动状态，及时处理空闲线程；
* 当执行任务数量超过线程池中线程数量时，有一系列的阻塞、限流的策略；

####  **2.2 基于多个反应器的多线程模式**

这是对上面模式的进一步完善，使用反应器线程池，一方面根据实际情况用于匹配调节CPU处理与IO读写的效率，提高系统资源的利用率，另一方面在静态或动态构造中每个反应器线程都包含对应的Selector,Thread,dispatchloop,下面是一个简单的代码示例与示意图（Netty就是基于这个模式设计的，一个处理Accpet连接的mainReactor线程，多个处理IO事件的subReactor线程）：

```java
    Selector[] selectors; // Selector集合，每一个Selector 对应一个subReactor线程
    //mainReactor线程
    class Acceptor { // ...
        public synchronized void run() { 
            //...
            Socket connection = serverSocket.accept(); 
            if (connection != null)
              new Handler(selectors[next], connection); 
            if (++next == selectors.length)
                next = 0;
        }
    }
```

 

![img](https://img2018.cnblogs.com/blog/780676/201907/780676-20190728110352690-166412138.png)

在服务的设计当中，我们还需要注意与java.nio包特性的结合：

一是注意线程安全，每个selectors 对应一个Reactor 线程，并将不同的处理程序绑定到不同的IO事件，在这里特别需要注意线程之间的同步；

二是java nio中文件传输的方式：

* Memory-mapped files 内存映射文件的方式，通过缓存区访问文件；

* Direct buffers直接缓冲区的方式，在合适的情况下可以使用零拷贝传输，但同时这会带来初始化与内存释放的问题（需要池化与主动释放）;