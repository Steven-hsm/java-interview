### 1.介绍

#### 1.1 发展经历

Infrastructure as a Service  基础设施即服务 IaaS

platform as a service 平台即服务 PaaS

software as a service 软件即服务 SaaS

* 资源管理器

-> apache mesos（开源的分布式资源管理框架） 2019-05 twitter -》 kubernets

-> docker swarm 2019-07 阿里云宣布  docker swarm 剔除

-> kubernets(k8s,功能全面) google 10年容器化基础框架 borg GO 语言 Borg

* 特点

  轻量级：消耗资源校

  开源：

  弹性伸缩：

  负载均衡：IPVS

#### 1.2 组件说明

高可用集群副本数据最好是 >=3  奇数

ApiServer:所有服务访问入口

ControllerManager：维持副本期望数目

Schedule：负责介绍任务，选择合适的节点进行分配任务

ETCD:的官方将它定位成一个可信赖的分布式键值存储服务，它能够为整个分布式集群存储一些关键数据，协同分布式集群的正常运转

kubelet:直接跟容器引擎交互实现容器的生命周期管理

kube-proxy:负责写入规则至IPTABLES、IPVS，实现服务映射访问

COREDNS：可以为集群中的SVC创建一个域名IP的对应关系解析

DASHBOARD：给K8s集群提供一个B/S结构访问体系

INGRESS CONTROLLER:官方只能实现四层代理，INGRESS可以实现七层代理

FEDERATION：提供一个可以跨集群中心多k8s统一管理功能

PROMETHEUS：提供k8s集群的监控能力

ELK：提供k8s集群日志统一分析介入平台

### 2. 基础概念

1. pod
   * 自主式Pod
   * 控制器管理的Pod
2. 



