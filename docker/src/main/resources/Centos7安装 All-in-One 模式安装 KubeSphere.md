公司用KubeSphere有一段时间了，之前我只是在大佬的搭建好的平台上使用，现在准备自己搭建自己的环境

其中踩了不少的坑，之前是centos8安装，但是失败了。后来看网上是centos7的文档比较多，就重新装了一个centos7的虚拟机

跟着官方一步步安装的时候也踩了不少坑，现在就按照踩完坑的步骤安装 [官网地址](https://kubesphere.io/zh/docs/quick-start/all-in-one-on-linux/)

### 1. 安装centos7

这里不做赘述，如果是虚拟机安装的话，一定要把处理器核心数设置在 2以上，这是k8s安装配置要求的

### 2. 配置 ip

```shell
 vi /etc/sysconfig/network-scripts/ifcfg-ens33
 ## 简单的就是设置为静态ip
 ONBOOT=yes
```

### 3. 禁用selinux（坑 一）

```shell
vi /etc/sysconfig/selinux
## 这是设置一个属性为disabled
SELINUX=disabled
```

配置完之后需要重启 reboot

### 4. 官网要求

（1）节点必须能够通过 SSH 连接。

（2）节点上可以使用 sudo/curl/openssl 命令。

（3）docker 可以由您自己安装或由 KubeKey 安装。

注意：如果你想离线安装 KubeSphere，请务必提前安装好 docker。

**安装要求的依赖**

```sh
yum install socat conntrack ebtables ipset
```

### 5. 关闭防火墙（坑二）

```shell
systemctl stop firewalld.service # 停止
systemctl disable firewalld.service # 禁用防火墙，下次启动也不会开启
```

### 6. 安装docker（坑三）

```shell
# 1. 安装docker
yum  install docker docker.io
# 2. 配置docker 源
vim /etc/docker/daemon.json
{
        "registry-mirrors": [
                "https://registry.docker-cn.com"
        ]
}
# 3. 重载docker
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### 7. 安装KubeKey

```shell
# 先执行以下命令以确保从正确的区域下载 KubeKey
export KKZONE=cn
# 执行以下命令下载 KubeKey
curl -sfL https://get-kk.kubesphere.io | VERSION=v1.0.1 sh -
# 为kk添加可执行权限
chmod +x kk
```

### 8. 安装Kubernetes和kubesphere

```shell
# 安装
./kk create cluster --with-kubernetes v1.17.9 --with-kubesphere v3.0.0
```

**验证结果**

```shell
kubectl logs -n kubesphere-system $(kubectl get pod -n kubesphere-system -l app=ks-install -o jsonpath='{.items[0].metadata.name}') -f
```

```shell
**************************************************
#####################################################
###              Welcome to KubeSphere!           ###
#####################################################
Console: http://192.168.106.135:30880
Account: admin
Password: P@88w0rd
NOTES：
  1. After logging into the console, please check the
     monitoring status of service components in
     the "Cluster Management". If any service is not
     ready, please wait patiently until all components
     are ready.
  2. Please modify the default password after login.
#####################################################
https://kubesphere.io             2021-08-28 12:20:55
#####################################################
```

### 9.登录

![image-20210828122637067](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210828122637067.png)