主从复制，是指将一台Redis服务器的数据，复制到其他的Redis服务器。前者称为主节点(master/leader)，后者称为从节点(slave/follower)；数据的复制是单向的，只能由主节点到从节点。Master以写为主，Slave 以读为主。一个主节点可以有多个从节点(或没有从节点)，但一个从节点只能有一个主节点

作用：

* 数据冗余：主从复制实现了数据的热备份，是持久化之外的一种数据冗余方式。
* 故障恢复：当主节点出现问题时，可以由从节点提供服务，实现快速的故障恢复；实际上是一种服务的冗余
* 负载均衡：在主从复制的基础上，配合读写分离，可以由主节点提供写服务，由从节点提供读服务。即写Redis数据时应用连接主节点，读Redis数据时应用连接从节点），分担服务器负载；尤其是在写少读多的场景下，通过多个从节点分担读负载，可以大大提高Redis服务器的并发量。
* 高可用（集群）基石：除了上述作用以外，主从复制还是哨兵和集群能够实施的基础，因此说主从复制是Redis高可用的基础。

单机redis的缺点：

* 从结构上，单个Redis服务器会发生单点故障，并且一台服务器需要处理所有的请求负载，压力较大；
* 从容量上，单个Redis服务器内存容量有限，就算一台Redis服务器内存容量为256G，也不能将所有内存用作Redis存储内存，一般来说，单台Redis最大使用内存不应该超过20G

主从复制，读写分离！  80% 的情况下都是在进行读操作！减缓服务器的压力！架构中经常使用！一主二从！

1. 编写三个配置文件

  redis6380.conf

  ```shell
  include ./redis.conf
  pidfile ./redis_6380.pid
  port 6380
  dbfilename dump6380.rdb
  ```

  redis6381.conf

  ```shell
  include ./redis.conf
  pidfile ./redis_6381.pid
  port 6381
  dbfilename dump6381.rdb
  ```

  redis6382.conf

  ```shell
  include ./redis.conf
  pidfile ./redis_6382.pid
  port 6382
  dbfilename dump6382.rdb
  ```

2. 依次启动

   ```shell
   redis-server redis6380conf
   redis-server redis6381conf
   redis-server redis6382.conf
   ```

3. redis启动时默认都是主库，只需要把主库配置成从库就好了

   ```shell
   slaveof  <ip> <port> # 配置为某个主机的从库
   slaveof 127.0.0.1 6380 # 连接81,82客户端后执行，将其配置为80的从库，也可以直接在配置文件中指定主库
   ```

4. 查看配置

   ```shell
   127.0.0.1:6380> info replication
   # Replication
   role:master
   connected_slaves:2
   slave0:ip=127.0.0.1,port=6381,state=online,offset=476,lag=1
   slave1:ip=127.0.0.1,port=6382,state=online,offset=476,lag=1
   master_failover_state:no-failover
   master_replid:fdc8bb26197a9e2f00c17c7fcff5ea8974a5faf8
   master_replid2:0000000000000000000000000000000000000000
   master_repl_offset:476
   second_repl_offset:-1
   repl_backlog_active:1
   repl_backlog_size:1048576
   repl_backlog_first_byte_offset:1
   repl_backlog_histlen:476
   ```

* 主机可以读写，从机只能读
* 从机第一次连接到主机会进行全量复制，后续的写操作都会是增量复制
* 如果主机宕机，需要手动设置主机，手动设置主机之后，也需要主动将从机设置为新主机的从机