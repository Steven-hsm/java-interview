## docker安装

1. 拉取镜像

   ```shell
   docker pull docker.io/redis:6.0.9
   ```

2. 创建目录存放配置数据及配置

   ```shell
   mkdir -p /work/docker/redis/conf
   mkdir -p /work/docker/redis/data 
   ```

3.  修改redis配置文件

   ```shell
   vi /work/docker/redis/conf/redis.conf 	#从网上下载
   ```

4. 编辑启动脚本```start_redis.sh```

   ```shell
   docker stop myredis
   docker rm myredis
   docker run -d --privileged=true \
     --restart always \
     -v /work/docker/redis/conf/redis.conf:/etc/redis/redis.conf \
     -v /opt/docker/redis/conf/data:/data \
     --name myredis \
     -p 6379:6379 redis:6.0.9 --appendonly yes
   
   ```

5. 启动redis

   ```shell
   ./start_redis.sh
   ```

## 构建生产集

1. 修改系统参数 

   ```shell
   vi /etc/sysctl.conf
   
   #在尾部追加
   net.core.somaxconn=65535
   vm.overcommit_memory=1
   
   sysctl -p    #使修改生效
   ```

2. 禁用大内存页

   ```shell
   vi /etc/rc.d/rc.local
   
   #在尾部追加
   if test -f /sys/kernel/mm/transparent_hugepage/enabled; then
       echo never > /sys/kernel/mm/transparent_hugepage/enabled
   fi
   if test -f /sys/kernel/mm/transparent_hugepage/defrag; then
       echo never > /sys/kernel/mm/transparent_hugepage/defrag
   fi
   
   chmod u+x /etc/rc.d/rc.local  
   reboot # 需要重启内存
   ```
   
## 生产级别

1. 创建目录

   ```shell
   mkdir -p /work/docker/redis/standalone/{conf,data}
   ```

2. 修改文件权限

   ```shell
   touch /work/docker/redis/standalone/conf/redis.conf
   chmod 755 /work/docker/redis/standalone/conf/redis.conf
   ```

3. 编辑配置文件

   ```shell
   vi /work/docker/redis/standalone/conf/redis.conf
   
   #允许外网访问
   bind 0.0.0.0 
   #数据目录
   dir /data 
   #设置连接密码 
   requirepass 12345678
   #开启aof日志文件
   appendonly yes
   #禁用aof重写(当占用内存很大时)
   no-appendfsync-on-rewrite yes
   #auto-aof-rewrite-percentage 0
   ```

4. 编辑```start_redis_standlone.sh```

   ```shell
   docker stop myredis
   docker rm myredis
   docker run -d --name myredis -p 6379:6379 \
   --sysctl net.core.somaxconn=4096 \
   -v /work/docker/redis/standalone/data/:/data \
   -v /work/docker/redis/standalone/conf/redis.conf:/etc/redis/redis.conf \
   -v /etc/localtime:/etc/localtime \
   docker.io/redis:6.0.9 redis-server /etc/redis/redis.conf
   ```

5. 启动```redis```

   ```shell
   ./start_redis_standlone.sh
   ```

   