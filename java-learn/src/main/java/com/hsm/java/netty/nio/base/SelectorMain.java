package com.hsm.java.netty.pre01.nio.base;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorMain {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) continue;
            Set selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {

                } else if (key.isConnectable()) {

                } else if (key.isReadable()) {

                } else if (key.isWritable()) {

                }
                keyIterator.remove();
            }
        }
    }
}
