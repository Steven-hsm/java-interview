package com.hsm.java.netty.pre01.nio.base;

import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Classname MyChannel
 * @Description TODO
 * @Date 2021/7/26 16:24
 * @Created by huangsm
 */
public class MyChannelWrite {
    public static void main(String[] args) throws Exception {
        FileOutputStream aFile = new FileOutputStream("D:\\1.txt");
        FileChannel outChannel = aFile.getChannel();

        /*ByteBuffer buf = ByteBuffer.allocate(48);
        buf.put("this is a test".getBytes(StandardCharsets.UTF_8));*/
        ByteBuffer buf = ByteBuffer.wrap("this is a test".getBytes(StandardCharsets.UTF_8));
        buf.flip();
        outChannel.write(buf);
        buf.clear();

        outChannel.close();
        aFile.close();
    }
}
