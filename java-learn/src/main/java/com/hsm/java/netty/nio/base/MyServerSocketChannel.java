package com.hsm.java.netty.pre01.nio.base;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class MyServerSocketChannel {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        while(true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
        }
    }
}
