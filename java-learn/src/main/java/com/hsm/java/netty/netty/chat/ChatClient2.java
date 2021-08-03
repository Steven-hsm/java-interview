package com.hsm.java.netty.netty.chat;

import com.hsm.java.netty.netty.chat.handler.ChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Classname ChatClient
 * @Description TODO
 * @Date 2021/8/3 11:55
 * @Created by huangsm
 */
public class ChatClient2 {
    private final String host;
    private final int port;

    public ChatClient2(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(){
        // 创建线程组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(eventExecutors)
                    // 设置通道的实现类(反射)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 向pipeline加入一个解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            // 向pipeline加入一个编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            // 加入自定义处理器
                            pipeline.addLast(new ChatClientHandler());
                        }
                    });
            System.out.println("客户端 is ok");


            ChannelFuture sync = bootstrap.connect(host, port).sync();
            // 得到channel
            Channel channel = sync.channel();
            System.out.println("=========="+channel.localAddress()+"============");
            // 客户端需要输入信息，创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String msg = scanner.nextLine();
                // 通过channel发送传到服务器端
                channel.writeAndFlush(msg+"\r\n");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }


    }

    public static void main(String[] args) {
        ChatClient2 jymGroupChatClient = new ChatClient2("127.0.0.1",6668);
        jymGroupChatClient.run();
    }
}
