1. 数据库说明

   * redis默认有16个库，默认选中db 0
   * 简单命令：
     * select 切换数据库 
     * dbsize 查看数据库大小
     * flushdb 清除当前数据库
     * flushall 清除全部数据库

2. 内存模型

   redis是基于内存的，CPU不是redis的性能瓶颈，Redis的性能瓶颈主要在内存和网络带宽，既然可以用单线程就用单线程实现了。

   1. redis单线程为什么快?

      redis 是将所有的数据全部放在内存中的，所以说使用单线程去操作效率就是最高的，多线程（CPU上下文会切换：耗时的操作！！！），对于内存系统来说，如果没有上下文切换效率就是最高的！多次读写都是在一个CPU上的，在内存情况下，这个就是最佳的方案！

   2. redis是单线程吗？

      Redis 的单线程主要是指 Redis 的网络 IO 和键值对读写是由一个线程来完成的，这也是 Redis 对外 提供键值存储服务的主要流程。但 Redis 的其他功能，比如持久化、异步删除、集群数据同步等，其实是由额外的线程执行的。 

   3. redis单线程如何处理那么多的并发客户端连接？

      Redis的**IO多路复用**：redis利用epoll来实现IO多路复用，将连接信息和事件放到队列中，依次放到 文件事件分派器，事件分派器将事件分发给事件处理器。

      ```shell
      CONFIG GET maxclients # 查看redis支持的最大连接数
      ```

3. 数据类型及常用命令

   1. keys相关

      * keys *  查看所有的key值
      * set [key] [value] 设置键值
      * exists [key] 判断键值是否存在
      * move [key] [db] 移动某个键值到其他库
      * clear 清屏
      * get [key] 获取键值
      * expire [key] [time] 设置key的过期时间，单位为秒
      * ttl [key] 查看当前key的过期时间
      * type [key] 查看key的数据类型

   2. String字符串

      * set [key] [value] 设置键值对
      * append [key] [value] 往后追加字符串，没有会新建
      * strlen [key] 查看键值长度
      * incr [key] 自增1
      * incrby [key] [step] 设置步长自增
      * decr [key] 自减1
      * decrby [key] [step]  设置步长自减
      * getrange [key] [start] [end] 截取字符串
      * setrange [key] [start] [value] 替换指定未知开始的字符串
      * setex [key] [expire] [value] 设置值并设置过期时间
      * setnx [key] [value] 不存在就设值
      * mset [key1] [value1] [key2] [value2] ..... 批量设置键值
      * mget [key1] [key2] 批量查询键值
      * msetnx [key1] [value1] [key2] [value2] ..... 批量设置键值,不存在就塞值
      * getset [key] [value] 获取老的值并设置新值

   3. List 列表

      命令都是以L，R开头

      * lpush [key] [value] 插入列表头
      * lrange [key] [start] [end] 获取列表中的元素
      * rpush [key] [value] 插入列表尾
      * lpop [key] 移除第一个元素
      * rpop [key] 移除最后一个元素
      * lindex [key] [index] 获取第index个元素
      * lrem [key] [num] [value] 精确匹配删除指定的元素
      * ltrim [key] [start] [end] 截取指定长度，缓存中的值也会改变
      * rpoplpush [key1] [key2] 移除列表key1尾部的一个元素并放入列表key2的头部
      * lset [key] [index] [value] 修改指定索引值

   4. Set集合

      * sadd [key] [value] 往集合中插入值
      * smembers [key] 查看指定set的值
      * sismember [key] [value] 查看某个值是否在set中
      * scard [key] 返回集合大小
      * srem [key] [value] 移除集合指定元素
      * srandmember [key] 随机取出一个元素
      * srandmember [key] [num] 随机取出num个元素
      * spop [key]  随机取出某个元素并删除
      * smove [key1] [key2] [value] 移除key1的元素到key2中
      * sdiff [key1] [key2] 差集
      * sinter [key1] [key2] 交集
      * sunion [key1] [key2] 并集

   5. hash哈希

      * hset [key] [mkey] [mvalue] 往哈希中插入值
      * hset [key] [mkey] 获取哈希中的某个值
      * hset [key] [mkey1] [mvalue1] [mkey2] [mvalue2] ... 往哈希中批量插入值
      * hset [key] [mkey1] [mkey2] 获取哈希中的多个值
      * hgetall [key] 获取哈希中的所有值
      * hdel [key] [mkey] 删除hash中的某个值
      * hlen [key] 获取哈希中的字段数量
      * hexists [key] [mkey] 判断是否存在某个key值
      * hkeys [key] 获取哈希中的所有字段
      * hvals [key] 获取哈希中的所有值
      * hincyby [key] [mkey] 哈希中对应的字段值自增
      * hsetnx [key] [mkey] [mvalue] 不存在可以设置，存在就不能设置

   6. zset有序集合

      * zadd [key] [score] [value] 往有序集合中添加元素，并添加排序指标
      * zrange [key] [start] [end] 获取有序集合元素
      * zrangebyscore [key]-inf +inf 获取全部的集合，从小到大
      * zrevrange [key] [start] [end] 倒序获取集合元素
      * zrem [key] [value] 移除某个元素
      * zcard [key] 获取集合中的元素
      * zcount [key] [start] [end] 指定区间的成员数量

