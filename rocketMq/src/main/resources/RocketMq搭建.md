## 六、RocketMq安装部署

部署前准备

* 安装Jdk

  ```shell
  #1. 获取jdk安装包(进入 /opt目录操作 cd /opt)
  wget https://github.com/frekele/oracle-java/releases/download/8u181-b13/jdk-8u181-linux-x64.tar.gz
  #2. 解压缩jdk安装包
  tar -zxvf jdk-8u181-linux-x64.tar.gz
  #3. 设置环境变量
  vi ~/.bash_profile
  export JAVA_HOME=/opt/jdk1.8.0_181
  export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  export PATH=$PATH:$JAVA_HOME/bin
  #4. 使环境变量生效
  source ~/.bash_profile
  #5. 检测并查看java版本
  java -version
  ```

* 安装Maven

  ```shell
  #1. 获取Maven安装包(进入 /opt目录操作 cd /opt)
  wget https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
  #2. 解压缩Maven压缩包
  tar -zxvf apache-maven-3.6.3-bin.tar.gz
  #3. 设置环境变量
  vi ~/.bash_profile
  export PATH=$PATH:/opt/apache-maven-3.6.3/bin
  #4. 使环境变量生效
  source ~/.bash_profile
  #5. 检测并查看java版本
  mvn -version
  ```

### 1. 单Master模式

这种方式风险较大，一旦Broker重启或者宕机时，会导致整个服务不可用。不建议线上环境使用,可以用于本地测试。

```shell
#1. 获取rocketmq 安装包
wget https://codeload.github.com/apache/rocketmq/tar.gz/refs/tags/rocketmq-all-4.8.0
#2. 解压缩rocketmq安装包
tar -zxvf rocketmq-all-4.8.0
#3. 编译rocketMq
cd rocketmq-all-4.8.0/
mvn -Prelease-all -DskipTests clean install -U
#4. 启动rocketMq mqnamesrv
cd distribution/target/rocketmq-4.8.0/rocketmq-4.8.0
nohup sh bin/mqnamesrv &
# 查看日志
tail -f ~/logs/rocketmqlogs/namesrv.log
#5. 启动broker
nohup sh bin/mqbroker -n localhost:9876 &
#查看日志
tail -f ~/logs/rocketmqlogs/broker.log 
#6. 测试
export NAMESRV_ADDR=localhost:9876
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
```

* 问题解决（内存不足）

  RocketMq默认启动配置的java虚拟机参数内存占用较高，如果内存不够需要修改配置

  修改runserver.sh，runbroker.sh java启动参数

  ```shell
  JAVA_OPT="${JAVA_OPT} -server -Xms1g -Xmx1g -Xmn512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
  ```

* Lock failed,MQ already started（修改参数再启动的时候报错）

  查看java进程，关闭进程之后重新启动程序

  ```shell
  ps -ef | grep java
  kill -9 进程id
  ```

### 2. 多Master模式

一个集群无Slave，全是Master，例如2个Master或者3个Master，这种模式的优缺点如下：

- 优点：配置简单，单个Master宕机或重启维护对应用无影响，在磁盘配置为RAID10时，即使机器宕机不可恢复情况下，由于RAID10磁盘非常可靠，消息也不会丢（异步刷盘丢失少量消息，同步刷盘一条不丢），性能最高；
- 缺点：单台机器宕机期间，这台机器上未被消费的消息在机器恢复之前不可订阅，消息实时性会受到影响。

##### 1）启动NameServer

NameServer需要先于Broker启动，且如果在生产环境使用，为了保证高可用，建议一般规模的集群启动3个NameServer，各节点的启动命令相同，如下：

```
### 首先启动Name Server
$ nohup sh mqnamesrv &
 
### 验证Name Server 是否启动成功
$ tail -f ~/logs/rocketmqlogs/namesrv.log
The Name Server boot success...
```

##### 2）启动Broker集群

```
### 在机器A，启动第一个Master，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-noslave/broker-a.properties &
 
### 在机器B，启动第二个Master，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-noslave/broker-b.properties &

...
```

