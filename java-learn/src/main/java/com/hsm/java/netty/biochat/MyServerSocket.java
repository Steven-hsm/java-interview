package com.hsm.java.netty.biochat;

import com.sun.media.jfxmedia.logging.Logger;

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
public class MyServerSocket {
    //连接的客户端
    private static Set<Socket> socketSet = new HashSet<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(889);
        int index = 0;
        while(true){
            index ++ ;
            //这里会阻塞住，
            Socket socket = serverSocket.accept();
            socketSet.add(socket);

            //对每个socket的输入都开启一个线程进行监听
            new Thread(() ->{
                try{
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //监听客户端的请求参数
                    while(true){
                        try{
                            String message = bufferedReader.readLine();
                            System.out.println(Thread.currentThread().getName() + ":" + message);
                            //遍历客户端，将其中一个客户端的消息发送给其他客户端
                            for (Socket socket1 : socketSet) {
                                if(!socket1.equals(socket)){
                                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
                                    bufferedWriter.write(Thread.currentThread().getName() + ":" + message + "\n");
                                    bufferedWriter.flush();
                                }
                            }
                        }catch (Exception e){
                            System.out.println( Thread.currentThread().getName() + "可能退出了,将它移除掉");
                            socketSet.remove(socket);
                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            },"用户" + index).start();
        }
    }
}
