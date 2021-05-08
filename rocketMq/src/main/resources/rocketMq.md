## 1.安装jdk

```shell
 tar -zxvf jdk-8u291-linux-x64.tar.gz
 
 vi ~/.bash_profile
 PATH=$PATH:/opt/jdk1.8.0_291/bin
 export PATH
 
 source ~/.bash_profile
```



## 2. 安装maven

```shell
wget https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
tar -zxvf apache-maven-3.6.3-bin.tar.gz

vi ~/.bash_profile
PATH=$PATH:/opt/apache-maven-3.6.3/bin
export PATH

source ~/.bash_profile
```

## 3. 安装rocketMq

```shell
wget https://codeload.github.com/apache/rocketmq/tar.gz/refs/tags/rocketmq-all-4.8.0
tar -zxvf rocketmq-all-4.8.0
cd rocketmq-all-4.8.0/
mvn -Prelease-all -DskipTests clean install -U

cd distribution/target/rocketmq-4.8.0/rocketmq-4.8.0
nohup sh bin/mqnamesrv &
nohup sh bin/mqbroker -n localhost:9876 &

export NAMESRV_ADDR=localhost:9876
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
```