如上启动命令是在单个NameServer情况下使用的。对于多个NameServer的集群，Broker启动命令中`-n`后面的地址列表用分号隔开即可，例如 `192.168.1.1:9876;192.161.2:9876`。

### 3. 多Master多Slave模式-异步复制

每个Master配置一个Slave，有多对Master-Slave，HA采用异步复制方式，主备有短暂消息延迟（毫秒级），这种模式的优缺点如下：

- 优点：即使磁盘损坏，消息丢失的非常少，且消息实时性不会受影响，同时Master宕机后，消费者仍然可以从Slave消费，而且此过程对应用透明，不需要人工干预，性能同多Master模式几乎一样；
- 缺点：Master宕机，磁盘损坏情况下会丢失少量消息。

##### 1）启动NameServer

```
### 首先启动Name Server
$ nohup sh mqnamesrv &
 
### 验证Name Server 是否启动成功
$ tail -f ~/logs/rocketmqlogs/namesrv.log
The Name Server boot success...
```

##### 2）启动Broker集群

```
### 在机器A，启动第一个Master，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-2s-async/broker-a.properties &
 
### 在机器B，启动第二个Master，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-2s-async/broker-b.properties &
 
### 在机器C，启动第一个Slave，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-2s-async/broker-a-s.properties &
 
### 在机器D，启动第二个Slave，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-2s-async/broker-b-s.properties &
```

### 4. 多Master多Slave模式-同步双写

每个Master配置一个Slave，有多对Master-Slave，HA采用同步双写方式，即只有主备都写成功，才向应用返回成功，这种模式的优缺点如下：

- 优点：数据与服务都无单点故障，Master宕机情况下，消息无延迟，服务可用性与数据可用性都非常高；
- 缺点：性能比异步复制模式略低（大约低10%左右），发送单个消息的RT会略高，且目前版本在主节点宕机后，备机不能自动切换为主机。

##### 1）启动NameServer

```
### 首先启动Name Server
$ nohup sh mqnamesrv &
 
### 验证Name Server 是否启动成功
$ tail -f ~/logs/rocketmqlogs/namesrv.log
The Name Server boot success...
```

##### 2）启动Broker集群

```
### 在机器A，启动第一个Master，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-2s-sync/broker-a.properties &
 
### 在机器B，启动第二个Master，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-2s-sync/broker-b.properties &
 
### 在机器C，启动第一个Slave，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-2s-sync/broker-a-s.properties &
 
### 在机器D，启动第二个Slave，例如NameServer的IP为：192.168.1.1
$ nohup sh mqbroker -n 192.168.1.1:9876 -c $ROCKETMQ_HOME/conf/2m-2s-sync/broker-b-s.properties &
```

以上Broker与Slave配对是通过指定相同的BrokerName参数来配对，Master的BrokerId必须是0，Slave的BrokerId必须是大于0的数。另外一个Master下面可以挂载多个Slave，同一Master下的多个Slave通过指定不同的BrokerId来区分。$ROCKETMQ_HOME指的RocketMQ安装目录，需要用户自己设置此环境变量。

## 搭建图形化界面

[rocketmq-console](https://github.com/apache/rocketmq-externals/releases/tag/rocketmq-console-1.0.0)

```shell
#1. 获取安装包
wget https://codeload.github.com/apache/rocketmq-externals/tar.gz/refs/tags/rocketmq-console-1.0.0
#2. 解压缩
tar -zxvf rocketmq-console-1.0.0
#3. 编译源码
cd rocketmq-externals-rocketmq-console-1.0.0/rocketmq-console/
mvn clean package -Dmaven.test.skip=true
nohup java -jar \
    -Drocketmq.config.namesrvAddr=192.168.106.133:9876 \
    -Drocketmq.config.isVIPChannel=false \
    target/rocketmq-console-ng-1.0.0.jar &
#4. 访问
http://192.168.106.133:8080/#/
```





