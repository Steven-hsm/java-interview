自动选举主机，是对主从复制，主机宕机的一种优化。主从切换技术的方法是：当主服务器宕机后，需要手动把一台从服务器切换为主服务器，这就需要人工干预，费事费力，还会造成一段时间内服务不可用。

哨兵模式是一种特殊的模式，首先Redis提供了哨兵的命令，哨兵是一个独立的进程，作为进程，它会独立运行。其原理是哨兵通过发送命令，等待Redis服务器响应，从而监控运行的多个Redis实例。

作用：

* 通过发送命令，让Redis服务器返回监控其运行状态，包括主服务器和从服务器
* 当哨兵监测到master宕机，会自动将slave切换成master，然后通过发布订阅模式通知其他的从服务器，修改配置文件，让它们切换主机。
* 然而一个哨兵进程对Redis服务器进行监控，可能会出现问题，为此，我们可以使用多个哨兵进行监控。各个哨兵之间还会进行监控，这样就形成了多哨兵模式。

假设主服务器宕机，哨兵1先检测到这个结果，系统并不会马上进行failover过程，仅仅是哨兵1主观的认为主服务器不可用，这个现象成为主观下线。当后面的哨兵也检测到主服务器不可用，并且数量达到一定值时，那么哨兵之间就会进行一次投票，投票的结果由一个哨兵发起，进行failover[故障转移]操作。切换成功后，就会通过发布订阅模式，让各个哨兵把自己监控的从服务器实现切换主机，这个过程称为客观下线。

1. 配置哨兵

   sentinel.conf

   ```shell
   sentinel monitor myredis 127.0.0.1 6379 1
   ```

2. 启动哨兵

   ```shell
   redis-sentinel sentinel.conf #启动哨兵
   ```

3. 原理

   如果Master 节点断开了，这个时候就会从从机中随机选择一个服务器！

   如果主机此时回来了，只能归并到新的主机下，当做从机，这就是哨兵模式的规则！

4. 优点

   * 哨兵集群，基于主从复制模式，所有的主从配置优点，它全有
   * 主从可以切换，故障可以转移，系统的可用性就会更好
   * 哨兵模式就是主从模式的升级，手动到自动，更加健壮！

5. 缺点

   * Redis 不好啊在线扩容的，集群容量一旦到达上限，在线扩容就十分麻烦！

   * 实现哨兵模式的配置其实是很麻烦的，里面有很多选择！

     ```shell
     #哨兵sentinel实例运行的端口默认26379 
     port 26379
     #quorum配置多少个sentinel哨兵统一认为master主节点失联那么这时客观上认为主节点失联了
     #sentinelmonitor<master-name><ip><redis-port><quorum>
     sentinel monitor mymaster 127.0.0.1 63792
     #当在Redis实例中开启了requirepassfoobared授权密码这样所有连接Redis实例的客户端都要提供密码
     #设置哨兵sentinel连接主从的密码注意必须为主从设置一样的验证密码
     #sentinelauth-pass<master-name><password>
     sentinel auth -pass mymasterMySUPER--secret-0123passw0rd
     #指定多少毫秒之后主节点没有应答哨兵sentinel此时哨兵主观上认为主节点下线默认30秒
     #sentineldown-after-milliseconds<master-name><milliseconds>
     sentineldown-after-millisecondsmymaster30000
     #这个配置项指定了在发生failover主备切换时最多可以有多少个slave同时对新的master进行同步，这个数字越小，完成failover所需的时间就越长，但是如果这个数字越大，就意味着越多的slave因为replication而不可用。可以通过将这个值设为1来保证每次只有一个slave处于不能处理命令请求的状态。
     #sentinelparallel-syncs<master-name><numslaves>
     sentinel parallel-syncs mymaster 1
     #故障转移的超时时间failover-timeout可以用在以下这些方面：
     #1.同一个sentinel对同一个master两次failover之间的间隔时间。
     #2.当一个slave从一个错误的master那里同步数据开始计算时间。直到slave被纠正为向正确的master那里同步数据时。
     #3.当想要取消一个正在进行的failover所需要的时间。 
     #4.当进行failover时，配置所有slaves指向新的master所需的最大时间。不过，即使过了这个超时，slaves依然会被正确配置为指向master，但是就不按parallel-syncs所配置的规则来了
     #默认三分钟
     #sentinelfailover-timeout<master-name><milliseconds>
     sentinelfailover-timeoutmymaster180000
     #通知脚本
     sentinelnotification-scriptmymaster/var/redis/notify.sh
     ```