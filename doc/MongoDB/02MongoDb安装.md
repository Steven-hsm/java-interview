1. 下载安装包

   ```shell
   wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel70-5.0.5.tgz
   ```

2. 解压缩

   ```shell
   tar -xvzf mongodb-linux-x86_64-rhel70-5.0.5.tgz
   mv mongodb-linux-x86_64-rhel70-5.0.5 mongodb5.0.5
   ```

3. 添加到系统路径

   ```shell
   vi ~/.bash_profile
   PATH=$PATH:/root/soft/mongodb5.0.5/bin
   
   source ~/.bash_profile
   ```

4. 创建数据目录

   ```shell
   mkdir -p /root/soft/mongodb5.0.5/data/db #MongoDB默认的数据存放路径 /data/db ‐‐dbpath 参数指定路径
   mkdir -p /root/soft/mongodb5.0.5/data/log #日志文件地址
   ```

5. 启动

   ```shell
   mongod --logpath /root/soft/mongodb5.0.5/data/log  --dbpath /root/soft/mongodb5.0.5/data/db --fork
   ```

6. 连接

   ```shell
   mongo
   ```

7. 若数据损坏，需要进行如下操作

   ```shell
   rm -f /root/soft/mongodb5.0.5/data/db/*.lock # 删除lock文件
   mongod --repair --dbpath=/root/soft/mongodb5.0.5/data/db #修复数据
   ```

8. 关闭服务

   ```shell
   mongod  --shutdown  --dbpath /root/soft/mongodb5.0.5/data/db
   ```

**通过配置文件方式启动**

1. 增加配置文件

   ```shell
   vi mongodb.conf
   dbpath=/root/soft/mongodb5.0.5/data/db #数据文件存放目录
   logpath=/root/soft/mongodb5.0.5/data/log/mongod.log #日志文件存放地址
   port=27017 #端口
   fork= true #以守护程序的方式启用，即在后台运行
   #auth=true #需要认证。如果放开注释，就必须创建MongoDB的账号，使用账号与密码才可远程访问，第一次安装建议注释
   bind_ip=0.0.0.0 #允许远程访问，或者直接注释，127.0.0.1是只允许本地访问
   ```

2.  通过配置文件启动

   ```shell
   mongod  -f mongodb.conf
   ```

