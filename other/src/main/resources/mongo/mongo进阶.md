### 1. 副本集

* 一组维护相同数据集的mongod服务

* 提供冗余和高可用性

* 主从集群和副本集最大的区别就是副本集没有固定的“主节点”；整个集群会选出一个“主节点”，当其挂

  掉后，又在剩下的从节点中选中其他节点为“主节点”，副本集总有一个活跃点(主、primary)和一个或多

  个备份节点(从、secondary)。

**两种类型**

* 主节点：数据操作的主要连接点，可读写
* 从节点：数据冗余备份节点，可以读或者选举

**三种角色**

* 主要成员；接受所有得写操作
* 副本成员：从主节点通过复制操作以维护相同的数据集，即备份数据，不可写数据
* 仲裁者：不保留任何数据的副本，只具有选举作用
* 如果你的副本+主节点的个数是偶数，建议加一个仲裁者，形成奇数，容易满足大多数的投票

### 2.副本集创建

![副本集](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210412201107674.png)

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
       bindIp: localhost,192.168.106.128
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
       bindIp: localhost,192.168.106.128
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
       bindIp: localhost,192.168.106.128
       #bindIp
       #绑定的端口
       port: 27019
   replication:
       #副本集的名称
       replSetName: myrs

   # 3.启动仲裁节点
   mongod -f  /mongodb/replica_sets/myrs_27019/mongod.conf
   ```

   4. 初始化配置副本集和主节点

      ```shell
      # 1. 连接到客户端
      mongo --host=192.168.106.128 --port=27017
      # 2. 使用默认的配置来初始化副本集命令行提示符发生变化，变成了一个从节点角色，此时默认不能读写。稍等片刻，回车，变成主节点。
      rs.initiate(configuration)
      # 3. 查看rs配置
      rs.config()
      # 4. 其他命令
      use local
      show collections
      db.system.replset.find()
      # 5. 检查副本集状态
      rs.status()
      # 6. 添加副本节点
      rs.add("192.168.106.128:27018", arbiterOnly)
      # 7. 添加仲裁从节点
      rs.addArb("192.168.106.128:27019")
      ```

   5. 副本集的读写问题

      * 从节点只是一个备份，不是奴隶节点
      * 从节点没有读取权限，可以增加读的权限，需要设置
      * 设置为奴隶节点，允许在从成员上运行读操作```shell rs.slaveOk() | rs.slaveOk(true) | 最新 rs.secondaryOk()```

   6. 主节点的选举

      1. 触发条件
         * 主节点故障
         * 主节点网络不可达
         * 人工干预
      2. 选举规则
         * 票数高者成为主节点
         * 优先级参数影响很大```shell rs.conf().members[1].priority=2 ```
      3.

