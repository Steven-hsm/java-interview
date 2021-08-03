package com.hsm.java.netty.netty.chat.handler;

import com.hsm.java.netty.netty.chat.ChatServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * @Classname ChatServerHandler
 * @Description TODO
 * @Date 2021/8/3 11:54
 * @Created by huangsm
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * handlerAdded 表示连接建立，一旦连接，第一个被执行
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        // 将该客户上线的消息，推送给其他在线的客户端,channelGroup.writeAndFlush会将所有的channel遍历发送
        ChatServer.channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"加入聊天\n");
        ChatServer.channelGroup.add(channel);
    }

    /**
     * 表示channel处于活动状态,提示xx上线
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"上线了");
    }

    /**
     * 表示channel处于不活动状态,提示xx下线
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了");

    }

    /**
     * 断开连接,将下线信息提示给当前在线的所有客户端
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ChatServer.channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"离开了\n");
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, final String msg) throws Exception {
        final Channel channel = channelHandlerContext.channel();
        // 根据不同的情况 回送不同的消息
        ChatServer.channelGroup.forEach(ch ->{
            // 不是当前的channel 转发消息
            if(ch!=channel){
                ch.writeAndFlush("[客户]"+channel.remoteAddress()+"发送消息："+msg+"\n");
            } else {
                ch.writeAndFlush("[自己]发送了消息:"+msg+"\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
