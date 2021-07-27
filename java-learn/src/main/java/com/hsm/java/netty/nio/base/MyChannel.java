package com.hsm.java.netty.pre01.nio.base;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname MyChannel
 * @Description TODO
 * @Date 2021/7/26 16:24
 * @Created by huangsm
 */
public class MyChannel {
    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:\\caseProblem.ftl", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
