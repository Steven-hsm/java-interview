### 1. ERROR: child process failed, exited with 48

**问题：**

启动mongodb的时候，发现起不来，报错：child process failed, exited with error number 48然后先去/var/log/mongo/mongod.log 查看启动的日志

**可能原因：**

应该是没有正常关闭mongodb引起的，比如直接 kill -9 <pid>导致

**解决方法：**

1.找到mongod.lock文件，并删除mongod.lock



2.以修复方式启动mongodb

```
/usr/bin/mongod -f /etc/mongod.conf --repair
```

3.然后接着在启动一次

```
/usr/bin/mongod -f /etc/mongod.conf --auth
```

4.查看进程是否运行

```
ps aux|grep mongo
```

**正确关闭mongodb的方法**

```shell
mongod  --shutdown  --dbpath /root/soft/mongodb5.0.5/data/db
```



> warning：千万不能使用kill -9 <pid>,因为MongoDB使用mmap方式进行数据文件管理，也就是说写操作基本是在内存中进行，写操作会被每隔60秒(syncdelay设定)的flush到磁盘里。如果在这60秒内flush处于停止事情我们进行kill -9那么从上次flush之后的写入数据将会全部丢失。
> 如果在flush操作进行时执行kill -9则会造成文件混乱，可能导致数据全丢了，启动时加了repair也无法恢复.

### 2. Cannot assign requested address

报错

```json
{"t":{"$date":"2021-12-26T22:42:43.309-05:00"},"s":"E",  "c":"CONTROL",  "id":20568,   "ctx":"initandlisten","msg":"Error setting up listener","attr":{"error":{"code":9001,"codeName":"SocketException","errmsg":"Cannot assign requested address"}}}
```

最后发现是自己的ip配置错了。

* bindIP应该是主机的ip地址或主机名。
* mongod不能绑定其他主机的ip地址。
* 这应该是每个主机的相关部分。
