本机环境：centos8

参考博客：https://blog.csdn.net/witton/article/details/107085155

### 1. 环境准备

1. 卸载podman

   centos8默认安装了podman容器，它和docker可能存在冲突，最好卸载掉

   ```shell
   sudo yum remove podman
   ```

2. 关闭交换区

   ```shell
   sudo swapoff -a #临时关闭 
   sudo sed -i 's/.*swap.*/#&/' /etc/fstab #永久关闭交换区
   ```

3. 禁用selinux

   ```shell
   setenforce 0 #临时关闭
   sed -i "s/^SELINUX=enforcing/SELINUX=disabled/g" /etc/selinux/config #永久关闭
   ```

4. 关闭防火墙

   ```shell
   sudo systemctl stop firewalld.service
   sudo systemctl disable firewalld.service
   ```

### 2. k8s安装

1. 配置系统基本安装源

   ```shell
   sudo curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-8.repo
   ```

2. 添加K8s安装源

   将以下内容保存到/etc/yum.repos.d/kubernetes.repo

   ```shell
   [kubernetes]
   name=Kubernetes
   baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
   enabled=1
   gpgcheck=1
   repo_gpgcheck=1
   gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
   ```

3. 安装docker

   ```shell
   sudo yum install -y yum-utils device-mapper-persistent-data lvm2 net-tools
   sudo yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
   yum -y install docker-ce
   ```

   使用docker加速

   ```shell
   mkdir -p /etc/docker
   vim /etc/docker/daemon.json
   {
      "registry-mirrors" : ["https://mj9kvemk.mirror.aliyuncs.com"]
   }
   ```

4. 安装kubectl、kubelet、kubeadm

   ```shell
   sudo yum install -y kubectl kubelet kubeadm
   sudo systemctl enable kubelet
   sudo systemctl start kubelet
   
   kubeadm version
   kubectl version --client
   kubelet --version
   ```


5. 初始化kubernetes集群

   ```shell
   kubeadm init --apiserver-advertise-address=0.0.0.0 \
   --apiserver-cert-extra-sans=127.0.0.1 \
   --image-repository=registry.aliyuncs.com/google_containers \
   --ignore-preflight-errors=all \
   --kubernetes-version=v1.21.1 \
   --service-cidr=10.10.0.0/16 \
   --pod-network-cidr=10.18.0.0/16
   ```

   可能会报如下错误

   ```shell
   detected “cgroupfs” as the Docker cgroup driver
   ```

   查看docker信息

   ```shell
   docker info | grep Cgroup
   ```

   修改驱动

   ```shell
   /usr/lib/systemd/system/docker.service
   
   # 在ExecStart命令中添加
   --exec-opt native.cgroupdriver=systemd
   ```

   重启服务

   ```shell
   systemctl daemon-reload
   systemctl restart docker
   docker info | grep Cgroup
   ```

   重新执行初始化工作

   ```shell
   kubeadm init --apiserver-advertise-address=0.0.0.0 \
   --apiserver-cert-extra-sans=127.0.0.1 \
   --image-repository=registry.aliyuncs.com/google_containers \
   --ignore-preflight-errors=all \
   --kubernetes-version=v1.21.1 \
   --service-cidr=10.10.0.0/16 \
   --pod-network-cidr=10.18.0.0/16
   ```

6. 部署后续

   如果出现错误可以根据提示自行修复

   创建目录：

   ```shell
   mkdir -p $HOME/.kube
   sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
   sudo chown $(id -u):$(id -g) $HOME/.kube/config
   ```

   获取节点信息

   ```shell
   kubectl get node
   kubectl get pod --all-namespaces
   ```
   
7. 安装calico网络

   ```shell
   kubectl apply -f https://docs.projectcalico.org/manifests/calico.yaml
   ```

8. 安装kubernetes-dashboard

   ```shell
   wget https://raw.githubusercontent.com/kubernetes/dashboard/master/aio/deploy/recommended.yaml
   # 修改文件
   vi recommended.yaml
   kind: Service
   apiVersion: v1
   metadata:
     labels:
       k8s-app: kubernetes-dashboard
     name: kubernetes-dashboard
     namespace: kubernetes-dashboard
   spec:
     type: NodePort #添加这行
     ports:
       - port: 443
         targetPort: 8443
         nodePort: 30000    #添加这行
     selector:
       k8s-app: kubernetes-dashboard
   ```

   创建pod

   ```shell
   kubectl create -f recommended.yaml
   kubectl get svc -n kubernetes-dashboard
   ```

### 3.web创建pod

web页面登录 https://ip:30000/#/login

#### 3.1 token登录

1. 创建Token

   ```shell
   kubectl create sa dashboard-admin -n kube-system
   ```

2. 授权Token访问权限

   ```shell
   kubectl create clusterrolebinding dashboard-admin --clusterrole=cluster-admin --serviceaccount=kube-system:dashboard-admin
   ```

3. 获取Token

   ```shell
   ADMIN_SECRET=$(kubectl get secrets -n kube-system | grep dashboard-admin | awk '{print $1}')
   DASHBOARD_LOGIN_TOKEN=$(kubectl describe secret -n kube-system ${ADMIN_SECRET} | grep -E '^token' | awk '{print $2}')
   echo ${DASHBOARD_LOGIN_TOKEN}
   ```

#### 3.2 部署服务

```shell
kubectl taint nodes --all node-role.kubernetes.io/master-
```

#### 3.3常用Token命

```shell
kubeadm token list #查看Token
kubeadm token create #创建Token
kubeadm token delete TokenXXX #删除 Token
kubeadm token create --print-join-command #初始化master节点时，node节点加入集群命令

token=$(kubeadm token generate)
kubeadm token create $token --print-join-command --ttl=0

kubeadm token list | awk -F" " '{print $1}' |tail -n 1 # 打印第一行

kubectl describe secrets -n kube-system $(kubectl -n kube-system get secret | awk '/dashboard-admin/{print $1}')
```





