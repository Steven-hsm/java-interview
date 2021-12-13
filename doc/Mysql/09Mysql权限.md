Mysql权限管理也是通过mysql表来管理的

本文基于mysql8

* 身份验证：

  * 优先从mysql.user中判断ip、用户名、密码是否存在,存在即通过验证。

* 权限认证：

  * 按照mysql.user、db、tables_priv、columns_priv、procs_priv顺序验证，如果对应权限值为Y,表示拥有该权限

### 1. MySQL 权限级别

  * 全局性的管理权限：作用于整个MySQL实例级别 （user表）

      * User表：存放用户账户信息以及全局级别（所有数据库）权限，决定了来自哪些主机的哪些用户可以访问数据库实例，如果`有全局权限则意味着对所有数据库都有此权限
  * 数据库级别的权限： 作用于某个指定的数据库上或者所有的数据库上 （db表）
        * Db表：存放`数据库级别`的权限，决定了来自哪些主机的哪些用户可以访问此数据库 
  * 数据库对象级别的权限：作用于指定的数据库对象上（表、视图等）或者所有的数据库对象上（tables_priv、columns_priv、procs_priv）

      * Tables_priv表：`存放表级别的权限`，决定了来自哪些主机的哪些用户可以访问数据库的这个表 
      * Columns_priv表：`存放列级别的权限`，决定了来自哪些主机的哪些用户可以访问数据库表的这个字段 
      * Procs_priv表：`存放存储过程和函数`级别的权限

  权限存在mysql上述表中，待MySQL实例启动后就加载到内存中

### 2. MySQL 权限详解
* All/All Privileges权限代表全局或者全数据库对象级别的所有权限

* Alter权限代表允许修改表结构的权限，但必须要求有create和insert权限配合。如果是rename表名，则要求有alter和drop原表， create和insert新表的权限

* Alter routine权限代表允许修改或者删除存储过程、函数的权限

* Create权限代表允许创建新的数据库和表的权限

* Create routine权限代表允许创建存储过程、函数的权限

* Create tablespace权限代表允许创建、修改、删除表空间和日志组的权限

* Create temporary tables权限代表允许创建临时表的权限

* Create user权限代表允许创建、修改、删除、重命名user的权限

* Create view权限代表允许创建视图的权限

* Delete权限代表允许删除行数据的权限

* Drop权限代表允许删除数据库、表、视图的权限，包括truncate table命令

* Event权限代表允许查询，创建，修改，删除MySQL事件

* Execute权限代表允许执行存储过程和函数的权限

* File权限代表允许在MySQL可以访问的目录进行读写磁盘文件操作，可使用的命令包括load data infile,select … into outfile,load file()函数

* Grant option权限代表是否允许此用户授权或者收回给其他用户你给予的权限,重新付给管理员的时候需要加上这个权限

* Index权限代表是否允许创建和删除索引

* Insert权限代表是否允许在表里插入数据，同时在执行analyze table,optimize table,repair table语句的时候也需要insert权限

* Lock权限代表允许对拥有select权限的表进行锁定，以防止其他链接对此表的读或写

* Process权限代表允许查看MySQL中的进程信息，比如执行show processlist, mysqladmin processlist, show engine等命令

* Reference权限是在5.7.6版本之后引入，代表是否允许创建外键

* Reload权限代表允许执行flush命令，指明重新加载权限表到系统内存中，refresh命令代表关闭和重新开启日志文件并刷新所有的表

* Replication client权限代表允许执行show master status,show slave status,show binary logs命令

* Replication slave权限代表允许slave主机通过此用户连接master以便建立主从复制关系

* Select权限代表允许从表中查看数据，某些不查询表数据的select执行则不需要此权限，如Select 1+1， Select PI()+2；而且select权限在执行update/delete语句中含有where条件的情况下也是需要的

* Show databases权限代表通过执行show databases命令查看所有的数据库名

* Show view权限代表通过执行show create view命令查看视图创建的语句

* Shutdown权限代表允许关闭数据库实例，执行语句包括mysqladmin shutdown

* Super权限代表允许执行一系列数据库管理命令，包括kill强制关闭某个连接命令， change master to创建复制关系命令，以及create/alter/drop server等命令

* Trigger权限代表允许创建，删除，执行，显示触发器的权限

* Update权限代表允许修改表中的数据的权限

* Usage权限是创建一个用户之后的默认权限，其本身代表连接登录权限

### 3. MySQL权限表结构说明

![image-20211130105425988](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211130105425988.png)

 以user表为例，其他表类似。

> 注意： 这里是以host、user为主键，所以在创建用户以及赋权限的时候要特别小心
>
>  CREATE USER 'test12345'@'%' IDENTIFIED BY '123456'; 
>
>  CREATE USER 'test12345'@'*' IDENTIFIED BY '123456';  
>
> 上面两条语句会创建两个用户

**Tables_priv和columns_priv权限值**

| Table Name     | Column Name   | Possible Set Elements                                        |
| -------------- | ------------- | ------------------------------------------------------------ |
| `tables_priv`  | `Table_priv`  | `'Select', 'Insert', 'Update', 'Delete', 'Create', 'Drop', 'Grant', 'References', 'Index', 'Alter', 'Create View', 'Show view', 'Trigger'` |
| `tables_priv`  | `Column_priv` | `'Select', 'Insert', 'Update', 'References'`                 |
| `columns_priv` | `Column_priv` | `'Select', 'Insert', 'Update', 'References'`                 |
| `procs_priv`   | `Proc_priv`   | `'Execute', 'Alter Routine', 'Grant'`                        |

### 4. MySQL权限常用命令

**命令说明**

* MySQL的授权用户由两部分组成： `用户名和登录主机名`
* 表达用户的语法为’user_name’@’host_name’，单引号不是必须，但如果其中`包含特殊字符则是必须的`,”@‘localhost’代表匿名登录的用户
* Host_name可以使主机名或者ipv4/ipv6的地址。 Localhost代表本机， 127.0.0.1代表ipv4本机地址， ::1代表ipv6的本机地址
* Host_name字段允许使用`%和_`两个匹配字符，比如’%’代表所有主机， ’%.mysql.com’代表 来自mysql.com这个域名下的所有主机， ‘192.168.1.%’代表所有来自192.168.1网段的主机

```mysql
# 有理论之后，下面的命令执行完可以在终端执行测试一下
# mysql8经测试，必须先创建用户之后再赋予权限，赋予权限时，能再加IDENTIFIED BY，创建用户和赋予权限语句的host、user必须相同
## 赋予用户权限简单命令##################################################
select user,host from mysql.user; # 查询mysql用户信息
show grants for root@'localhost'; #查看已经授权给用户的权限信息 
show create user root@'localhost'; #查看用户的其他非授权信息
CREATE USER 'test'@'localhost' IDENTIFIED BY '123456'; #创建用户，这个时候还没有权限 192.168.148.132
GRANT ALL PRIVILEGES ON *.* TO 'test'@'localhost' WITH GRANT OPTION; #赋予权限，这里赋予了所有权限，会直接在user表添加相关权限信息,这里不能远程连接

