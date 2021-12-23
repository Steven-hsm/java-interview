Zookeeper 集群模式一共有三种类型的角色

* **Leader**: 处理所有的事务请求（写请求），可以处理读请求，集群中只能有一个Leader
* **Follower**：只能处理读请求，同时作为 Leader的候选节点，即如果Leader宕机，Follower节点 要参与到新的Leader选举中，有可能成为新的Leader节点。 
* **Observer**：只能处理读请求。不能参与选举 

**Zookeeper集群模式安装**

1. 配置JAVA环境，检验环境：保证是jdk7 及以上即可

2. 下载并解压zookeeper 

   ```shell
   wget https://downloads.apache.org/zookeeper/stable/apache-zookeeper-3.6.3-bin.tar.gz
   tar -zxvf apache-zookeeper-3.6.3-bin.tar.gz
   cd apache-zookeeper-3.6.3-bin
   ```

3. 重命名配置文件

   ```shell
   cp conf/zoo_sample.cfg conf/zoo‐1.cfg
   
   vim conf/zoo‐1.cfg 
   dataDir=/usr/local/data/zookeeper‐1
   clientPort=2181 
   server.1=127.0.0.1:2001:3001:participant
   server.2=127.0.0.1:2002:3002:participant
   server.3=127.0.0.1:2003:3003:participant
   server.4=127.0.0.1:2004:3004:observer
   ```

   **配置说明** 

   * tickTime：用于配置Zookeeper中最小时间单位的长度，很多运行时的时间间隔都是 使用tickTime的倍数来表示的。 
   * initLimit：该参数用于配置Leader服务器等待Follower启动，并完成数据同步的时 间。Follower服务器再启动过程中，会与Leader建立连接并完成数据的同步，从而确定自 己对外提供服务的起始状态。Leader服务器允许Follower再initLimit 时间内完成这个工 作。
   * syncLimit：Leader 与Follower心跳检测的最大延时时间 
   * dataDir：顾名思义就是 Zookeeper 保存数据的目录，默认情况下，Zookeeper 将 写数据的日志文件也保存在这个目录里。 
   * clientPort：这个端口就是客户端连接 Zookeeper 服务器的端口，Zookeeper 会监 听这个端口，接受客户端的访问请求。 
   * server.A=B：C：D：E 其中 A 是一个数字，表示这个是第几号服务器；B 是这个服 务器的 ip 地址；C 表示的是这个服务器与集群中的 Leader 服务器交换信息的端口；D 表示的是万一集群中的 Leader 服务器挂了，需要一个端口来重新进行选举，选出一个新 的 Leader，而这个端口就是用来执行选举时服务器相互通信的端口。如果是伪集群的配置方式，由于 B 都是一样，所以不同的 Zookeeper 实例通信端口号不能一样，所以要给 它们分配不同的端口号。如果需要通过添加不参与集群选举以及事务请求的过半机制的 
   * Observer节点，可以在E的位置，添加observer标识。 

4. 再从zoo-1.cfg复制三个配置文件zoo-2.cfg，zoo-3.cfg和zoo-4.cfg，只需修改 dataDir和clientPort不同即可 

   ```shell
    cp conf/zoo.cfg conf/zoo-1.cfg
    cp conf/zoo-1.cfg conf/zoo-2.cfg
    cp conf/zoo-1.cfg conf/zoo-3.cfg
    cp conf/zoo-1.cfg conf/zoo-4.cfg
    #修改以下参数
    dataDir=/usr/local/data/zookeeper‐1
    clientPort=2181 
   ```

5. 创建数据目录

   ```shell
   mkdir -p /usr/local/data/zookeeper‐1
   echo 1 >/usr/local/data/zookeeper‐1/myid 
   mkdir -p /usr/local/data/zookeeper‐2
   echo 2 >/usr/local/data/zookeeper‐2/myid 
   mkdir -p /usr/local/data/zookeeper‐3
   echo 3 >/usr/local/data/zookeeper‐3/myid 
   mkdir -p /usr/local/data/zookeeper‐4
   echo 4 >/usr/local/data/zookeeper‐4/myid 
   ```

6. 启动zookeeper实例

   ```shell
   bin/zkServer.sh start conf/zoo-1.cfg
   bin/zkServer.sh start conf/zoo-2.cfg
   bin/zkServer.sh start conf/zoo-3.cfg
   bin/zkServer.sh start conf/zoo-4.cfg
   ```

7. 查看集群状态

   ```shell
   bin/zkServer.sh status conf/zoo-1.cfg
   ```

   