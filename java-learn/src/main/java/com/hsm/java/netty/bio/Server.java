package com.hsm.java.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname Server
 * @Description TODO
 * @Date 2021/7/24 16:57
 * @Created by huangsm
 */
@Slf4j
public class Server {

    private static Set<Socket> socketSet = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ServerSocket ss = new ServerSocket(8888);
        log.info("服务器端监听8888端口，等待客户端连接");
        while (true) {
            Socket socket = ss.accept();
            socketSet.add(socket);
            executor.submit(() -> {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true){
                        String line = bufferedReader.readLine();
                        for (Socket otherClientSocket : socketSet) {
                            if(!otherClientSocket.equals(socket)){
                                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(otherClientSocket.getOutputStream()));
                                bufferedWriter.write(Thread.currentThread().getName() + ":" + line + "\n");
                                bufferedWriter.flush();
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("获取数据异常", e);
                }

            });
        }

    }
}
