1. 设置外网可以访问

   * 开放端口

     * 关闭防火墙：```systemctl stop firewalld.service```
     * 开放6379端口：iptables -I INPUT -p tcp --dport 6379 -j ACCEPT

   * 修改redis.conf

     ```shell
     bind 127.0.0.1 # 注释这一行
     protected-mode no # 关闭保护模式
     ```

   * 重启redis

     

2. 单位

   ```shell
   1k => 1000 bytes
   1kb => 1024 bytes
   1m => 1000000 bytes
   1mb => 1024*1024 bytes
   1g => 1000000000 bytes
   1gb => 1024*1024*1024 bytes
   ```

   * 大小不敏感

3. 网络

   ```shell
   bind 127.0.0.1 # 绑定的ip 
   protected-mode yes # 保护模式 
   port 6379 # 端口设置
   ```

4. 通用

   ```shell
   daemonize yes # 以守护进程的方式运行，默认是 no，我们需要自己开启为yes！ 
   pidfile /var/run/redis_6379.pid # 如果以后台的方式运行，我们就需要指定一个 pid 文件！
   loglevel notice logfile "" # 日志的文件位置名 
   databases 16 # 数据库的数量，默认是 16 个数据库 
   always-show-logo yes # 是否总是显示LOGO
   ```

5. 快照

   持久化， 在规定的时间内，执行了多少次操作，则会持久化到文件 .rdb. aof

   ```shell
   # 如果900s内，如果至少有一个1 key进行了修改，我们及进行持久化操作 
   save 900 1 
   # 如果300s内，如果至少10 key进行了修改，我们及进行持久化操作 
   save 300 10 
   # 如果60s内，如果至少10000 key进行了修改，我们及进行持久化操作 
   save 60 10000 # 我们之后学习持久化，会自己定义这个测试！ 
   stop-writes-on-bgsave-error yes # 持久化如果出错，是否还需要继续工作！
   rdbcompression yes # 是否压缩 rdb 文件，需要消耗一些cpu资源！ 
   rdbchecksum yes # 保存rdb文件的时候，进行错误的检查校验！ 
   dir ./ # rdb 文件保存的目录！
   ```

6. 安全

   ```shell
   requirepass foobared # 设置密码
   ```

7. 限制

   ```shell
   maxclients 10000 # 设置能连接上redis的最大客户端的数量 
   maxmemory <bytes> # redis 配置最大的内存容量 
   maxmemory-policy noeviction # 内存到达上限之后的处理策略 
   1、volatile-lru：只对设置了过期时间的key进行LRU（默认值） 
   2、allkeys-lru ： 删除lru算法的key 
   3、volatile-random：随机删除即将过期key 
   4、allkeys-random：随机删除 
   5、volatile-ttl ： 删除即将过期的 
   6、noeviction ： 永不过期，返回错误
   ```

8. APPEND ONLY 模式 aof配置

   ```shell
   appendonly no # 默认是不开启aof模式的，默认是使用rdb方式持久化的，在大部分所有的情况下， rdb完全够用！ 
   appendfilename "appendonly.aof" # 持久化的文件的名字 
   #appendfsync always # 每次修改都会 sync。消耗性能 
   appendfsync everysec # 每秒执行一次 sync，可能会丢失这1s的数据！ 
   # appendfsync no # 不执行 sync，这个时候操作系统自己同步数据，速度最快！
   ```

