使用docker部署在mysql8主从复制

```shell
docker pull mysql:8.0.16
```

### 1. 主机配置

1. 新建挂载目录

   ```shell
   mkdir -p /usr/mysql/conf /usr/mysql/data
   ```

2. 修改目录权限

   ```shell
   chmod -R 755 /usr/mysql/
   ```

3. 修改配置文件 

   ```shel
   vi /usr/mysql/conf/my.cnf
   ```

   my.cnf

   ```properties
   [client]
   #socket = /usr/mysql/mysqld.sock
   default-character-set = utf8mb4
   [mysqld]
   #pid-file        = /var/run/mysqld/mysqld.pid
   #socket          = /var/run/mysqld/mysqld.sock
   #datadir         = /var/lib/mysql
   #socket = /usr/mysql/mysqld.sock
   #pid-file = /usr/mysql/mysqld.pid
   datadir = /usr/mysql/data
   character_set_server = utf8mb4
   collation_server = utf8mb4_bin
   secure-file-priv= NULL
   # Disabling symbolic-links is recommended to prevent assorted security risks
   symbolic-links=0
   # Custom config should go here
   !includedir /etc/mysql/conf.d/
   
   log-bin = mysql-bin
   server-id =1
   innodb-file-per-table =ON
   skip_name_resolve=ON
   binlog-format=ROW
   binlog-do-db=Test #这里是需要同步的数据库
   
   ```

4. 创建启动脚本

   start_mysql.sh

   ```shell
   docker stop mysql #停止容器
   docker rm mysql   #移除容器
   docker run --restart=unless-stopped -d --name mysql -v /usr/mysql/conf/my.cnf:/etc/mysql/my.cnf -v /usr/mysql/data:/usr/mysql/data -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:8.0.16 #启动容器
   ```

5. 赋予脚本启动权限

   ```shell
   chmod +x start_mysql.sh
   ```

6. 运行主节点

   ```shell
   ./start_mysql.sh
   ```

### 2. 从机配置

1. 新建挂载目录

   ```shell
   mkdir -p /usr/mysql2/conf /usr/mysql2/data
   ```

2. 修改目录权限

   ```shell
   chmod -R 755 /usr/mysql2/
   ```

3. 修改配置文件 

   ```shell
   vi /usr/mysql2/conf/my.cnf # 修改配置文件vi /usr/mysql/conf/my.cnf
   ```

   my.cnf
   
   ```properties
   #socket = /usr/mysql/mysqld.sock
   default-character-set = utf8mb4
   [mysqld]
   port = 3307
   #pid-file        = /var/run/mysqld/mysqld.pid
   #socket          = /var/run/mysqld/mysqld.sock
   #datadir         = /var/lib/mysql
   #socket = /usr/mysql/mysqld.sock
   #pid-file = /usr/mysql/mysqld.pid
   datadir = /usr/mysql2/data
   character_set_server = utf8mb4
   collation_server = utf8mb4_bin
   secure-file-priv= NULL
   # Disabling symbolic-links is recommended to prevent assorted security risks
   symbolic-links=0
   # Custom config should go here
   !includedir /etc/mysql/conf.d/
   
   server-id=2
   log-bin=mysql-bin
   binlog-format=ROW
   replicate-do-db=Test #这里是需要同步的数据库
   read_only=1 # 从库最好配置成可读，不然从库的更新操作可能会导致binlog 同步出现问题，这里对root用户无效
   ```
   
4. 创建启动脚本

   start_mysql2.sh

   ```shell
   docker stop mysql2
   docker rm mysql2
   docker run --restart=unless-stopped -d --name mysql2 -v /usr/mysql2/conf/my.cnf:/etc/mysql/my.cnf -v /usr/mysql2/data:/usr/mysql/data -p 3307:3307 -e MYSQL_ROOT_PASSWORD=123456 mysql:8.0.16
   ```

5. 赋予脚本启动权限

   ```shell
   chmod +x start_mysql2.sh
   ```

6. 运行从节点

   ```shell
   ./start_mysql2.sh
   ```

### 3. 主从配置

主机执行的命令

```shell
show GLOBAL VARIABLES like '%log_bin%' #查看二进制日志是否开启
show master logs; #查看主节点二进制日志列表,从节点配置的数据从这里取
show GLOBAL VARIABLES like '%server%' #查看主节点的server id
```

从机执行的命令

```shell
STOP SLAVE; #关闭同步

CHANGE MASTER TO
MASTER_HOST='服务器主机地址',
MASTER_USER='用户名',
MASTER_PASSWORD='密码!',
MASTER_LOG_FILE='mysql-bin.000001',
MASTER_LOG_POS=0;

START SLAVE; # 开启同步
```

```shell
 START SLAVE; # 开启同步
 SHOW SLAVE STATUS\G; # 查看Slave的运行状态
```

接下来在主库Test中创建的表和数据都会同步到从库了
