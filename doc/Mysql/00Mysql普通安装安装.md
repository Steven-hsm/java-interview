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
# Disabling symbolic-links is recommended to prevent assorted security risks
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



