package com.hsm.java.netty.netty.chat;

import com.hsm.java.netty.netty.chat.handler.ChatServerHandler;
import com.hsm.java.util.ThreadUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Classname ChatServer
 * @Description TODO
 * @Date 2021/8/3 11:52
 * @Created by huangsm
 */
public class ChatServer {
    /**
     * 定义一个线程组，管理所有channel
     * GlobalEventExecutor 全局的事件执行器，是一个单例
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private String host;
    private int port;

    public ChatServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 编写run方法，处理客户端请求
     */
    public void run(){
        // 创建线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置参数
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 向pipeline加入一个解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            // 向pipeline加入一个编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            // 加入自定义处理器
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("服务器 is ready");
            // 异步监听
            ChannelFuture sync = serverBootstrap.bind(port).sync();
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("监听端口 6668 成功");
                    } else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });
            Channel channel = sync.channel();
            new Thread(() ->{
                while (true){
                    ThreadUtils.sleep(1000);
                    channelGroup.writeAndFlush("这个是循环发的消息");
                }
            }).start();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        ChatServer jymGroupChatSever = new ChatServer("127.0.0.1",6668);
        jymGroupChatSever.run();
    }
}
