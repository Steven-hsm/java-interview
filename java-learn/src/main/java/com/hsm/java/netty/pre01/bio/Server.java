package com.hsm.java.netty.pre01.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname Server
 * @Description TODO
 * @Date 2021/7/24 16:57
 * @Created by huangsm
 */
public class Server{
        public static void main(String[] args) {
            try {
                ServerSocket ss = new ServerSocket(8888);
                System.out.println("启动服务器....");
                Socket s = ss.accept();
                System.out.println("客户端:"+s.getInetAddress().getLocalHost()+"已连接到服务器");

                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                //读取客户端发送来的消息
                String mess = br.readLine();
                System.out.println("客户端："+mess);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                bw.write(mess+"\n");
                bw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
