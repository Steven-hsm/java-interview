package com.hsm.java.netty.pre01;

import java.io.*;
import java.net.Socket;

/**
 * @Classname MyClinetSocket
 * @Description TODO
 * @Date 2021/7/24 15:17
 * @Created by huangsm
 */
public class MyClinetSocket {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1",888);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String readLine = "";
        while (!"Done".equals(readLine)){
            //给服务器端发送消息
            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            readLine = systemIn.readLine();
            bw.write( readLine+ "\n");
            bw.flush();

            //等待接受服务端的消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = bufferedReader.readLine();
            System.out.println("收到服务消息:" +  s);
        }
    }
}
