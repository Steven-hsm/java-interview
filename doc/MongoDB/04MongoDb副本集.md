* 一组Mongodb复制集，就是一组mongod进程，这些进程维护同一个数据集合。复制集提供了数据冗余和高等级的可靠性，这是生产部署的基础。
* 保证数据在生产部署时的冗余和可靠性，通过在不同的机器上保存副本来保证数据的不会因为单点损坏而丢失。能够随时应对数据丢失、机器损坏带来的风险。
* 换一句话来说，还能提高读取能力，用户的读取服务器和写入服务器在不同的地方，而且，由不同的服务器为不同的用户提供服务，提高整个系统的负载。

### 1. 副本集

- 一组维护相同数据集的mongod服务

- 提供冗余和高可用性

- 主从集群和副本集最大的区别就是副本集没有固定的“主节点”；整个集群会选出一个“主节点”，当其挂掉后，又在剩下的从节点中选中其他节点为“主节点”，副本集总有一个活跃点(主、primary)和一个或多个备份节点(从、secondary)。

- 一个副本集最多可以有[50个成员](https://docs.mongodb.com/manual/release-notes/3.0/#replica-sets-max-members)，但仅能有7个可投票成员。

**两种类型**

- 主节点：数据操作的主要连接点，可读写
- 从节点：数据冗余备份节点，可以读或者选举
- 仲裁节点：只用于选举

**三种角色**

- 主要成员；接受所有的写操作
- 副本成员：从主节点通过复制操作以维护相同的数据集，即备份数据，不可写数据
- 仲裁者：不保留任何数据的副本，只具有选举作用
- 如果你的副本+主节点的个数是偶数，建议加一个仲裁者，形成奇数，容易满足大多数的投票

### 2.副本集创建

![img](https://images2017.cnblogs.com/blog/1190037/201801/1190037-20180106145148128-1854811460.png)

1. 主节点

   ```shell
   # 1.创建目录
   mkdir -p /mongodb/replica_sets/myrs_27017/log \ & mkdir -p /mongodb/replica_sets/myrs_27017/data/db
   # 2.新建或修改配置文件
   vi /mongodb/replica_sets/myrs_27017/mongod.conf
   
   systemLog:
       #MongoDB发送所有日志输出的目标指定为文件
       destination: file
       #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
       path: "/mongodb/replica_sets/myrs_27017/log/mongod.log"
       #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
       logAppend: true
   storage:
       #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
       dbPath: "/mongodb/replica_sets/myrs_27017/data/db"
       journal:
           #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
           enabled: true
   processManagement:
       #启用在后台运行mongos或mongod进程的守护进程模式。
       fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
       pidFilePath: "/mongodb/replica_sets/myrs_27017/log/mongod.pid"
   net:
       #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
       bindIp: 0.0.0.0
       #bindIp
       #绑定的端口
       port: 27017
   replication:
       #副本集的名称
       replSetName: myrs
   
   # 3.启动主节点
   mongod -f /mongodb/replica_sets/myrs_27017/mongod.conf
   ```

2. 副本节点

   ```shell
   # 1. 创建目录
   mkdir -p /mongodb/replica_sets/myrs_27018/log \ & mkdir -p /mongodb/replica_sets/myrs_27018/data/db
   # 2. 修改配置文件
   vim /mongodb/replica_sets/myrs_27018/mongod.conf
   
   systemLog:
       #MongoDB发送所有日志输出的目标指定为文件
       destination: file
       #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
       path: "/mongodb/replica_sets/myrs_27018/log/mongod.log"
       #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
       logAppend: true
   storage:
       #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
       dbPath: "/mongodb/replica_sets/myrs_27018/data/db"
       journal:
       #启用或禁用持久性日志以确保数据文件保持有效和可恢复。 、
           enabled: true
   processManagement:
       #启用在后台运行mongos或mongod进程的守护进程模式。
       fork: true
       #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
       pidFilePath: "/mongodb/replica_sets/myrs_27018/log/mongod.pid"
   net:
       #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip
       #bindIpAll: true
       #服务实例绑定的IP
       bindIp: 0.0.0.0
       #bindIp
       #绑定的端口
       port: 27018
   replication:
       #副本集的名称
       replSetName: myrs
   
   # 3.启动副本节点
   mongod -f  /mongodb/replica_sets/myrs_27018/mongod.conf
   ```

3. 仲裁节点

   ```shell
   # 1. 创建目录
   mkdir -p /mongodb/replica_sets/myrs_27019/log \ & mkdir -p /mongodb/replica_sets/myrs_27019/data/db
   # 2. 修改配置文件
   vim /mongodb/replica_sets/myrs_27019/mongod.conf
   
   systemLog:
       #MongoDB发送所有日志输出的目标指定为文件
       destination: file
       #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
       path: "/mongodb/replica_sets/myrs_27019/log/mongod.log"
       #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
       logAppend: true
   storage:
       #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
       dbPath: "/mongodb/replica_sets/myrs_27019/data/db"
       journal:
       #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
           enabled: true
   processManagement:
       #启用在后台运行mongos或mongod进程的守护进程模式。
       fork: true
       #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
       pidFilePath: "/mongodb/replica_sets/myrs_27019/log/mongod.pid"
   net:
       #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip
       #bindIpAll: true
       #服务实例绑定的IP
       bindIp: 0.0.0.0
       #bindIp
       #绑定的端口
       port: 27019
   replication:
       #副本集的名称
       replSetName: myrs
   
   # 3.启动仲裁节点
   mongod -f  /mongodb/replica_sets/myrs_27019/mongod.conf
   ```

   1. 初始化配置副本集和主节点

      ```shell
      # 1. 连接到客户端
      mongo --host=192.168.106.128 --port=27017
      # 2. 使用默认的配置来初始化副本集命令行提示符发生变化，变成了一个从节点角色，此时默认不能读写。稍等片刻，回车，变成主节点。
      rs.initiate()
      # 3. 查看rs配置
      rs.config()
      # 4. 其他命令
      use local
      show collections
      db.system.replset.find()
      # 5. 检查副本集状态
      rs.status()
      # 6. 添加副本节点
      rs.add("192.168.148.129:27018")
      # 7. 添加仲裁从节点
      rs.add("192.168.148.129:27019",true)
      ```

   2. 副本集的读写问题

      - 从节点只是一个备份，不是奴隶节点
      - 从节点没有读取权限，可以增加读的权限，需要设置
      - 设置为奴隶节点，允许在从成员上运行读操作`shell rs.slaveOk() | rs.slaveOk(true) | 最新 rs.secondaryOk()`

   3. 主节点的选举

      1. 触发条件
         - 主节点故障
         - 主节点网络不可达
         - 人工干预
      2. 选举规则
         - 票数高者成为主节点
         - 优先级参数影响很大`shell rs.conf().members[1].priority=2`

   4. 删除所有的节点(恢复初始配置)

      ```shell
      rm -f -r /mongodb/replica_sets/myrs_27017/data/db/*
      rm -f -r /mongodb/replica_sets/myrs_27018/data/db/*
      rm -f -r /mongodb/replica_sets/myrs_27019/data/db/*
      ```

      ### 数据迁移测试

      ##### 1. 将原来的节点变成主节点，单机变成副本集

      ```shell
      1. 原配置信息
      dbpath=/usr/mongodb/data #数据文件存放目录
      logpath=/usr/mongodb/log/mongod.log #日志文件存放地址
      port=27017 #端口
      fork= true #以守护程序的方式启用，即在后台运行
      #auth=true #需要认证。如果放开注释，就必须创建MongoDB的账号，使用账号与密码才可远程访问，第一次安装建议注释
      bind_ip=0.0.0.0 #允许远程访问，或者直接注释，127.0.0.1是只允许本地访问
      
      2. 修改配置信息
      systemLog:
          #MongoDB发送所有日志输出的目标指定为文件
          destination: file
          #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
          path: "/usr/mongodb/log/mongod.log"
          #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
          logAppend: true
      storage:
          #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
          dbPath: "/usr/mongodb/data"
          journal:
          #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
              enabled: true
      processManagement:
          #启用在后台运行mongos或mongod进程的守护进程模式。
          fork: true
          #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
          pidFilePath: "/usr/mongodb/log/mongod.pid"
      net:
          #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip
          #bindIpAll: true
          #服务实例绑定的IP
          bindIp: localhost,192.168.106.128
          #bindIp
          #绑定的端口
          port: 27020
      replication:
          #副本集的名称
          replSetName: myrs
      3. 启动存在数据的mongo
      	mongod -f /opt/mongodb4/mongodb.conf
      4. 这个时候访问是访问不了的，需要初始化副本集，初始化之后就可以访问了
      	rs.initiate()
      5. 其他节点依次启动，并按照副本集的配置配好
      6. 副本集增加可读权限，其他副本集执行rs.secondaryOk() 变成从节点之后可读
      ```

### 3. 注意点

**仲裁节点**

在选举中，只进行投票，不能成为主库。由于arbiter节点没有复制数据，因此这个架构中仅提供一个完整的数据副本。arbiter节点只需要更少的资源，代价是更有限的冗余和容错。

**主节点选举**

复制集通过replSetInitiate命令（或mongo shell的rs.initiate()）进行初始化，初始化后各个成员间开始发送心跳消息，并发起Priamry选举操作，获得『大多数』成员投票支持的节点，会成为Primary，其余节点成为Secondary。

大多数指的是： N/2 + 1

**成员说明**

| **成员**      | **说明**                                                     |
| ------------- | ------------------------------------------------------------ |
| **Secondary** | 正常情况下，复制集的Seconary会参与Primary选举（自身也可能会被选为Primary），并从Primary同步最新写入的数据，以保证与Primary存储相同的数据。Secondary可以提供读服务，增加Secondary节点可以提供复制集的读服务能力，同时提升复制集的可用性。另外，Mongodb支持对复制集的Secondary节点进行灵活的配置，以适应多种场景的需求。 |
| **Arbiter**   | Arbiter节点只参与投票，不能被选为Primary，并且不从Primary同步数据。比如你部署了一个2个节点的复制集，1个Primary，1个Secondary，任意节点宕机，复制集将不能提供服务了（无法选出Primary），这时可以给复制集添加一个Arbiter节点，即使有节点宕机，仍能选出Primary。Arbiter本身不存储数据，是非常轻量级的服务，当复制集成员为偶数时，最好加入一个Arbiter节点，以提升复制集可用性。 |
| **Priority0** | Priority0节点的选举优先级为0，不会被选举为Primary比如你跨机房A、B部署了一个复制集，并且想指定Primary必须在A机房，这时可以将B机房的复制集成员Priority设置为0，这样Primary就一定会是A机房的成员。（注意：如果这样部署，最好将『大多数』节点部署在A机房，否则网络分区时可能无法选出Primary） |
| **Vote0**     | Mongodb 3.0里，复制集成员最多50个，参与Primary选举投票的成员最多7个，其他成员（Vote0）的vote属性必须设置为0，即不参与投票。 |
| **Hidden**    | Hidden节点不能被选为主（Priority为0），并且对Driver不可见。因Hidden节点不会接受Driver的请求，可使用Hidden节点做一些数据备份、离线计算的任务，不会影响复制集的服务。 |
| **Delayed**   | Delayed节点必须是Hidden节点，并且其数据落后与Primary一段时间（可配置，比如1个小时）。因Delayed节点的数据比Primary落后一段时间，当错误或者无效的数据写入Primary时，可通过Delayed节点的数据来恢复到之前的时间点。 |

