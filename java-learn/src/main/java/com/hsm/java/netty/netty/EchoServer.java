package com.hsm.java.netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Classname EchoServer
 * @Description TODO
 * @Date 2021/7/28 19:42
 * @Created by huangsm
 */
@Slf4j
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8089).start();
    }

    public void start() throws InterruptedException {
        //EventLoop 用于处理 Channel 的 I/O 操作 一个 EventLoopGroup 具有一个或多个 EventLoop  EventLoop 作为一个 Thread 给 Channel 执行工作
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            //Netty 应用程序通过设置 bootstrap（引导）类的开始，该类提供了一个 用于应用程序网络层配置的容器。
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(eventLoopGroup)
                    //底层网络传输 API 必须提供给应用 I/O操作的接口 当创建一个 Channel，Netty 通过 一个单独的 EventLoop 实例来注册该 Channel（并同样是一个单独的 Thread）的通道的使用寿命。
                    //这就是为什么你的应用程序不需要同步 Netty 的 I/O操作;所有 Channel 的 I/O 始终用相同的线程来执行。
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(8089))
                    //ChannelHandler 支持很多协议，并且提供用于数据处理的容器 \
                    //ChannelHandler 由特定事件触发。 ChannelHandler 可专用于几乎所有的动作，包括将一个对象转为字节（或相反），执行过程中抛出的异常处理。
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //ChannelPipeline 提供了一个容器给 ChannelHandler 链并提供了一个API 用于管理沿着链入站和出站事件的流动
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //Netty 所有的 I/O 操作都是异步
            ChannelFuture f = bootstrap.bind().sync();
            log.info(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        }  finally {
            eventLoopGroup.shutdownGracefully().sync();
        }

    }
}
