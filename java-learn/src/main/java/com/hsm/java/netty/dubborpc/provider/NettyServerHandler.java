package com.hsm.java.netty.dubborpc.provider;

import com.hsm.java.netty.dubborpc.consumer.ClientBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Classname NettyServerHandler
 * @Description TODO
 * @Date 2021/8/10 20:10
 * @Created by huangsm
 */
public class NettyServerHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器的api 时，我们需要定义一个协议
        //比如我们要求 每次发消息是都必须以某个字符串开头 "HelloService#hello#你好"
        if (msg.toString().startsWith(ClientBootstrap.SERVICE_NAME + "#" + ClientBootstrap.METHOD_NAME_HELLO)) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
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
