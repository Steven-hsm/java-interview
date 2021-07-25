## NIO

*NIO 最初是为 New Input/Output 的缩写。然而，Java 的 API 已经存在足够长的时间，它不再是新的。现在普遍使用的缩写来表示Nonblocking I/O (非阻塞 I/O)。另一方面，一般（包括作者）指阻塞 I/O 为 OIO 或 Old Input/Output。你也可能会遇到普通 I/O。*

![image-20210725104802487](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210725104802487.png)

Selector 最终决定哪一组注册的 socket 准备执行 I/O。正如我们之前所解释的那样，这 I/O 操作设置为非阻塞模式。通过通知，一个线程可以同时处理多个并发连接。（一个 Selector 由一个线程通常处理，但具体实施可以使用多个线程。）因此，每次读或写操作执行能立即检查完成。总体而言，该模型提供了比 阻塞 I/O 模型 更好的资源使用，因为

- 可以用较少的线程处理更多连接，这意味着更少的开销在内存和上下文切换上
- 当没有 I/O 处理时，线程可以被重定向到其他任务上。

如果你要直接使用这些 Java API 构建的 NIO 建立你的应用程序，只是这样做正确和安全是无法保证的。实现可靠和可扩展的 event-processing（事件处理器）来处理和调度数据并保证尽可能有效地，这是一个繁琐和容易出错的任务，最好留给专家 - Netty。