### 1. 概念

分片（sharding）是一种跨多台机器分布数据的方法， MongoDB使用分片来支持具有非常大的数据集和高吞吐量操作的部署。分片(sharding)是指将数据拆分，将其分散存在不同的机器上的过程

- 垂直扩展意味着增加单个服务器的容量
- 水平扩展意味着划分系统数据集并加载多个服务器，添加其他服务器以根据需要增加容量

**分片集群包含组件**

- 分片（存储）：每个分片包含分片数据的子集
- mongos（路由）：mongos充当查询路由器，在客户端应用程序和分片集群之间提供接口
- confifig servers（“调度”的配置）：配置服务器存储群集的元数据和配置设置

### 2. 分片架构

两个分片节点副本集（3+3）+一个配置节点副本集（3）+两个路由节点（2），共11个服务节点

![image20210414201829883](C:/Users/Admin/AppData/Roaming/Typora/typora-user-images/image-20210414201829883.png)

**第一套副本集**

```shell
# 1. 新建目录
mkdir -p /mongodb/sharded_cluster/myshardrs01_27018/log \ & mkdir -p /mongodb/sharded_cluster/myshardrs01_27018/data/db 
mkdir -p /mongodb/sharded_cluster/myshardrs01_27118/log \ & mkdir -p /mongodb/sharded_cluster/myshardrs01_27118/data/db 
mkdir -p /mongodb/sharded_cluster/myshardrs01_27218/log \ & mkdir -p /mongodb/sharded_

# 2.修改配置文件
vi /mongodb/sharded_cluster/myshardrs01_27018/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myshardrs01_27018/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myshardrs01_27018/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myshardrs01_27018/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27017
replication:
    #副本集的名称
    replSetName: myshardrs01
sharding:
    #分片角色 
    clusterRole: shardsvr
    
vi /mongodb/sharded_cluster/myshardrs01_27118/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myshardrs01_27118/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myshardrs01_27118/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myshardrs01_27118/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27017
replication:
    #副本集的名称
    replSetName: myshardrs01
sharding:
    #分片角色 
    clusterRole: shardsvr
    
vi /mongodb/sharded_cluster/myshardrs01_27218/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myshardrs01_27218/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myshardrs01_27218/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myshardrs01_27218/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27017
replication:
    #副本集的名称
    replSetName: myshardrs01
sharding:
    #分片角色 
    clusterRole: shardsvr
    
mongod -f /mongodb/sharded_cluster/myshardrs01_27018/mongod.conf
mongod -f /mongodb/sharded_cluster/myshardrs01_27118/mongod.conf
mongod -f /mongodb/sharded_cluster/myshardrs01_27218/mongod.conf

mongo --host 192.168.106.128 --port 27018
rs.initiate()
rs.status()
rs.conf()
rs.add("192.168.106.128:27118")
rs.addArb("192.168.106.128:27218")
rs.conf()
```

**第二套副本**

```shell
# 1. 新建目录
mkdir -p /mongodb/sharded_cluster/myshardrs02_27318/log \ & mkdir -p /mongodb/sharded_cluster/myshardrs02_27318/data/db
mkdir -p /mongodb/sharded_cluster/myshardrs02_27418/log \ & mkdir -p /mongodb/sharded_cluster/myshardrs02_27418/data/db
mkdir -p /mongodb/sharded_cluster/myshardrs02_27518/log \ & mkdir -p /mongodb/sharded_cluster/myshardrs02_27518/data/db

# 2.修改配置文件
vi /mongodb/sharded_cluster/myshardrs02_27318/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myshardrs02_27318/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myshardrs02_27318/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myshardrs02_27318/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27318
replication:
    #副本集的名称
    replSetName: myshardrs02
sharding:
    #分片角色 
    clusterRole: shardsvr
    
vi /mongodb/sharded_cluster/myshardrs02_27418/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myshardrs02_27418/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myshardrs02_27418/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myshardrs02_27418/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27418
replication:
    #副本集的名称
    replSetName: myshardrs02
sharding:
    #分片角色 
    clusterRole: shardsvr
    
vi /mongodb/sharded_cluster/myshardrs02_27518/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myshardrs02_27518/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myshardrs02_27518/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myshardrs02_27518/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27518
replication:
    #副本集的名称
    replSetName: myshardrs02
sharding:
    #分片角色 
    clusterRole: shardsvr
    
mongod -f /mongodb/sharded_cluster/myshardrs02_27318/mongod.conf
mongod -f /mongodb/sharded_cluster/myshardrs02_27418/mongod.conf
mongod -f /mongodb/sharded_cluster/myshardrs02_27518/mongod.conf

mongo --host 192.168.106.128 --port 27318
rs.initiate()
rs.status()
rs.conf()
rs.add("192.168.106.128:27418")
rs.addArb("192.168.106.128:27518")
rs.conf()
```

