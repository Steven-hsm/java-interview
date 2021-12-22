依赖java环境，请先配好java环境

1. 获取安装包

   ```shell
   wget https://downloads.apache.org/zookeeper/stable/apache-zookeeper-3.6.3-bin.tar.gz
   ```

2. 解压

   ```shell
   tar -zxvf apache-zookeeper-3.6.3-bin.tar.gz
   ```

3. 拷贝配置文件

   ```shell
   cd apache-zookeeper-3.6.3-bin
   cp conf/zoo_sample.cfg conf/zoo.cfg
   ```

4. 启动服务端

   ```shell
   bin/zkServer.sh start conf/zoo.cfg 
   ```

5. 启动客户端连接

   ```shell
   bin/zkCli.sh
   ```

   













