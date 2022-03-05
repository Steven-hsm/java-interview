### 1. Netty模型

<img src="C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220305142727597.png" alt="image-20220305142727597" style="zoom:67%;" />

1.  Netty 抽象出两组线程池BossGroup和WorkerGroup，BossGroup专门负责接收客户端的连接, WorkerGroup专 门负责网络的读写 
2. BossGroup和WorkerGroup类型都是NioEventLoopGroup 
3. NioEventLoopGroup 相当于一个事件循环**线程组**, 这个组中含有多个事件循环线程 ， 每一个事件循环线程是 NioEventLoop 
4. 每个NioEventLoop都有一个selector , 用于监听注册在其上的socketChannel的网络通讯 
5. 每个Boss NioEventLoop线程内部循环执行的步骤有 3 步 
   * 处理accept事件 , 与client 建立连接 , 生成 NioSocketChannel 
   * 将NioSocketChannel注册到某个worker NIOEventLoop上的selector 
   * 处理任务队列的任务 ， 即runAllTasks 
6. 每个worker NIOEventLoop线程循环执行的步骤 
   * 轮询注册到自己selector上的所有NioSocketChannel 的read, write事件 
   * 处理 I/O 事件， 即read , write 事件， 在对应NioSocketChannel 处理业务 
   * runAllTasks处理任务队列TaskQueue的任务 ，一些耗时的业务处理一般可以放入TaskQueue中慢慢处理，这样不影响数据在 pipeline 中的流动处理 
7. 每个worker NIOEventLoop处理NioSocketChannel业务时，会使用 pipeline (管道)，管道中维护了很多 handler 处理器用来处理 channel 中的数据 

**模块组件**

* **Bootstrap、ServerBootstrap**：Bootstrap 意思是引导，一个 Netty 应用通常由一个 Bootstrap 开始，主要作用是配置整个 Netty 程序，串联各个组 

  件，Netty 中 Bootstrap 类是客户端程序的启动引导类，ServerBootstrap 是服务端启动引导类。 

* **Future、ChannelFuture**：Netty 中所有的 IO 操作都是异步的，不能立刻得知消息是否被正确处理。但是可以过一会等它执行完成或者直接注册一个监听，具体的实现就是通过 Future 和 ChannelFutures，他们可以注 册一个监听，当操作执行成功或失败时监听会自动触发注册的监听事件。 

* **Channel**:Netty 网络通信的组件，能够用于执行网络 I/O 操作。Channel 为用户提供： 

  * 当前网络连接的通道的状态（例如是否打开？是否已连接？）
  * 网络连接的配置参数 （例如接收缓冲区大小） 
  * 提供异步的网络 I/O 操作(如建立连接，读写，绑定端口)，异步调用意味着任何 I/O 调用都将立即返回，并且不保 证在调用结束时所请求的 I/O 操作已完成。 
  * 调用立即返回一个 ChannelFuture 实例，通过注册监听器到 ChannelFuture 上，可以 I/O 操作成功、失败或取 消时回调通知调用方
  * 支持关联 I/O 操作与对应的处理程序。 
  * 不同协议、不同的阻塞类型的连接都有不同的 Channel 类型与之对应。
    * NioSocketChannel，异步的客户端 TCP Socket 连接。
    * NioServerSocketChannel，异步的服务器端 TCP Socket 连接。
    * NioDatagramChannel，异步的 UDP 连接。
    * NioSctpChannel，异步的客户端 Sctp 连接。
    *  NioSctpServerChannel，异步的 Sctp 服务器端连接。

* **Selector**:Netty 基于 Selector 对象实现 I/O 多路复用，通过 Selector 一个线程可以监听多个连接的 Channel 事件。

  * 当向一个 Selector 中注册 Channel 后，Selector 内部的机制就可以自动不断地查询(Select) 这些注册的 Channel 是否有已就绪的 I/O 事件（例如可读，可写，网络连接完成等），这样程序就可以很简单地使用一个线程高效地管理多个 Channe

* **NioEventLoop**：NioEventLoop 中维护了一个线程和任务队列，支持异步提交执行任务，线程启动时会调用 NioEventLoop 的 run 方 法，执行 I/O 任务和非 I/O 任务

  * I/O 任务，即 selectionKey 中 ready 的事件，如 accept、connect、read、write 等，由 processSelectedKeys 方 法触发
  * 非 IO 任务，添加到 taskQueue 中的任务，如 register0、bind0 等任务，由 runAllTasks 方法触发。

* **NioEventLoopGroup**：主要管理 eventLoop 的生命周期，可以理解为一个线程池，内部维护了一组线程，每个线程 (NioEventLoop)负责处理多个 Channel 上的事件，而一个 Channel 只对应于一个线程

* **ChannelHandler**：ChannelHandler 是一个接口，处理 I/O 事件或拦截 I/O 操作，并将其转发到其 ChannelPipeline(业务处理链)中的 下一个处理程序

  * ChannelHandler 本身并没有提供很多方法，因为这个接口有许多的方法需要实现，方便使用期间，可以继承它的子类
    * ChannelInboundHandler 用于处理入站 I/O 事件
    * ChannelOutboundHandler 用于处理出站 I/O 操作。 
    * ChannelInboundHandlerAdapter 用于处理入站 I/O 事件。
    * ChannelOutboundHandlerAdapter 用于处理出站 I/O 操作。

* **ChannelHandlerContext**：保存 Channel 相关的所有上下文信息，同时关联一个 ChannelHandler 对象

* **ChannelPipline**：保存 ChannelHandler 的 List，用于处理或拦截 Channel 的入站事件和出站操作。

  * ChannelPipeline 实现了一种高级形式的拦截过滤器模式，使用户可以完全控制事件的处理方式，以及 Channel 中各 个的 ChannelHandler 如何相互交互
  * 一个 Channel 包含了一个 ChannelPipeline，而 ChannelPipeline 中又维护了一个由 ChannelHandlerContext 组成的双向链表，并且每个 ChannelHandlerContext 中又关联着一个 ChannelHandler。 
  * read事件(入站事件)和write事件(出站事件)在一个双向链表中，入站事件会从链表 head 往后传递到最后一个入站的 handler，出站事件会从链表 tail 往前传递到最前一个出站的 handler，两种类型的 handler 互不干扰。 