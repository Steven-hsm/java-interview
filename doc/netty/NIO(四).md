多人聊天客户端

**服务端**

```java
public class Server {
    private ServerSocketChannel servSocketChannel;
    private Selector selector;

    /**
     * 初始化服务器
     * */
    public Server(){

        try {
            servSocketChannel = ServerSocketChannel.open();
            servSocketChannel.socket().bind(new InetSocketAddress(7000));
            selector = Selector.open();
            //开启非阻塞模式
            servSocketChannel.configureBlocking(false);
            //注册serverSocketChannel
            servSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听连接
     * */
    public void listen()  {
        while (true) {
            Iterator<SelectionKey> keyIterator = null;
            try {
                //这里我们等待1秒，如果没有事件发生, 返回
                if(selector.select(1000) == 0) { //没有事件发生

                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                keyIterator = selectionKeys.iterator();
                if(keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    //客户端连接
                    if(key.isAcceptable()) {
                        SocketChannel socketChannel = servSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(socketChannel.getRemoteAddress() + "上线");
                    }
                    //读已就绪
                    if(key.isReadable()) {
                        readData(key);
                    }else{
                        System.out.println("等待处理");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //当前的key 删除，防止重复处理
            keyIterator.remove();
        }
    }
    public void readData(SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel)selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            int read = channel.read(byteBuffer);
            while (read > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println(msg);
                //转发
                sendInfoToOtherClients(msg, channel);
                byteBuffer.clear();
                read = channel.read(byteBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            selectionKey.cancel();
        }
    }

    private void sendInfoToOtherClients(String msg, SocketChannel channel) {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            SelectableChannel targetChannel = key.channel();
            if(targetChannel instanceof SocketChannel && targetChannel != channel) {
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                try {
                    socketChannel.write(wrap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }

}
```

**客户端**

```java
public class Client {
    private SocketChannel socketChannel;
    private final String IP = "127.0.0.1";
    private Selector selector;
    private final int port = 7000;
    private String username;

    public Client() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(IP, port));
            selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + " is ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //读取从服务器端回复的消息
    public void readInfo() {

        try {

            int readChannels = selector.select();
            if(readChannels > 0) {//有可以用的通道

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();
                    if(key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove(); //删除当前的selectionKey, 防止重复操作
            } else {
                //System.out.println("没有可以用的通道...");

            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //向服务器发送消息
    public void sendInfo(String info) {

        info = username + " 说：" + info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        //启动我们客户端
        Client chatClient = new Client();

        //启动一个线程, 每个3秒，读取从服务器发送数据
        new Thread() {
            public void run() {

                while (true) {
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }
}
```

