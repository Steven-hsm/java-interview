## Netty实现简单DubboRpc

### 1. RPC 基本介绍

1. `RPC（Remote Procedure Call）`—远程过程调用，是一个计算机通信协议。该协议允许运行于一台计算机的程序调用另一台计算机的子程序，而程序员无需额外地为这个交互作用编程

2. 两个或多个应用程序都分布在不同的服务器上，它们之间的调用都像是本地方法调用一样(如图)

   <img src="https://dongzl.github.io/netty-handbook/_media/chapter11/chapter11_01.png" alt="img" style="zoom:200%;" />

3. 常见的 `RPC` 框架有：比较知名的如阿里的 `Dubbo`、`Google` 的 `gRPC`、`Go` 语言的 `rpcx`、`Apache` 的 `thrift`，`Spring` 旗下的 `SpringCloud`。

   

### 2. RPC 调用流程图

   ![img](https://dongzl.github.io/netty-handbook/_media/chapter11/chapter11_03.png)

### 3. 自己实现dubboRpc

1. 创建一个接口，定义抽象方法。用于消费者和提供者之间的约定。
2. 创建一个提供者，该类需要监听消费者的请求，并按照约定返回数据。
3. 创建一个消费者，该类需要透明的调用自己不存在的方法，内部需要使用 `Netty` 请求提供者返回数据

![img](https://dongzl.github.io/netty-handbook/_media/chapter11/chapter11_04.png)

#### 3.1 通用接口

```
public interface HelloService {
    
    String hello(String mes);

    String test(String mes);
}
```

#### 3.2 服务器端实现

1. 接口实现类

   ```java
   public class HelloServiceImpl implements HelloService {
       private static int count = 0;
   
       //当有消费方调用该方法时， 就返回一个结果
       @Override
       public String hello(String mes) {
           System.out.println("收到客户端消息=" + mes);
           return "hello";
       }
   
       @Override
       public String test(String mes) {
           return "返回测试消息";
       }
   }
   ```

2. 服务处理器

   ```java
   public class NettyServerHandler  extends ChannelInboundHandlerAdapter {
       @Override
       public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
           //获取客户端发送的消息，并调用服务
           System.out.println("msg=" + msg);
           //客户端在调用服务器的api 时，我们需要定义一个协议
           //调用的是hello方法，就执行hello方法，返回给客户端
           if (msg.toString().startsWith(ClientBootstrap.SERVICE_NAME + "#" + ClientBootstrap.METHOD_NAME_HELLO)) {
               String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
               ctx.writeAndFlush(result);
           }
           ////调用的是test方法，就执行hello方法，返回给客户端
           if (msg.toString().startsWith(ClientBootstrap.SERVICE_NAME + "#" + ClientBootstrap.METHOD_NAME_TEST)) {
               String result = new HelloServiceImpl().test(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
               ctx.writeAndFlush(result);
           }
       }
   
       @Override
       public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
           ctx.close();
       }
   }
   ```

3. 服务类

   ```java
   public class NettyServer {
       public static void startServer(String hostName, int port) {
           startServer0(hostName, port);
       }
   
       //编写一个方法，完成对NettyServer的初始化和启动
   
       private static void startServer0(String hostname, int port) {
   
           EventLoopGroup bossGroup = new NioEventLoopGroup(1);
           EventLoopGroup workerGroup = new NioEventLoopGroup();
   
           try {
   
               ServerBootstrap serverBootstrap = new ServerBootstrap();
   
               serverBootstrap.group(bossGroup, workerGroup)
                       .channel(NioServerSocketChannel.class)
                       .childHandler(new ChannelInitializer<SocketChannel>() {
                                         @Override
                                         protected void initChannel(SocketChannel ch) throws Exception {
                                             ChannelPipeline pipeline = ch.pipeline();
                                             pipeline.addLast(new StringDecoder());
                                             pipeline.addLast(new StringEncoder());
                                             pipeline.addLast(new NettyServerHandler()); //业务处理器
   
                                         }
                                     }
   
                       );
   
               ChannelFuture channelFuture = serverBootstrap.bind(hostname, port).sync();
               System.out.println("服务提供方开始提供服务~~");
               channelFuture.channel().closeFuture().sync();
   
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               bossGroup.shutdownGracefully();
               workerGroup.shutdownGracefully();
           }
       }
   }
   ```

4. 启动类

   ```java
   public class MyServerBootstrap {
       public static void main(String[] args) {
           //代码代填..
           NettyServer.startServer("127.0.0.1", 7000);
       }
   }
   ```

#### 3.3 服务器端实现

1. 客户端处理器

   ```java
   public class NettyClientHandler  extends ChannelInboundHandlerAdapter implements Callable {
       private ChannelHandlerContext context;//上下文
       private String result; //返回的结果
       private String para; //客户端调用方法时，传入的参数
   
       //与服务器的连接创建后，就会被调用, 这个方法是第一个被调用(1)
       @Override
       public void channelActive(ChannelHandlerContext ctx) throws Exception {
           System.out.println(" channelActive 被调用  ");
           context = ctx; //因为我们在其它方法会使用到 ctx
       }
   
       //收到服务器的数据后，调用方法 (4)
       //
       @Override
       public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
           System.out.println(" channelRead 被调用  ");
           result = msg.toString();
           notify(); //唤醒等待的线程
       }
   
       @Override
       public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
           ctx.close();
       }
   
       //被代理对象调用, 发送数据给服务器，-> wait -> 等待被唤醒(channelRead) -> 返回结果 (3)-》5
       @Override
       public synchronized Object call() throws Exception {
           System.out.println(" call1 被调用  ");
           context.writeAndFlush(para);
           //进行wait
           wait(); //等待channelRead 方法获取到服务器的结果后，唤醒
           System.out.println(" call2 被调用  ");
           return result; //服务方返回的结果
   
       }
   
       //(2)
       void setPara(String para) {
           System.out.println(" setPara  ");
           this.para = para;
       }
   }
   ```

2. 客户

   ```java
   public class NettyClient {
       //创建线程池
       private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
   
       private static NettyClientHandler client;
       private int count = 0;
   
       //编写方法使用代理模式，获取一个代理对象
   
       public Object getBean(final Class<?> serivceClass, final String providerName) {
   
           return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                   new Class<?>[]{serivceClass}, (proxy, method, args) -> {
   
                       System.out.println("(proxy, method, args) 进入...." + (++count) + " 次");
                       //{}  部分的代码，客户端每调用一次 hello, 就会进入到该代码
                       if (client == null) {
                           initClient();
                       }
   
                       //设置要发给服务器端的信息
                       //服务名#方法名#参数
                       client.setPara(providerName +"#"+ method.getName() + "#" + args[0]);
   
                       //
                       return executor.submit(client).get();
   
                   });
       }
   
       //初始化客户端
       private static void initClient() {
           client = new NettyClientHandler();
           //创建EventLoopGroup
           NioEventLoopGroup group = new NioEventLoopGroup();
           Bootstrap bootstrap = new Bootstrap();
           bootstrap.group(group)
                   .channel(NioSocketChannel.class)
                   .option(ChannelOption.TCP_NODELAY, true)
                   .handler(
                           new ChannelInitializer<SocketChannel>() {
                               @Override
                               protected void initChannel(SocketChannel ch) throws Exception {
                                   ChannelPipeline pipeline = ch.pipeline();
                                   pipeline.addLast(new StringDecoder());
                                   pipeline.addLast(new StringEncoder());
                                   pipeline.addLast(client);
                               }
                           }
                   );
   
           try {
               bootstrap.connect("127.0.0.1", 7000).sync();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }
   ```

3. 客户端启动类

   ```java
   public class ClientBootstrap {
       //服务接口
       public static final String SERVICE_NAME = "HelloService";
       //服务方法
       public static final String METHOD_NAME_TEST = "test";
       public static final String METHOD_NAME_HELLO = "hello";
   
       public static void main(String[] args) throws Exception {
   
           //创建一个消费者
           NettyClient customer = new NettyClient();
   
           //创建代理对象
           HelloService service = (HelloService) customer.getBean(HelloService.class, SERVICE_NAME);
   
           for (; ; ) {
               Thread.sleep(2 * 1000);
               //通过代理对象调用服务提供者的方法(服务)
               String res = service.test("你好 dubbo~");
               System.out.println("test调用的结果 res= " + res);
               String res2 = service.hello("你好 dubbo~");
               System.out.println("hello调用的结果 res= " + res2);
           }
       }
   }
   ```

   