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

* 镜像命令
* 容器命令
* 操作命令

## Docker镜像

## 容器数据卷

## DockerFile

## Docker网络原理

## IDEA整合Docker

## Docker Compose

## Docker Swarm

简化版的k8s

## CI\CD jenkins



