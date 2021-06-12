## 安装
### 1.准备工作

* 安装gcc编译器

  ```
  yum install -y curl-devel expat-devel gettext-devel openssl-devel zlib-devel gcc-c++ perl-ExtUtils-MakeMaker
  yum -y install gcc automake autoconf libtool make
  ```

* 测试gcc版本

  ```shell
  gcc --version
  ```

### 2. 获取安装包

```shell
wget https://download.redis.io/releases/redis-6.2.4.tar.gz
```

### 3. 解压缩

```shell
tar -zxvf redis-6.2.4.tar.gz
```

### 4. 进入目录执行make

```shell
cd redis-6.2.4
make
```

### 5. 如果失败

```shell
make distclean #清理
make
```

### 6. make install

确定make没问题之后执行```make install```

### 7. 安装目录说明

进入安装目录：/usr/local/bin

* redis-benchmark：性能测试工具，可以在自己本子运行，看看自己本子性能如何
* redis-check-aof：修复有问题的AOF文件，rdb和aof后面讲
* redis-check-dump：修复有问题的dump.rdb文件
* redis-sentinel：Redis集群使用
* redis-server：Redis服务器启动命令
* redis-cli：客户端，操作入口

### 8. 启动(可以将目录配置到环境变量中)

* 前台启动：（不推荐）

  ```shell
  redis-server
  ```

  

* 后台启动:(推荐)：

  1. 备份redis.conf

     ```shell
     mkdir -p /opt/config
     cp /opt/redis-6.2.4/redis.conf /opt/config
     ```

  2. 修改*daemonize no*改成*yes*

  3. redis启动

     ```shell
     redis-server/opt/config/redis.conf
     ```

* 客户点启动

  ```shell
  redis-cli
  redis-cli -p6379 #指定端口
  ```

* 关闭客户端

  ```shell
  shutdown
  ```

