[博客地址](https://www.cnblogs.com/clsn/p/8214345.html#auto-id-22)

分片集群，一般建立在复制集（副本集）之上

###  1 MongoDB复制集

#### 1.1 简介

一组Mongodb复制集，就是一组mongod进程，这些进程维护同一个数据集合。复制集提供了**数据冗余**和**高等级的可靠性**，这是生产部署的基础。

#### 1.2 目的

保证数据在生产部署时的**冗余**和**可靠性**，通过在不同的机器上保存副本来保证数据的不会因为单点损坏而丢失。能够随时应对**数据丢失**、**机器损坏**带来的风险。

换一句话来说，还能提高读取能力，用户的读取服务器和写入服务器在不同的地方，而且，由不同的服务器为不同的用户提供服务，提高整个系统的负载。

#### 1.3 介绍

一组复制集就是一组mongod实例掌管同一个数据集，实例可以在不同的机器上面。实例中包含一个主导，接受客户端所有的写入操作，其他都是副本实例，从主服务器上获得数据并保持同步。

主服务器很重要，包含了所有的改变操作（写）的日志。但是副本服务器集群包含有所有的主服务器数据，因此当主服务器挂掉了，就会在副本服务器上重新选取一个成为主服务器。

每个复制集还有一个仲裁者，仲裁者不存储数据，只是负责通过心跳包来确认集群中集合的数量，并在主服务器选举的时候作为仲裁决定结果。

#### 1.4 基本架构

基本的架构由3台服务器组成，一个三成员的复制集，由三个有数据，或者两个有数据，一个作为仲裁者。一般选在的机构都是基数台，若为偶数，会增加仲裁节点

##### 1.4.1 三个存储数据的复制集

一个主库，两个从库

![image-20210508142728471](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210508142728471.png)

当主机宕机之后，两个主库会进行竞选，其中一个就会变成从库，原主库回复后，作为从库假如到当前的复制集

![image-20210508142739728](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210508142739728.png)

##### 1.4.2 存在选举（仲裁）节点

一个主库，一个从库，一个仲裁节点

当主库宕机时，将会 选在从库成为主库，主库修复后，将其加入到现有的复制集群中

#### 1.5 主节点选举

复制集通过replSetInitiate命令（或者mongoshell的rs.initiate()）进行初始化，初始化后各个成员间开始发送心跳消息，并发起Priamry选举操作，获得『大多数』成员投票支持的节点，会成为Primary，其余节点成为Secondary。

#### 1.6 配置副本集

1. 创建目录

   ```shell
   for  i in 28017 28018 28019 28020
       do 
         mkdir -p /mongodb/$i/conf  
         mkdir -p /mongodb/$i/data  
         mkdir -p /mongodb/$i/log
   done 
   ```

2. 编辑实例配置文件

   ```shell
   cat >>/mongodb/28017/conf/mongod.conf<<'EOF'
   systemLog:
     destination: file
     path: /mongodb/28017/log/mongodb.log
     logAppend: true
   storage:
     journal:
       enabled: true
     dbPath: /mongodb/28017/data
     directoryPerDB: true
     #engine: wiredTiger
     wiredTiger:
       engineConfig:
         # cacheSizeGB: 1
         directoryForIndexes: true
       collectionConfig:
         blockCompressor: zlib
       indexConfig:
         prefixCompression: true
   processManagement:
     fork: true
   net:
     port: 28017
   replication:
     oplogSizeMB: 2048
     replSetName: my_repl
   EOF
   ```

3. 复制配置文件

   ```shell
   for i in 28018 28019 28020
     do  
      \cp  /mongodb/28017/conf/mongod.conf  /mongodb/$i/conf/
   done
   ```

4. 修改配置文件

   ```shell
   for i in 28018 28019 28020
     do 
       sed  -i  "s#28017#$i#g" /mongodb/$i/conf/mongod.conf
   done
   ```

5. 启动服务

   ```shell
   for i in 28017 28018 28019 28020
     do  
       mongod -f /mongodb/$i/conf/mongod.conf 
   done
   ```

6. 关闭服务

   ```shell
   for i in 28017 28018 28019 28020
      do  
        mongod --shutdown  -f /mongodb/$i/conf/mongod.conf 
   done
   ```

7. 配置副本集

   ```shell
   mongo --port 28017
   
   config = {_id: 'my_repl', members: [
                             {_id: 0, host: '10.0.0.152:28017'},
                             {_id: 1, host: '10.0.0.152:28018'},
                             {_id: 2, host: '10.0.0.152:28019'}]
             }
   ```

8. 初始化副本集

   ```shell
   rs.initiate(config)
   ```

   

### 2. 分片技术

　分片（sharding）是MongoDB用来将大型集合分割到不同服务器（或者说一个集群）上所采用的方法。尽管分片起源于关系型数据库分区，但MongoDB分片完全又是另一回事。

　　和MySQL分区方案相比，MongoDB的最大区别在于它几乎能自动完成所有事情，只要告诉MongoDB要分配数据，它就能自动维护数据在不同服务器之间的均衡。

#### 2.1 目的

高数据量和吞吐量的数据库应用会对单机的性能造成较大压力,大的查询量会将单机的CPU耗尽,大的数据量对单机的存储压力较大,最终会耗尽系统的内存而将压力转移到磁盘IO上。

　　为了解决这些问题,有两个基本的方法: 垂直扩展和水平扩展。

　　　　垂直扩展：增加更多的CPU和存储资源来扩展容量。

　　　　水平扩展：将数据集分布在多个服务器上。水平扩展即分片。

#### 2.2 设计思想

　分片为应对高吞吐量与大数据量提供了方法。使用分片减少了每个分片需要处理的请求数，因此，通过水平扩展，集群可以提高自己的存储容量和吞吐量。举例来说，当插入一条数据时，应用只需要访问存储这条数据的分片.

　　使用分片减少了每个分片存储的数据。

　　例如，如果数据库1tb的数据集，并有4个分片，然后每个分片可能仅持有256 GB的数据。如果有40个分片，那么每个切分可能只有25GB的数据。

#### 2.3 优势

**对集群进行抽象，让集群“不可见”**

MongoDB自带了一个叫做mongos的专有路由进程。mongos就是掌握统一路口的路由器，其会将客户端发来的请求准确无误的路由到集群中的一个或者一组服务器上，同时会把接收到的响应拼装起来发回到客户端。

**保证集群总是可读写**

MongoDB通过多种途径来确保集群的可用性和可靠性。将MongoDB的分片和复制功能结合使用，在确保数据分片到多台服务器的同时，也确保了每分数据都有相应的备份，这样就可以确保有服务器换掉时，其他的从库可以立即接替坏掉的部分继续工作。

**使集群易于扩展**

当系统需要更多的空间和资源的时候，MongoDB使我们可以按需方便的扩充系统容量。

#### 2.4 集群架构

默认需要3个config server节点多个mongos节点，多个mongod节点

![image-20210508144744831](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210508144744831.png)

* *mongos ：数据路由，和客户端打交道的模块。mongos本身没有任何数据，他也不知道该怎么处理这数据，去找config server*
* *config server：所有存、取数据的方式，所有shard节点的信息，分片功能的一些配置信息。可以理解为真实数据的元数据。*
* *shard：真正的数据存储位置，以chunk为单位存数据。*

#### 2.5 数据分布

在一个shard server内部，MongoDB还是会把数据分为chunks，每个chunk代表这个shard server内部一部分数据。chunk的产生，会有以下两个用途：

* **Splitting**：当一个chunk的大小超过配置中的chunk size时，MongoDB的后台进程会把这个chunk切分成更小的chunk，从而避免chunk过大的情况
* **Balancing**：在MongoDB中，balancer是一个后台进程，负责chunk的迁移，从而均衡各个shard server的负载，系统初始1个chunk，chunk size默认值64M,生产库上选择适合业务的chunk size是最好的。MongoDB会自动拆分和迁移chunks。

1. *使用chunk来存储数据*
2. *集群搭建完成之后，默认开启一个chunk，大小是64M，*
3. *存储需求超过64M，chunk会进行分裂，如果单位时间存储需求很大，设置更大的chunk*
4. *chunk会被自动均衡迁移。*

**chunkSize的选择**

chunk的分裂和迁移非常消耗IO资源；chunk分裂的时机：在插入和更新，读数据不会分裂

小的chunksize：数据均衡是迁移速度快，数据分布更均匀。数据分裂频繁，路由节点消耗更多资源。大的chunksize：数据分裂少。数据块移动集中消耗IO资源。通常100-200M

**chunk分裂及迁移**

随着数据的增长，其中的数据大小超过了配置的chunk size，默认是64M，则这个chunk就会分裂成两个。数据的增长会让chunk分裂得越来越多。

这时候，各个shard 上的chunk数量就会不平衡。这时候，mongos中的一个组件balancer 就会执行自动平衡。把chunk从chunk数量最多的shard节点挪动到数量最少的节点。

**chunkSize对分裂迁移的影响**

MongoDB 默认的 chunkSize 为64MB，如无特殊需求，建议保持默认值；chunkSize 会直接影响到 chunk 分裂、迁移的行为。

　　chunkSize 越小，chunk 分裂及迁移越多，数据分布越均衡；反之，chunkSize 越大，chunk 分裂及迁移会更少，但可能导致数据分布不均。

　　chunkSize 太小，容易出现 jumbo chunk（即shardKey 的某个取值出现频率很高，这些文档只能放到一个 chunk 里，无法再分裂）而无法迁移；chunkSize 越大，则可能出现 chunk 内文档数太多（chunk 内文档数不能超过 250000 ）而无法迁移。

　　chunk 自动分裂只会在数据写入时触发，所以如果将 chunkSize 改小，系统需要一定的时间来将 chunk 分裂到指定的大小。

　　chunk 只会分裂，不会合并，所以即使将 chunkSize 改大，现有的 chunk 数量不会减少，但 chunk 大小会随着写入不断增长，直到达到目标大小。

#### 2.6 数据区分

##### 2.6.1 分片键

MongoDB中数据的分片是、以集合为基本单位的，集合中的数据通过片键（Shard key）被分成多部分。其实片键就是在集合中选一个键，用该键的值作为数据拆分的依据。

所以一个好的片键对分片至关重要。片键必须是一个索引，通过sh.shardCollection加会自动创建索引（前提是此集合不存在的情况下）。一个自增的片键对写入和数据均匀分布就不是很好，因为自增的片键总会在一个分片上写入，后续达到某个阀值可能会写到别的分片。但是按照片键查询会非常高效。

随机片键对数据的均匀分布效果很好。注意尽量避免在多个分片上进行查询。在所有分片上查询，mongos会对结果进行归并排序。

对集合进行分片时，你需要选择一个片键，片键是每条记录都必须包含的，且建立了索引的单个字段或复合字段，MongoDB按照片键将数据划分到不同的数据块中，并将数据块均衡地分布到所有分片中。

为了按照片键划分数据块，MongoDB使用基于范围的分片方式或者 基于哈希的分片方式。

* *分片键是不可变。*
* *分片键必须有索引。*
* *分片键大小限制512bytes。*
* *分片键用于路由查询。*
* *MongoDB不接受已进行collection级分片的collection上插入无分片*
* *键的文档（也不支持空值插入）*

#### 2.7 部署分片集群

**shard集群配置**

1. 创建目录

   ```shell
   for  i in 17 18 19 20 21 22 23 24 25 26 
     do 
     mkdir -p /mongodb/280$i/conf  
     mkdir -p /mongodb/280$i/data  
     mkdir -p /mongodb/280$i/log
   done
   ```

2. 编辑集群配置

   ```shell
   cat > /mongodb/28021/conf/mongod.conf <<'EOF'
   systemLog:
     destination: file
     path: /mongodb/28021/log/mongodb.log   
     logAppend: true
   storage:
     journal:
       enabled: true
     dbPath: /mongodb/28021/data
     directoryPerDB: true
     #engine: wiredTiger
     wiredTiger:
       engineConfig:
         cacheSizeGB: 1
         directoryForIndexes: true
       collectionConfig:
         blockCompressor: zlib
       indexConfig:
         prefixCompression: true
   net:
     bindIp: 10.0.0.152
     port: 28021
   replication:
     oplogSizeMB: 2048
     replSetName: sh1
   sharding:
     clusterRole: shardsvr
   processManagement: 
     fork: true
   EOF
   ```

3. 复制shard集群配置文件

   ```shell
   for  i in  22 23 24 25 26  
     do  
      \cp  /mongodb/28021/conf/mongod.conf  /mongodb/280$i/conf/
   done
   ```

4. 修改配置文件

   ```shell
   for  i in   22 23 24 25 26  
     do 
       sed  -i  "s#28021#280$i#g" /mongodb/280$i/conf/mongod.conf
   done
   ```

5.  修改配置文件复制集名称（replSetName）

   ```shell
   for  i in    24 25 26  
     do 
       sed  -i  "s#sh1#sh2#g" /mongodb/280$i/conf/mongod.conf
   done
   ```

6. 启动shard集群

   ```shell
   for  i in  21 22 23 24 25 26
     do  
       mongod -f /mongodb/280$i/conf/mongod.conf 
   done
   ```

7. 配置复制集

   ```shell
   mongo --host 10.0.0.152 --port 28021  admin
   config = {_id: 'sh1', members: [
                             {_id: 0, host: '10.0.0.152:28021'},
                             {_id: 1, host: '10.0.0.152:28022'},
                             {_id: 2, host: '10.0.0.152:28023',"arbiterOnly":true}]
              }  
    # 初始化配置
   rs.initiate(config) 
   ```

8. 配置复制集2

   ```shell
   mongo --host 10.0.0.152 --port 28024  admin
   config = {_id: 'sh2', members: [
                             {_id: 0, host: '10.0.0.152:28024'},
                             {_id: 1, host: '10.0.0.152:28025'},
                             {_id: 2, host: '10.0.0.152:28026',"arbiterOnly":true}]
              }
   # 初始化配置
   rs.initiate(config)
   ```

   **config集群配置**

   1. 创建主节点配置文件

   ```
   cat > /mongodb/28018/conf/mongod.conf <<'EOF'
   systemLog:
     destination: file
     path: /mongodb/28018/log/mongodb.conf
     logAppend: true
   storage:
     journal:
       enabled: true
     dbPath: /mongodb/28018/data
     directoryPerDB: true
     #engine: wiredTiger
     wiredTiger:
       engineConfig:
         cacheSizeGB: 1
         directoryForIndexes: true
       collectionConfig:
         blockCompressor: zlib
       indexConfig:
         prefixCompression: true
   net:
     bindIp: 10.0.0.152
     port: 28018
   replication:
     oplogSizeMB: 2048
     replSetName: configReplSet
   sharding:
     clusterRole: configsvr
   processManagement: 
     fork: true
   EOF
   ```

   2. 将配置文件分发到从节点

   ```
   for  i in 19 20 
     do  
      \cp  /mongodb/28018/conf/mongod.conf  /mongodb/280$i/conf/
   done
   ```

   3. 修改配置文件端口信息

   ```
   for  i in 19 20  
     do 
       sed  -i  "s#28018#280$i#g" /mongodb/280$i/conf/mongod.conf
   done
   ```

   4. 启动config server集群

   ```
   for  i in  18 19 20 
     do  
       mongod -f /mongodb/280$i/conf/mongod.conf 
   done
   ```

   5. 配置config server复制集

   ```
   mongo --host 10.0.0.152 --port 28018  admin
   ```

   6. 配置复制集信息

   ```
   config = {_id: 'configReplSet', members: [
                             {_id: 0, host: '10.0.0.152:28018'},
                             {_id: 1, host: '10.0.0.152:28019'},
                             {_id: 2, host: '10.0.0.152:28020'}]
              }
   # 初始化配置
   rs.initiate(config)    
   ```

   　　注：config server 使用复制集不用有arbiter节点。3.4版本以后config必须为复制集

   **mongos节点配置**

   1. 修改配置文件

   ```
   cat > /mongodb/28017/conf/mongos.conf <<'EOF'
   systemLog:
     destination: file
     path: /mongodb/28017/log/mongos.log
     logAppend: true
   net:
     bindIp: 10.0.0.152
     port: 28017
   sharding:
     configDB: configReplSet/10.0.0.152:28108,10.0.0.152:28019,10.0.0.152:28020
   processManagement: 
     fork: true
   EOF
   ```

   2. 启动mongos

   ```
   mongos -f /mongodb/28017/conf/mongos.conf
   ```

   3. 登陆到mongos

   ```
   mongo 10.0.0.152:28017/admin
   ```

   4. 添加分片节点

   ```
   db.runCommand( { addshard : "sh1/10.0.0.152:28021,10.0.0.152:28022,10.0.0.152:28023",name:"shard1"} )
   db.runCommand( { addshard : "sh2/10.0.0.152:28024,10.0.0.152:28025,10.0.0.152:28026",name:"shard2"} )
   ```

   5. 列出分片

   ```
   mongos> db.runCommand( { listshards : 1 } )
   {
       "shards" : [
           {
               "_id" : "shard2",
               "host" : "sh2/10.0.0.152:28024,10.0.0.152:28025"
           },
           {
               "_id" : "shard1",
               "host" : "sh1/10.0.0.152:28021,10.0.0.152:28022"
           }
       ],
       "ok" : 1
   }
   ```

   整体状态查看

   ```
   mongos> sh.status();
   ```

##### 2.8 分片配置

激活数据库分片功能

```
mongos> db.runCommand( { enablesharding : "test" } )
```

指定分片建对集合分片，范围片键--创建索引

```
mongos> use test 
mongos> db.vast.ensureIndex( { id: 1 } )
mongos> use admin
mongos> db.runCommand( { shardcollection : "test.vast",key : {id: 1} } )
```