CREATE USER 'test'@'%' IDENTIFIED BY '123456'; 
GRANT ALL PRIVILEGES ON *.* TO 'test'@'%' WITH GRANT OPTION; #和上述两个语句的区别是，这里支持远程访问，上述语句只能本地访问

##  回收 mysql 权限#####################################################
CREATE USER 'hsm'@'%' IDENTIFIED BY '123456'; #创建用户
grant select on Test.User to hsm@'%'; #赋予查询的权限
show grants for hsm@'%'; # 查询权限信息
	GRANT USAGE ON *.* TO `hsm`@`%`
	GRANT SELECT ON `Test`.`User` TO `hsm`@`%`
revoke select on Test.User from hsm@'%'; #回收查询权限，再次查询，会发现没有了select权限

## 删除用户权限 #########################################################
drop user hsm@'%' # 删除用户

flush privileges; #刷新权限
rename user 'hsm'@'*' to 'hsm'@'%'; # 重命名权限
```

**修改用户权限说明**

* 执行Grant,revoke,set password,rename user命令修改权限之后， MySQL会自动将修改后的权限信息同步加载到系统内存中
* 如果执行insert/update/delete操作上述的系统权限表之后，则必须再执行刷新权限命令才能同步到系统内存中，刷新权限命令包括： `flush privileges`/mysqladmin flush-privileges / mysqladmin reload
* 如果是修改tables和columns级别的权限，则客户端的下次操作新权限就会生效
* 如果是修改database级别的权限，则新权限在客户端执行use database命令后生效
* 如果是修改global级别的权限，则需要重新创建连接新权限才能生效
* 如果是修改global级别的权限，则需要重新创建连接新权限才能生效 (例如修改密码)

**GRANT命令使用说明**

*  ALL PRIVILEGES 是表示所有权限，你也可以使用select、update等权限。
*  ON 用来指定权限针对哪些库和表。 \*.\* 中前面的*号用来指定数据库名，后面的*号用来指定表名。
*  TO 表示将权限赋予某个用户。
*  WITH GRANT OPTION 这个选项表示该用户可以将自己拥有的权限授权给别人
* 可以使用GRANT重复给用户添加权限，权限叠加，比如你先给用户添加一个select权限，然后又给用户添加一个insert权限，那么该用户就同时拥有了select和insert权限。

### 5. 其他命令

**设置MySQL用户资源限制**

- 通过设置全局变量max_user_connections可以限制所有用户在同一时间连接MySQL实例的数量，但此参数无法对每个用户区别对待，所以MySQL提供了对每个用户的资源限制管理
- MAX_QUERIES_PER_HOUR：一个用户在一个小时内可以执行查询的次数（基本包含所有语句）
- MAX_UPDATES_PER_HOUR：一个用户在一个小时内可以执行修改的次数（仅包含修改数据库或表的语句）
- MAX_CONNECTIONS_PER_HOUR：一个用户在一个小时内可以连接MySQL的时间
- MAX_USER_CONNECTIONS：一个用户可以在`同一时间连接MySQL实例的数量`

**修改 mysql 用户密码**

```shell
alter user test12345@'*' IDENTIFIED BY '1234567'; # 修改密码为1234567
```

**设置MySQL用户密码过期策略**

设置系统参数default_password_lifetime作用于所有的用户账户

* default_password_lifetime=180 设置180天过期
* default_password_lifetime=0 设置密码不过期 

```mysql
ALTER USER 'hsm'@'%' PASSWORD EXPIRE INTERVAL 90 DAY;
ALTER USER 'hsm'@'%' PASSWORD EXPIRE NEVER; #密码不过期
ALTER USER 'hsm'@'%' PASSWORD EXPIRE DEFAULT; #默认过期策略
ALTER USER 'hsm'@'%' PASSWORD EXPIRE; # 手动强制某个用户密码过期
```

**mysql 用户 lock**

通过执行create user/alter user命令中带account lock/unlock子句设置用户的lock状态

```mysql
 create user abc2@localhost identified by 'mysql' account lock; #创建用户并锁定用户
 alter user abc2@'localhost' account unlock;  # 解锁用户
