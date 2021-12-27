### 1. 说明

默认情况下，MongoDB实例启动运行时是没有启用用户访问权限控制的，也就是说，在实例本机服务器上都可以随意连接到实例进行各种操作，MongoDB不会对连接客户端进行用户验证，这是非常危险的。

**保证安全**

- 使用新的端口，默认的27017端口如果一旦知道了ip就能连接上，不太安全
- 设置mongodb的网络环境，最好将mongodb部署到公司服务器内网，这样外网是访问不到的。公司内部访问使用vpn等
- 开启安全认证

为了强制开启用户访问控制(用户验证)，则需要在MongoDB实例启动时使用选项 --auth 或在指定启动配置文件中添加选项 auth=true 。

**概念**

1. 启用访问控制:MongoDB使用的是基于角色的访问控制(Role-Based Access Control,RBAC)来管理用户对实例的访问。通过对用户授予一个或多个角色来控制用户访问数据库资源的权限和数据库操作的权限，在对用户分配角色之前，用户无法访问实例。
2. 角色：在MongoDB中通过角色对用户授予相应数据库资源的操作权限，每个角色当中的权限可以显式指定，也可以通过继承其他角色的权限，或者两都都存在的权限。
3. 权限：权限由指定的数据库资源(resource)以及允许在指定资源上进行的操作(action)组成。

**基本命令**

```shell
db.runCommand({ rolesInfo: 1 }) # 查询所有角色权限(仅用户自定义角色)
db.runCommand({ rolesInfo: 1, showBuiltinRoles: true }) #查询所有角色权限(包含内置角色)
db.runCommand({ rolesInfo: "<rolename>" }) #查询当前数据库中的某角色的权限
db.runCommand({ rolesInfo: { role: "<rolename>", db: "<database>" } } #查询其它数据库中指定的角色权限
db.runCommand( { rolesInfo: [ "<rolename>", { role: "<rolename>", db: "<database>" }, ... ] } )#查询多个角色权限
db.runCommand({ rolesInfo: 1, showBuiltinRoles: true }) #查看所有内置角色
```

**常用的内置角色**

- 数据库用户角色：read、readWrite;
- 所有数据库用户角色：readAnyDatabase、readWriteAnyDatabase、userAdminAnyDatabase、dbAdminAnyDatabase
- 数据库管理角色：dbAdmin、dbOwner、userAdmin；
- 集群管理角色：clusterAdmin、clusterManager、clusterMonitor、hostManager；
- 备份恢复角色：backup、restore；
- 超级用户角色：root
- 内部角色：system

### 2. 创建角色

```shell
# 1. 使用无需访问控制的方式启动
mongo --host 192.168.106.128 --port 27017

# 2.创建角色
use admin
db.createUser({user:"myroot",pwd:"123456",roles:["root"]})
db.createUser({user:"myadmin",pwd:"123456",roles: [{role:"userAdminAnyDatabase",db:"admin"}]})
db.system.users.find()
db.dropUser("myadmin")
db.changeUserPassword("myroot", "123456") 
db.auth("myroot","12345")
# 3.配置
security: #开启授权认证 
	authorization: enabled
```

创建普通用户可以在没有开启认证的时候添加，也可以在开启认证之后添加，但开启认证之后，必须使用有操作admin库的用户登录认证后才能操作。底层都是将用户信息保存在了admin数据库的集合system.users中。

### 3. 注意点

- Mongodb存储所有的用户信息在admin 数据库的集合system.users中，保存用户名、密码和数据库信息。
- 如果不指定数据库，则创建的指定的权限的用户在所有的数据库上有效
- 创建普通用户可以在没有开启认证的时候添加，也可以在开启认证之后添加，但开启认证之后，必须使用有操作admin库的用户登录认证后才能操作。底层都是将用户信息保存在了admin数据库的集合