1. 官网下载 redis包（http://redis.cn/）

    ```shell
    wget https://download.redis.io/releases/redis-6.2.6.tar.gz
    ```

2. 解压缩包

   ```shell
   tar -zxvf redis-6.2.6.tar.gz
   ```

3. 安装gcc-c++，并编译

   ```shell
   # 1. 安装gcc-c++ 不同的linux不一样，这里是centos安装命令
   yum install gcc-c++
   # 2. 进入目录下
   cd redis-6.2.6
   #3. 编译
   make 
   make install
   ```

4. redis默认安装目录```/usr/local/bin```

5. 把配置文件redis.conf 复制到我们相应的目录下，启动时指定配置文件路径即可

   ```shell
   redis-server /root/conf/redis/redis.conf
   ```

6. 连接客户端

   ```shell
   redis-cli -p 6379
   ```

7. **redis-benchmark**是官方测试性能用的，可以看看

   