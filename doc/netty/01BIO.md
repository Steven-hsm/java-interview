**Netty** 是一个利用 Java 的高级网络的能力，隐藏了Java背后的复杂性然后提供了一个易于使用的 API 的客户端/服务器框架。

* 高性能 
* 扩展性强
* 在网络发展初期，需要花很多时间来学习 socket 的复杂，寻址等等，在 C socket 库上进行编码，并需要在不同的操作系统上做不同的处理。

* Java 早期版本(1995-2002)介绍了足够的面向对象的糖衣来隐藏一些复杂性，但实现复杂的客户端-服务器协议仍然需要大量的样板代码（和进行大量的监视才能确保他们是对的）。

## BIO

![image-20220305102643433](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220305102643433.png)

```java
public static void main(String[] args) throws Exception {
    //1.ServerSocket 创建并监听端口的连接请求
    ServerSocket serverSocket = new ServerSocket(888);
    //2.accept() 调用阻塞，直到一个连接被建立了。返回一个新的 Socket 用来处理 客户端和服务端的交互
    Socket clientSocket = serverSocket.accept();
    //3.流被创建用于处理 socket 的输入和输出数据。BufferedReader 读取从字符输入流里面的文本。PrintWriter 打印格式化展示的对象读到本文输出流
    InputStream inputStream = clientSocket.getInputStream();
    OutputStream outputStream = clientSocket.getOutputStream();
    //4.处理循环开始 readLine() 阻塞，读取字符串直到最后是换行或者输入终止。
    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    PrintWriter pw = new PrintWriter(outputStream);
    //5.如果客户端发送的是“Done”处理循环退出
    String request,response;
    while ((request = br.readLine()) != null){
        if("Done".equals(request)){
            break;
        }
        //6.执行方法处理请求，返回服务器的响应
        response = "服务器收到消息：" + request;
        //7.响应发回客户端
        pw.println(response);
    }//8.处理循环继续

}
```

这段代码限制每次只能处理一个连接。为了实现多个并行的客户端我们需要分配一个新的 Thread 给每个新的客户端 Socket(当然需要更多的代码)。但考虑使用这种方法来支持大量的同步，长连接。在任何时间点多线程可能处于休眠状态，等待输入或输出数据。这很容易使得资源的大量浪费，对性能产生负面影响

**BIO实现多人聊天**

原生 socket 库同时也包含了非阻塞 I/O 的功能。这使我们能够确定任何一个 socket 中是否有数据准备读或写。我们还可以设置标志，因为读/写调用如果没有数据立即返回；就是说，如果一个阻塞被调用后就会一直阻塞，直到处理完成。

* serverSocket.accept(); 会等待一个新的socket客户端连接

*  BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

   br.readLine() 这里会等待客户端或者服务端响应消息

阻塞的同时需要处理其他的业务，那么就需要开启新的线程去处理业务

**服务端**

```java
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
```

**客户端**

```java
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
```

