Redis集群是一个由**多个主从节点群**组成的分布式服务集群，它具有**复制、高可用和分片**特性。Redis集群不需要sentinel哨兵也能完成节点移除和故障转移的功能。需要将每个节点设置成集群模式，这种集群模式没有中心节点，可水平扩展，据官方文档称可以线性扩展到上万个节点(官方推荐不超过1000个节点)。redis集群的性能和高可用性均优于之前版本的哨兵模式，且集群配置非常简单。

## **Redis集群的优点**：

（1）Redis集群有多个master，可以**减小访问瞬断问题的影响**；

　　若集群中有一个master挂了，正好需要向这个master写数据，这个操作需要等待一下；但是向其他master节点写数据是不受影响的。

（2）Redis集群有多个master，可以提供更高的并发量；　　

（3）Redis集群可以分片存储，这样就可以存储更多的数据；

## Redis集群的搭建(三主三从)

需要六个单独配置文件，配置文件修改相应的端口

```shell
[root@localhost cluster]# cat cluster1/8000/redis8000.conf
include /root/conf/redis/cluster/redis.conf
pidfile ./redis_8000.pid
port 8000
dbfilename dump8000.rdb
cluster-enabled yes
cluster-config-file nodes-8000.conf
cluster-node-timeout 5000
appendonly yes
requirepass redis-pw
masterauth redis-pw
dir /root/conf/redis/cluster/cluster1/8000
```

复制到对应目录下，然后修改配置

```shell
:%s/8000/8001/g
```

启动六个服务

```shell
[root@localhost cluster]# redis-server /root/conf/redis/cluster/cluster1/8000/redis8000.conf
[root@localhost cluster]# redis-server /root/conf/redis/cluster/cluster1/8001/redis8001.conf
[root@localhost cluster]# redis-server /root/conf/redis/cluster/cluster2/8010/redis8010.conf
[root@localhost cluster]# redis-server /root/conf/redis/cluster/cluster2/8011/redis8011.conf
[root@localhost cluster]# redis-server /root/conf/redis/cluster/cluster3/8020/redis8020.conf
[root@localhost cluster]# redis-server /root/conf/redis/cluster/cluster3/8021/redis8021.conf
```

搭建集群

```shell
redis-cli -a redis-pw --cluster create --cluster-replicas 1 192.168.148.130:8000 192.168.148.130:8001 192.168.148.130:8010  192.168.148.130:8011 192.168.148.130:8020 192.168.148.130:8021
```

* -a 集群密码
* --cluster-replicas 1 集群副本数量

客户端连接命令：src/redis‐cli --cluster help

```shell
create：创建一个集群环境host1:port1 ... hostN:portN
call：可以执行redis命令
add-node：将一个节点添加到集群里，第一个参数为新节点的ip:port，第二个参数为集群中任意一个已经存在的节点的ip:port
del-node：移除一个节点
reshard：重新分片
check：检查集群状态

#‐a表示服务端密码；‐c表示集群模式；-h指定ip地址；-p表示端口号
redis-cli -a redis-pw -c -h 192.168.1.1 -p 8001

192.168.148.130:8000>
cluster info # 集群信息
cluster nodes # 查看集群节点信息
```

**注意点**

* 杀掉所有的redis服务

  ```shell
  ps -ef|grep redis|grep -v grep|cut -c 9-15|xargs kill -9
  ```

* 如果服务不同可以尝试关闭防火墙

  ```shell
  systemctl stop firewalld # 临时关闭防火墙
  systemctl disable firewalld # 禁止开机启动
  ```

## Redis集群原理分析

Redis Cluster 将所有数据划分为 **16384** 个 slots（槽位），每个节点负责其中一部分槽位。槽位的信息存储于每个节点中。只有master节点会被分配槽位，slave节点不会分配槽位。

当Redis Cluster 的客户端来连接集群时，它也会得到一份集群的槽位配置信息，并将其缓存在客户端本地。这样当客户端要查找某个 key 时，可以直接定位到目标节点。同时因为槽位的信息可能会存在客户端与服务器不一致的情况，还需要纠正机制来实现槽位信息的校验调整。

  