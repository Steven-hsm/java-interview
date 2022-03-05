package com.hsm.java.netty.biochat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * @Classname ServerSocket
 * @Description TODO
 * @Date 2021/7/24 15:23
 * @Created by huangsm
 */
public class MyClientSocket {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1",889);
        new Thread(()->{
            try {
                //等待接受服务端的消息
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true){
                    String s = bufferedReader.readLine();
                    System.out.println( s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //一直等待客户端的输入
        while(true){
            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            String readLine = systemIn.readLine();
            bw.write( readLine+ "\n");
            bw.flush();
        }
    }
}
