## Docker Compose

Docker Compose来轻松高效的管理容器，定义运行多个容器

> 官网介绍
>
> 定义，运行多个容器
>
> YAML file配置文件
>
> single command 命令有哪些

步骤：（批量服务编排）

* DockerFile保证我们的项目在任何地方可以运行
* docker-compose.yml 这个文件怎么写
* 启动项目

compose是Docker官方的开源项目，需要安装

* 服务service,容器，应用
* 项目project。一组关联的容器

`Dockerfile`让程序在任何地方运行

**安装**

1. 下载

   ```shell
   curl -L https://get.daocloud.io/docker/compose/releases/download/1.24.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
   chmod +x /usr/local/bin/docker-compose
   docker-compose --version
   ```

2. 体验

   ```shell
   mkdir composetest 
   cd composetest
   ```

   app.py

   ```python
   import time
   
   import redis
   from flask import Flask
   
   app = Flask(__name__)
   cache = redis.Redis(host='redis', port=6379)
   
   def get_hit_count():
       retries = 5
       while True:
           try:
               return cache.incr('hits')
           except redis.exceptions.ConnectionError as exc:
               if retries == 0:
                   raise exc
               retries -= 1
               time.sleep(0.5)
   
   @app.route('/')
   def hello():
       count = get_hit_count()
       return 'Hello World! I have been seen {} times.\n'.format(count)
   ```

   requirements.txt

   ```txt
   flask
   redis
   ```

   dockerfile

   ```dockerfile
   FROM python:3.7-alpine
   WORKDIR /code
   ENV FLASK_APP=app.py
   ENV FLASK_RUN_HOST=0.0.0.0
   RUN apk add --no-cache gcc musl-dev linux-headers
   COPY requirements.txt requirements.txt
   RUN pip install -r requirements.txt
   EXPOSE 5000
   COPY . .
   CMD ["flask", "run"]
   ```

   docker-compose.yml

   ```shell
   version: "3.9"
   services:
     web:
       build: .
       ports:
         - "5000:5000"
       volumes:
         - .:/code
       environment:
         FLASK_ENV: development
     redis:
       image: "redis:alpine"
   ```

   ```shell
    docker-compose up
   ```

3. 多个服务器，集群。A,B -num 副本数量

4. 网络规则

   1. 项目中的内容都在同一个网络

**YAML文件规则**

docker-compose.yml 核心

```shell
# 3层
version：  	# 版本
service:	#服务
#其他配置
```

多写多看

**实战**

1. mkdir my_wordpress;	cd my_wordpress

2. docker-compose.yml

   ```yaml
   version: "3.9"
       
   services:
     db:
       image: mysql:5.7
       volumes:
         - db_data:/var/lib/mysql
       restart: always
       environment:
         MYSQL_ROOT_PASSWORD: somewordpress
         MYSQL_DATABASE: wordpress
         MYSQL_USER: wordpress
         MYSQL_PASSWORD: wordpress
       
     wordpress:
       depends_on:
         - db
       image: wordpress:latest
       ports:
         - "8000:80"
       restart: always
       environment:
         WORDPRESS_DB_HOST: db:3306
         WORDPRESS_DB_USER: wordpress
         WORDPRESS_DB_PASSWORD: wordpress
         WORDPRESS_DB_NAME: wordpress
   volumes:
     db_data: {}
   ```

3. ```
   docker-compose up -d
   ```

## Docker Swarm

集群方式的部署

简化版的k8s

## CI\CD jenkins

