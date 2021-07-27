package com.hsm.java.netty.pre01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname MyserverSocket
 * @Description
 * @Date 2021/7/24 15:07
 * @Created by huangsm
 */
public class MyServerSocket {

    public static void main(String[] args) throws Exception {
        //创建并监听端口的连接请求
        ServerSocket serverSocket = new ServerSocket(888);

        //这里会阻塞住，会等一个客户端socket连接过来
        Socket clientSocket = serverSocket.accept();

        //获取socket输入流
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        //这里会阻塞，一直尝试去获取客户端的数据
        String request = br.readLine();
        String response = "";
        //请求不为Done就一直保持和socket通信
        while (request != null){
            if("Done".equals(request)){
                break;
            }
            System.out.println("收到客户端的消息：" + request);


            //接受到客户端消息后，响应消息给客户端
            response = "服务器响应消息：" + request;
            //7.响应发回客户端
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(outputStream);
            pw.println(response);
            pw.flush();

            //给客户端发送消息后，等待客户端的消息
            request = br.readLine();
        }//8.处理循环继续

    }
}