**配置节点**

```shell
# 1. 新建目录
mkdir -p /mongodb/sharded_cluster/myconfigrs_27019/log \ & mkdir -p /mongodb/sharded_cluster/myconfigrs_27019/data/db 
mkdir -p /mongodb/sharded_cluster/myconfigrs_27119/log \ & mkdir -p /mongodb/sharded_cluster/myconfigrs_27119/data/db
mkdir -p /mongodb/sharded_cluster/myconfigrs_27219/log \ & mkdir -p /mongodb/sharded_cluster/myconfigrs_27219/data/db

# 2.修改配置文件
vi /mongodb/sharded_cluster/myconfigrs_27019/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myconfigrs_27019/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myconfigrs_27019/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myconfigrs_27019/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27019
replication:
    #副本集的名称
    replSetName: myconfigrs
sharding:
    #分片角色 
    clusterRole: configsvr
    
vi /mongodb/sharded_cluster/myconfigrs_27119/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myconfigrs_27119/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myconfigrs_27119/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myconfigrs_27119/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27119
replication:
    #副本集的名称
    replSetName: myconfigrs
sharding:
    #分片角色 
    clusterRole: configsvr
    
vi /mongodb/sharded_cluster/myconfigrs_27219/mongod.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/myconfigrs_27219/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
storage:
    #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
    dbPath: "/mongodb/sharded_cluster/myconfigrs_27219/data/db"
    journal:
        #启用或禁用持久性日志以确保数据文件保持有效和可恢复。
        enabled: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/myconfigrs_27219/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27219
replication:
    #副本集的名称
    replSetName: myconfigrs
sharding:
    #分片角色 
    clusterRole: configsvr
    
mongod -f /mongodb/sharded_cluster/myconfigrs_27019/mongod.conf
mongod -f /mongodb/sharded_cluster/myconfigrs_27119/mongod.conf
mongod -f /mongodb/sharded_cluster/myconfigrs_27219/mongod.conf

mongo --host 192.168.106.128 --port 27019
rs.initiate()
rs.status()
rs.conf()
rs.add("192.168.106.128:27119")
rs.add("192.168.106.128:27219")
rs.conf()
```

**路由节点**

```shell
mkdir -p /mongodb/sharded_cluster/mymongos_27017/log
vi /mongodb/sharded_cluster/mymongos_27017/mongos.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/mymongos_27017/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/mymongos_27017/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27017
sharding:
    #分片角色 
    configDB: myconfigrs/192.168.106.128:27019,192.168.106.128:27119,192.168.106.128:27219
    
mongos -f /mongodb/sharded_cluster/mymongos_27017/mongos.conf
mongo --host 180.76.159.126 --port 27017

sh.addShard("IP:Port")
sh.addShard("myshardrs01/192.168.106.128:27018,192.168.106.128:27118,192.168.106.128:27218")
sh.status()
sh.addShard("myshardrs02/192.168.106.128:27318,192.168.106.128:27418,192.168.106.128:27518")
sh.status()
sh.enableSharding("articledb")
sh.shardCollection(namespace, key, unique)	


mkdir -p /mongodb/sharded_cluster/mymongos_27117/log
vi /mongodb/sharded_cluster/mymongos_27117/mongos.conf

systemLog:
    #MongoDB发送所有日志输出的目标指定为文件
    destination: file
    #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
    path: "/mongodb/sharded_cluster/mymongos_27117/log/mongod.log"
    #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
    logAppend: true
processManagement:
    #启用在后台运行mongos或mongod进程的守护进程模式。
    fork: true #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID
    pidFilePath: "/mongodb/sharded_cluster/mymongos_27117/log/mongod.pid"
net:
    #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip #bindIpAll: true #服务实例绑定的IP
    bindIp: localhost,192.168.106.128
    #bindIp
    #绑定的端口
    port: 27117
sharding:
    #分片角色 
    configDB: myconfigrs/192.168.106.128:27019,192.168.106.128:27119,192.168.106.128:27219
    
mongos -f /mongodb/sharded_cluster/mymongos_27117/mongos.conf
```