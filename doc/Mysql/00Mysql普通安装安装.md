### 1 .普通安装
#### 1.1 安装5.7

```shell
wget https://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm
yum -y localinstall mysql57-community-release-el7-11.noarch.rpm 
yum -y install mysql-community-server
```

### 1.2 服务启动

```shell
# 启动mysql
systemctl start mysqld  
# 开机启动
systemctl enable mysqld
systemctl daemon-reload
```

#### 1.3 修改root登录密码

* 查看原密码

  ```shell
  cat /var/log/mysqld.log
  # 找到下面的一条记录可以看到临时密码
  2021-10-20T06:53:57.484667Z 1 [Note] A temporary password is generated for root@localhost: +pjOl?y)V4kj
  ```

* 命令行登录并修改密码

  ```shell
  mysql -u root -p  # 登录，输入上面的原始密码
  # 修改密码，可能报错说密码过于简单，这里就设置复杂点
  ALTER USER 'root'@'localhost' IDENTIFIED BY 'daasan7ujm^YHN';
  ```

* 设置远程登录

  ```shell
  GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'daasan7ujm^YHN' WITH GRANT OPTION; # 修该密码
  flush privileges;  # 刷新
  ```

* 开启访问端口

  ```shell
  firewall-cmd --zone=public --add-port=3306/tcp --permanent
  firewall-cmd --reload
  ```

#### 1.4 修改配置文件

```shell
vi /etc/my.cnf # 编辑文件

[mysql]
default-character-set=utf8
[mysqld]
datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
#Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links=0
log-error=/var/log/mysqld.log
pid-file=/var/run/mysqld/mysqld.pid
default-storage-engine=INNODB
character_set_server=utf8

systemctl restart mysqld  #重启mysql
```

#### 1.5 设置密码复杂度

```shell
set global validate_password_policy=0;      # 关闭密码复杂性策略
set global validate_password_length=1;      # 设置密码复杂性要求密码最低长度为1
set password=password('123456');
grant all privileges on *.* to root@'%' identified by '123456';

#想要永久生效，将配置配置到配置文件中 etc/my.cnf
```

### 2. docker 安装mysql8

#### 2.1拉取镜像

```shell
docker pull mysql:8.0.16
```

#### 2.2 创建挂载目录

```shell
mkdir -p /usr/mysql/conf /usr/mysql/data
chmod -R 755 /usr/mysql/
vim /usr/mysql/conf/my.cnf # 修改配置文件
```

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
```

#### 2.3 启动容器

```shell
docker run --restart=unless-stopped -d --name mysql -v /usr/mysql/conf/my.cnf:/etc/mysql/my.cnf -v /usr/mysql/data:/usr/mysql/data -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:8.0.16
```

参数解释：
* -v : 挂载宿主机目录和 docker容器中的目录，前面是宿主机目录，后面是容器内部目录

* -d : 后台运行容器

* -p 映射容器端口号和宿主机端口号

* -e 环境参数，MYSQL_ROOT_PASSWORD设置root用户的密码
  执行上述命令后，执行查询容器的命令就可以看到创建的mysql容器

  ```shel
  docker ps -a
  ```

### 2.4 远程连接

上述虽然安装好了mysql，但是使用远程的Navicat连接时提示错误，不能正确连接mysql，此时需要修改按照下面说的步骤修改一下mysql的密码模式以及主机等内容才可以。

修改mysql密码以及可访问主机
```shell
docker exec -it mysql /bin/bash #进入容器内部
mysql -uroot -p #连接mysql
mysql> use mysql #使用mysql库
#修改访问主机以及密码等，设置为所有主机可访问
$ mysql> ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
flush privileges # 刷新
```
经过以上步骤，再次远程使用Navicat连接数据库时就可以正常连接了。
**注意**：mysql_native_password，mysql8.x版本必须使用这种模式，否则navicate无法正确连接

