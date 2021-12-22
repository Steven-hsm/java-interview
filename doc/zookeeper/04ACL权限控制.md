zookeeper类似于文件系统，client可以创建、更新、删除节点。但是如何做到节点的权限控制呢，zookeeper提供了access control list访问控制列表。ACL 权限可以针对节点设置相关读写等权限，保障数据安全性。permissions 可以指定不同的权限范围及角色。

**ACL权限控制**，通过 ```[scheme:id:permissions]``` 来构成权限列表。

* 权限模式（schema）：授权的策略，采用的某种权限机制，包括 world、auth、digest、ip、super 几种。
* 授权对象（id）：授权的对象，代表允许访问的用户
* 权限（permission）：授予的权限，由 cdrwa 组成，其中每个字母代表支持不同权限， 创建权限 create(c)、删除权限 delete(d)、读权限 read(r)、写权限 write(w)、管理权限admin(a)。

**ACL特性**

* zookeeper的权限控制是基于每个znode节点的，需要对每个节点设置权限
* 每个znode支持多种权限控制方案和多个权限
* 子节点不会继承父节点的权限，客户端无权访问某节点，但可能可以访问它的子节点

### 1. **权限模式**

| 方案   | 描述                                                    |
| ------ | ------------------------------------------------------- |
| world  | 只有一个用户：anyone，代表登录zookeeper的所有人（默认） |
| ip     | 对客户端使用ip地址认证                                  |
| auth   | 使用已添加的用户认证                                    |
| digest | 使用"用户名：密码"方式认证                              |

### 2. **授权对象** 

给谁授予权限，授权对象ID是指权限赋予的实体，例如：IP地址或用户

### 3. **授予的权限**

create、delete、read、writer、admin也就是增、删、改、查、管理权限，这5种权限简写为cdrwa。这5种权限中，delete是指对子节点的删除权限，其他4种权限是指对自身节点的操作权限。

| 权限   | ACL简写 | 描述                             |
| ------ | ------- | -------------------------------- |
| create | c       | 可以创建子节点                   |
| delete | d       | 可以删除子节点（仅下一级节点）   |
| read   | r       | 可以读取节点数据及显示子节点列表 |
| write  | w       | 可以设置节点数据                 |
| admin  | a       | 可以设置节点访问控制列表权限     |

### 4. **授权的相关命令**

| 命令    | 使用方式                               | 描述         |
| ------- | -------------------------------------- | ------------ |
| getAcl  | getAcl [-s] path                       | 读取ACL权限  |
| setAcl  | setAcl [-s] [-v version] [-R] path acl | 设置ACL权限  |
| addauth | addauth schema auth                    | 添加认证用户 |

### 5. demo

1. world授权模式(开放式权限)

   ```shell
   create /world-node testdata # 创建节点
   getAcl /world-node # 查看节点权限 'world,'anyone : cdrwa
   
   setAcl /world-node world:anyone:drwa #设置权限，这里其实删除了c create 权限
   create /world-node/node2 testdata # 创建节点,这里就无法创建了 Insufficient permission : /world-node/node
   
   ```

2. ip授权模式

   ```shell
   create /ip-node testdata # 创建节点
   getAcl /ip-node # 查看节点权限 'world,'anyone : cdrwa
   
   setAcl /ip-node ip:127.0.0.1:cdrwa #设置权限，其他节点无法访问  
   ```

3. auth授权模式

   auth 用于授予权限，注意需要先创建用户。

   ```shell
   create /auth-node testdata
   addauth digest user:123456
   setAcl /auth-node auth:user:123456:cdrwa
   getAcl /auth-node
   ```

4. digest授权模式

   digest 可用于账号密码登录和验证，digest和auth有两点区别。

   一是digest不用进行授权用户的添加，

   二是digest在授权过程中需要提供加密之后的密码。这里的密码是经过SHA1及BASE64处理的密文，在shell中用以下命令计算：

   ```shell
   echo -n wfj:123456 | openssl dgst -binary -sha1 | openssl base64
   ```

   ```shell
   create /digest-node test-node
   setAcl /digest-node digest:wfj:rljj942QO396u5a6EO/22EozxsI=:cdrwa
   
   addauth digest wfj:123456
   ```