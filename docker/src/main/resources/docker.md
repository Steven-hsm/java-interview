<h1><center>Docker</center></h1>

[学习地址]( https://www.bilibili.com/video/BV1og4y1q7M4?p=2&amp;spm_id_from=pageDriver）


## Docker 概述

**Docker为什么会出现**

* 一款产品，多套环境，开发环境，测试环境，线上环境
* 开发，运维，版本更新导致服务不可用，对于运维来说，考验十分大
* 环境配置是十分的复杂

那么项目能不能带上环境安装打包

传统：开发给jar包，运维来做 --》 现在：开发打包部署上线，一套流程做完

**Docker的思想**

类似于一个集装箱，将不同应用隔离，将应用打包

通过隔离机制，可以将服务器利用到极致

**Docker 的历史**

2010年，几个搞IT的年轻人，就在美国成立了一家公司 `dotCloud`

做一些pass的云计算服务！LXC有关的容器技术

他们将自己的技术（容器化技术）命令为Docker

`开源`

2013 开放源代码，火了，Docker每个月都会更新一个版本

2014年4月9号，Docker1.0发布！

* Docker 为什么这么火
  * 也是一种虚拟化技术
  * 隔离，镜像十分小巧，运行镜像即可
* 虚拟机
  * 通过软件虚拟一台或者多台电脑，很笨重
  * linux centos原生镜像（一台电脑）隔离，需要开启多个虚拟机

现在所有的开发人员都必须会Docker

> 聊聊Docker

* Docker是基于Go语言开发的，开源项目
* 官网：https://docs.docker.com/
* 仓库地址：https://hub.docker.com/

> Docker能干什么

容器化技术

* 虚拟机技术缺点
  * 资源占用十分多
  * 冗余步骤多
  * 启动很慢
* 容器化技术
  * 容器化技术不是模拟一个完整的操作系统

比较Docker和虚拟机技术的不同

* 传统虚拟机，虚拟出一条硬件，运行一个完整的操作系统，然后在这个系统上安装和运行软件
* 容器内的应用直接运行在宿主主机的内容，容器没有自己的内核，也没有虚拟我们的硬件，所以就轻便
* 每个容器是互相隔离，每个容器内部都有一个属于自己的文件系统，互不影响

> DevOps(开发、运维)

传统：一堆帮助文档，安装程序

Docker：打包镜像发布测试，一键运行

> 更便捷的升级和扩容

使用了Docker之后，我们部署应用就和搭积木一样

项目打包为一个镜像，扩展服务器A,服务器B

> 更简单的系统运维

在容器化之后，我们的开发，测试环境都是高度一致的

> 更高效的计算资源利用

Docker是内核级别的虚拟化，可以在一个物理机上运行多个容器实例，服务器的性能可以被压榨到极致



## Docker安装

**Docker的基本组成**

![image-20210315230000070](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210315230000070.png)

* 镜像（image）：
  * Docker镜像就好比是一个模板，可以通过这个模板来创建容器服务
  * 通过这个镜像可以创建多个容器
* 容器（container）
  * Docker利用容器技术，独立运行一个或者一组应用，通过镜像创建的
  * 启动，停止，删除，基本命令
  * 目前可以吧容器简单理解为一个简易的linux系统
* 仓库（repository）
  * 仓库是用来存放镜像的地方
  * 仓库分为公有仓库和私有仓库
  * Docker hub（默认是国外的）
  * 阿里云   都有容器服务器，配置镜像加速

**安装docker**

```shell
#系统内核
uname -r
#系统版本
cat /etc/os-release
```

> 安装

1. 卸载旧的版本

   ```shell
   sudo yum remove docker \
                     docker-client \
                     docker-client-latest \
                     docker-common \
                     docker-latest \
                     docker-latest-logrotate \
                     docker-logrotate \
                     docker-engine
   ```

2. 需要的安装包

   ```shell
   yum install -y yum-utils
   ```

3. 设置镜像的仓库(国外镜像比较慢，可以修改为阿里)

   ```shell
   sudo yum-config-manager \
       --add-repo \
       https://download.docker.com/linux/centos/docker-ce.repo
       
   sudo yum-config-manager \
       --add-repo \
       https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
   ```

4. 更新yum软件包索引

   ```shell
   yum makecache fast
   ```

5. 安装docker

   ```shell
   yum install docker-ce docker-ce-cli containerd.io
   ```

6. 启动docker

   ```shell
   systemctl start docker
   ```

7. 运行hello-world

   ```shell
   docker run hello-world
   ```

**卸载Docker**

1. 卸载依赖

   ```shell
   yum remove docker-ce docker-ce-cli containerd.io
   ```

2. 删除资源

   ```shell
   rm -rf /var/lib/docker # docker的默认工作路径
   ```

**阿里云镜像加速**

**回顾hello-world**

![image-20210315233513676](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210315233513676.png)



## Docker 命令

* 帮助命令

  ```shell
  docker version 		# 显示docker的版本信息
  docker info 		# 显示docker的系统信息，包括镜像和容器数量
  docker 命令 --help   # 帮助命令
  ```

  帮助文档，看官方文档

* 镜像命令

  ```shell
  docker images   # 查看所有本地的主机上的镜像
  # 可选项
  -a ,--all 		# 列出所有的镜像
  -q ,--quiet		# 只显示镜像的id
  
  docker search 镜像	# 搜索镜像
  # 可选项
  --filter=stars=3000
  
  docker pull 镜像:tag 	# 下载镜像
  # docker是分层下载，docker image的核心，联合文件系统
  
  docker rmi 			# 删除镜像
  docker rmi -f $(docker images -aq) 	# 删除全部的容器
  ```

* 容器命令

  * 我们有了镜像才可以创建容器

  ```shell
  docker run [可选参数] image # 运行镜像，运行起来的即为容器
  ## 参数说明
  --name="Name"		# 容器名称
  -d					# 后台守护进程的方式运行
  -it					# 使用交互方式运行，进入容器查看内容
  -p					# 指定容器的端口
  	-p 主机端口：容器端口
  -P					# 指定随机端口
  
  docker ps				# 列出当前正在运行的容器
  ## 可选参数
  -a				# 列出当前正在运行的容器+带出历史运行过的容器
  -n=?			# 显示最近创建的容器
  -q				# 只显示容器的编号
  
  docker rm 容器id	# 删除容器,不能删除正在运行中的容器，需要加-f参数
  docker rm -f $(docker ps -aq) # 删除所有的容器
  docker ps -a -q | xargs docker rm # 删除所有的容器
  
  docker start 容器id 	# 启动容器
  docker restart 容器id # 重启容器
  docker stop 容器id	# 停止当前正在运行的容器
  docker kill 容器id	# 强制停止当前容器
  ```

  

* 其他常用操作命令

  * docker容器使用后台运行，就必须有一个前台进程，docker发现没有应用，就会自动停止

  ```shell
  docker logs -f -t --tail 100 容器
  # 可选参数
-tf				# 显示日志
  --tail number 	# 显示日志条数
  
  docker top 容器	# 查看容器的进程信息
  
  docker inspect 容器# 查看容器的信息
  
  docker exec -it 容器 /bin/bash	# 进入当前正在运行的容器，开启一个新的终端，可以在里面操作
  
  docker attach -it 容器			# 进入正在执行的代码。不会启动新的进程
  
  docker cp 容器id:容器内路径 容器外路径	# 将容器的数据拷贝到外部数据
  ```
  
  ![image-20210317215742076](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210317215742076.png)

## 可视化

* portainer
  * docker图像化管理工具，提供一个后台面板供我们操作
* Rancher（CI/CD再用）

## Docker镜像

**镜像是什么**

镜像是一种轻量级、可执行的独立软件包，用来打包软件运行环境和基于运行环境开发的软件，它包含运行某个软件所需的所有内容，包括代码，运行时库，环境变量和配置环境

所有的应用，直接打包docker镜像，就可以直接运行

> 获取镜像

* 远程仓库
* 拷贝
* 自己制作

**UnionFS(联合文件系统)**

UnionFS（联合文件系统）：Union文件系统是一种分层、轻量级并且高性能的文件系统，它支持对文件系统的修改作为一次提交来一层层的叠加，同时可以将不同目录挂载到同一个虚拟文件系统下，Union文件系统是Dokcer镜像的基础。镜像可以通过分层来进行继承，基于基础镜像（没有父镜像），可以制作各种具体的镜像。

特性：一次同时加载多个文件系统，但从外面看起来，只能看到一个文件系统，联合加载会把各层文件系统加载起来，这样最终的文件系统会包含所有的底层文件和目录

**Docker镜像加载原理**

docker的镜像实际上是由一层一层的文件系统构成，这种层级的文件系统UnionFS。

主要包含bootloader和kernelbootloader主要是引导加载kernel，Linux刚启动时会加载bootfs文件系统，在Docker镜像的最底层是bootfs。这一层与我们典型的linux/unix系统是一样的，包含boot加载器内核。当boot加载完之后整个内核就都在内存中了，此时内存的使用权已经由bootfs交给内核了，此时系统也会卸载bootfs

roorfs （root file system），在bootfs之上。包含的就是典型Linux系统中的 /dev ，/proc，/bin ，/etx 等标准的目录和文件。rootfs就是各种不同的操作系统发行版。比如Ubuntu，Centos等等。

　　对于一个精简的OS，rootfs可以很小，只需要包括最基本的命令、工具和程序库就可以了，因为底层直接用Host（宿主机）的kernel，自己只需要提供rootfs就行了，由此可见对于不同的Linux发行版，bootfs基本是一致的，rootfs会有差别，因此不同的发行版可以公用bootfs。

> 特点

docker 镜像都是只读的，当容器启动时，一个新的可写层呗加载到镜像的顶部

这一层就是我们通常说的容器层，容器之下的都叫镜像层

**提交自己的镜像**

```shell
docker commit # 提交容器称为一个新的副本
docker commit -m="提交的描述信息" -a="作者" 容器id 镜像名：tag
```

## 容器数据卷

**什么是容器数据卷**

数据在容器中，删除容器之后，数据就会丢失

容器之间可以有一个数据共享的技术！Docker容器中产生的数据，同步到本地

卷技术，目录的挂载，将我们容器类的目录，挂载到Linux上面

> 总结一句话：容器的持久化和同步操作！容器间也可以数据共享

**使用数据卷**

* 命令挂载（本地修改后，容器中就会自动修改）

  ```shell
  docker run -it -v 主机目录：容器目录
  ```

* mysql 实战

  ```shell
  docker run -d -p 3306:3306 -v /home/mysql/conf:/etc/mysql/conf.d -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --name mysql mysql:5.7
  ```

* 具名挂载与匿名挂载

  ```shell
  # 匿名挂载
  -v 容器内路径
  docker run -d -P --name nginx01 -v /etc/nginx nginx
  
  docker volumn ls	# 查看所有的volumn的情况
  docker inspect 容器	# 查看具体的映射
  
  # 具名挂载 
  docker run -d -P --name nginx02 -v juming-nginx:/etc/nginx nginx
  
  所有的docker容器内的卷，没有指定目录的情况下都在/var/lib/docker/volumes/xxxxx/_data
  # 读写权限
  可以 -v 容器内路径：ro rw 改变读写权限
  ro read only 
  rw read write
  ```

  

## DockerFile

就是用来构建docker镜像的构建文件！命令脚本(通过脚本生成镜像)

dockerfile01

```shell
FROM centos

VOLUME ["volume01","volume02"]
CMD echo "-----end-----"
CMD /bin/bash
```

创建镜像

```shell
docker build -f dockerfile01 -t kuangshen/centos:1.0 .
```

查看匿名挂载的卷

```shell
docker inspect
```

**构建步骤**

1. 编写一个dockerfile文件
2. docer build构建成为一个镜像
3. docker run 运行镜像
4. docker push 发布镜像

很多官方镜像都是基础包，很多功能没有。我们通常会自己搭建自己的镜像

**DockerFile构建过程**

* 基础知识：
  * 每个保留关键字（指令）都必须是大写字母
  * 执行从上到下顺序执行
  * #表示注释
  * 每个指令都会创建提交一个新的镜像层，并提交
* dockerfile是面向开发的，我们以后要发布项目，做镜像，就需要编写dockerfile文件，这个文件十分简单
* 步骤
  * DockerFile: 构建文件，定义了一切的步骤，源代码
  * DockerImages：通过DockerFile构建生成的镜像，最终发布和运行的产品
  * Docker容器：容器是镜像运行起来提供服务的

**DockerFile的指令**

```shell
FROM 		# 基础镜像，一起从这里开始构建
MAINTAINER	# 镜像作者，姓名+邮箱
RUN			# 镜像构建的时候需要运行的命令
ADD			# 步骤：tomcat镜像，这个tomcat压缩包，添加内容
WORKDIR		# 镜像的工作目录
VOLUME		# 挂载的目录
EXPOST		# 暴露端口配置
CMD			# 指定这个容器启动的时候要运行的命令，只有最后一个会生效，可被替代
ENTRYPOINT	# 指定这个容器启动的时候要运行的命令，可以追加命令
ONBUILD		# 当构建一个被继承DockerFile这个时候就会运行ONBUILD的指令，触发指令
COPY		# 类似ADD，将文件拷贝到镜像中
ENV			# 构建的时候设置环境变量
```

Docker hub中99%镜像都是从这个基础镜像过来的 FROM scratch,然后配置需要的软件和配置来进行的构建

> CMD和ENTRYPOINT区别
>
> CMD指定这个容器启动的时候要运行的命令，只有最后一个会生效，可被替代
>
> ENTRYPOINT指定这个容器启动的时候要运行的命令，可以追加命令。追加命令直接放在ENTRYPOINT 命令后



## Docker网络

**网络**

docker0

```shell
ip addr
lo 		本地回环地址
eth0 	外网地址
docker0	docker0地址
```

**docker如何处理容器间网络的通信**

容器启动的时候会得到一个eth0@if262ip地址，docker分配的

> 原理
>
> 1. 每启动一个docker容器，docker就会给docker容器分配一个ip，我们只要安装了docker，就会有一个网卡docker0，桥接模式，使用的技术是evth-pair技术
> 2. evth-pair。就是一堆的虚拟设备接口，他们都是成对出现的，一段连着协议，一段彼此相连。充当一个桥梁
> 3. 容器和容器之间是可以ping通的
> 4. docker0类似一个路由器

![image-20210319223825742](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210319223825742.png)

Docker 中的所有网络接口都是虚拟的。虚拟的转发效率高

**-- link**

通过--link可以解决网络连通问题

**-- 自定义网络**

网络模式

* 桥接模式 bridge docker 默认
* none 不配置网络
* host 和宿主机共享网络
* container：容器网络连通，用得少，局限很大

docker0 特点：默认域名不能访问 --link可以打通链接

```shell
docker network create --driver bridge --subnet 192.168.0.0/16 192168.0.1 mynet
```

自定义的网络docker都已经帮我们维护好了对应的关系，推荐平时这样使用



# 企业实战

## IDEA整合Docker

## Docker Compose

## Docker Swarm

简化版的k8s

## CI\CD jenkins