```

### 5. 权限例子

* 只授予能满足需要的最小权限，防止用户干坏事。比如用户只是需要查询，那就只给select权限就可以了，不要给用户赋予update、insert或者delete权限。
* 创建用户的时候限制用户的登录主机，一般是限制成指定IP或者内网IP段。
* 初始化数据库的时候删除没有密码的用户。安装完数据库的时候会自动创建一些用户，这些用户默认没有密码。
* 为每个用户设置满足密码复杂度的密码。
* 定期清理不需要的用户。回收权限或者删除用户。

1. 创建一个用户，对数据库只有可读权限

   ```shell
   CREATE USER 'readonly'@'%' IDENTIFIED BY 'readonly'; #创建用户readonly,%表示可以远程连接
   GRANT select ON *.* TO 'readonly'@'%'; #赋予权限，这里赋予了所有权限，会直接在user表添加相关权限信息,这里不能远程连接
   ```

2. 创建一个用户，只赋予用户某几个数据库的权限

   ```mysql
   CREATE USER 'order_all'@'%' IDENTIFIED BY 'order_all'; #创建用户order,可以查看订单表所有权限
   GRANT ALL PRIVILEGES ON Test.* TO 'order_all'@'%'; #赋予权限,Test库的全部权限
   GRANT ALL PRIVILEGES ON mypower.* TO 'order_all'@'%'; #赋予权限,mypower库的全部权限
   ```

3. 创建一个用户，只赋予用户某几个表的权限

   ```mysql
   CREATE USER 'table'@'%' IDENTIFIED BY 'table'; #创建用户order,可以查看订单表所有权限
   GRANT ALL PRIVILEGES ON Test.user TO 'table'@'%'; #赋予权限,Test库user表的全部权限
   GRANT ALL PRIVILEGES ON Test.Test TO 'table'@'%'; #赋予权限,Test库Test表的全部权限
   ```

4. 
