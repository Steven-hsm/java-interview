![image-20211125102517209](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211125102517209.png)

大体来说，MySQL 可以分为 Server 层和存储引擎层两部分。

**Server层**

主要包括连接器、查询缓存、分析器、优化器、执行器等，涵盖 MySQL 的大多数核心服务功能，以及所有的内置函数 （如日期、时间、数学和加密函数等），所有跨存储引擎的功能都在这一层实现，比如存储过程、触发器、视图等

* 连接器：管理连接以及权限控制
* 分析器：词法分析、语法分析
* 优化器：执行计划生成索引选择
* 执行器：调用引擎接口获取查询结果

**引擎层**

存储引擎层负责数据的存储和提取。其架构模式是插件式的，支持 InnoDB、MyISAM、Memory 等多个存储引擎

读写磁盘、数据结构化存储的实现

### 1. **连接器**

MySQL是开源的，他有非常多种类的客户端：navicat,mysql front,jdbc,SQLyog等非常丰富的客户端

向mysql发起通信都必须先跟Server端建立通信连接，而建立连接的工作就是有连接器完成的

```shell
mysql ‐h host[数据库地址] ‐u root[用户] ‐p root[密码] ‐P 3306
```

连接命令中的 mysql 是客户端工具，用来跟服务端建立连接。在完成经典的 TCP 握手后，连接器就要开始认证你的身份， 这个时候用的就是你输入的用户名和密码。

一个用户成功建立连接后，即使你用管理员账号对这个用户的权限做了修改，也不会影响已经存在连接的权 限

**常用操作**

```mysql
CREATE USER 'username'@'host' IDENTIFIED BY 'password'; #创建新用户
grant all privileges on *.* to 'username'@'%'; #赋权限,%表示所有(host)
flush privileges #刷新数据库
update user set password=password(”123456″) where user=’root’;#(设置用户名密码)
show grants for root@"%"; #查看当前用户的权限
show processlist #展示所有连接的客户端
```

客户端如果长时间不发送command到Server端，连接器就会自动将它断开。这个时间是由参数 wait_timeout 控制的，默认值 是 8 小时。 

```mysql
show global variables like "wait_timeout";  # 查看超时时间
set global wait_timeout=28800; #设置全局服务器关闭非交互连接之前等待活动的秒数
```

* 长连接：指连接成功后，如果客户端持续有请求，则一直使用同一个连接
* 短连接：每次执行完很少的几次 查询就断开连接，下次查询再重新建立一个

开发当中我们大多数时候用的都是长连接,把连接放在Pool内进行管理，但是长连接有些时候会导致 MySQL 占用内存涨得特别 快，这是因为 MySQL 在执行过程中临时使用的内存是管理在连接对象里面的。这些资源会在连接断开的时候才释放。所以如 果长连接累积下来，可能导致内存占用太大，被系统强行杀掉（OOM），从现象看就是 MySQL 异常重启了。怎么解决这类问题呢？ 

* 1、定期断开长连接。使用一段时间，或者程序里面判断执行过一个占用内存的大查询后，断开连接，之后要查询再重连。 
* 2、如果你用的是 MySQL 5.7 或更新版本，可以在每次执行一个比较大的操作后，通过执行 mysql_reset_connection 来重新初始化连接资 源。这个过程不需要重连和重新做权限验证，但是会将连接恢复到刚刚创建完时的状态。 

### 2. 查询缓存

MySQL 拿到一个查询请求后，会先到查询缓存看看，之前是不是执行过这条语句。之前执行过的语句及其结果可能会以 key-value 对的形式，被直接缓存在内存中。key 是查询的语句，value 是查询的结果.

**大多数情况查询缓存就是个鸡肋，为什么呢？**

因为查询缓存往往弊大于利。查询缓存的失效非常频繁，只要有对一个表的更新，这个表上所有的查询缓存都会被清空。 因此很可能你费劲地把结果存起来，还没使用呢，就被一个更新全清空了。对于更新压力大的数据库来说，查询缓存的命中率 会非常低。mysql8.0已经移除了缓存个功能

一般建议大家在静态表里使用查询缓存，什么叫静态表呢？就是一般我们极少更新的表

```properties
my.cnf
#query_cache_type有3个值 0代表关闭查询缓存OFF，1代表开启ON，2（DEMAND）代表当sql语句中有SQL_CACHE 关键词时才缓存 
query_cache_type=2
```

这样对于默认的 SQL 语句都不使用查询缓存。而对于你确定要使用查询缓存的语句，可以用 SQL_CACHE 显式指定

```mysql
select SQL_CACHE * from test where ID=5;
```

```mysql
show global variables like "%query_cache_type%"; # 查看当前mysql实例是否开启缓存机制
show status like'%Qcache%'; //查看运行的缓存信息
```

### 3. **分析器**

如果没有命中查询缓存，就要开始真正执行语句了

词法分析器分成6个主要步骤完成对sql语句的分析 

* 1、词法分析 
* 2、语法分析 
* 3、语义分析 
* 4、构造执行树 
* 5、生成执行计划 
* 6、计划的执行 

### 4. **优化器**

经过了分析器，MySQL 就知道你要做什么了。在开始执行之前，还要先经过优化器的处理。

优化器是在表里面有多个索引的时候，决定使用哪个索引；或者在一个语句有多表关联（join）的时候，决定各个表的连接 顺序

### 5. **执行器**

开始执行的时候，要先判断一下你对这个表 T 有没有执行查询的权限，如果没有，就会返回没有权限的错误，如下所示 (在 工程实现上，如果命中查询缓存，会在查询缓存返回结果的时候，做权限验证。查询也会在优化器之前调用 precheck 验证权 限)。

### 6. **bin-log归档** 

删库是不需要跑路的，因为我们的SQL执行时，会将sql语句的执行逻辑记录在我们的bin-log当中

binlog是Server层实现的二进制日志,他会记录我们的cud操作。Binlog有以下几个特点：

* 1、Binlog在MySQL的Server层实现（引擎共用）
* 2、Binlog为逻辑日志,记录的是一条语句的原始逻辑
* 3、Binlog不限大小,追加写入,不会覆盖以前的日志 

配置my.cnf 

```properties
配置开启binlog 
log‐bin=/usr/local/mysql/data/binlog/mysql‐bin #注意5.7以及更高版本需要配置本项：server‐id=123454（自定义,保证唯一性）; 
#binlog格式，有3种statement,row,mixed
binlog‐format=ROW
#表示每1次执行写入就与硬盘同步，会影响性能，为0时表示，事务提交时mysql不做刷盘操作，由系统决定
sync‐binlog=1
```

binlog命令

```mysql
show variables like '%log_bin%'; # 查看bin‐log是否开启
 flush logs; # 会多一个最新的bin‐log日志
 show master status; #查看最后一个bin‐log日志的相关信息
 reset master;# 清空所有的bin‐log日志
```

查看binlog内容

```mysql
 /usr/local/mysql/bin/mysqlbinlog ‐‐no‐defaults /usr/local/mysql/data/binlog/mysql‐bin. 000001 查看binlog内容
```

