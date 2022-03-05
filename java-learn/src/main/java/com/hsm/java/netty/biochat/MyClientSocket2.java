package com.hsm.java.netty.biochat;

import java.io.*;
import java.net.Socket;

/**
 * @Classname ServerSocket
 * @Description TODO
 * @Date 2021/7/24 15:23
 * @Created by huangsm
 */
public class MyClientSocket2 {
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
        while(true){
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            String readLine = systemIn.readLine();
            bw.write( readLine+ "\n");
            bw.flush();
        }

    }
}
