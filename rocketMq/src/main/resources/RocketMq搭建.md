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
tail -f ~/logs/rocketmqlogs/Broker.log 
#6. 测试
export NAMESRV_ADDR=localhost:9876
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
```

