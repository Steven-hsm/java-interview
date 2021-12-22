1. 创建zookeeper 节点命令

   ```shell
    create [‐s] [‐e] [‐c] [‐t ttl] path [data] [acl]
    #创建持久化节点
    create /test‐node some‐data
    #创建临时节点
    create -e /temp-code temp-data
    #创建序号节点，加参数 -s
    create -s /seq-code seq-data
    #创建容器节点
    create -c /container
   ```

   -s: 顺序节点 

   -e: 临时节点 

   -c: 容器节点 

   -t: 可以给节点添加过期时间，默认禁用，需要通过系统参数启用 

2. 查看节点

   ```shell
   get /test-node
   ```

3. 修改节点

   ```shell
   set /test‐node some‐data‐changed
   ```

4. 查看节点状态

   ```shell
   stat /test-node
   ```

   * cZxid：创建znode的事务ID（Zxid的值）。 

   * mZxid：最后修改znode的事务ID。 

   * pZxid：最后添加或删除子节点的事务ID（子节点列表发生变化才会发生改变）。 
   * ctime：znode创建时间。 
   * mtime：znode最近修改时间。 
   * dataVersion：znode的当前数据版本。 
   * cversion：znode的子节点结果集版本（一个节点的子节点增加、删除都会影响这个 版本）。
   * aclVersion：表示对此znode的acl版本。 
   * ephemeralOwner：znode是临时znode时，表示znode所有者的 session ID。 如果 znode不是临时znode，则该字段设置为零。 
   * dataLength：znode数据字段的长度。
   * numChildren：znode的子znode的数量。 

5. 查看节点状态的同事查看数据

   ```shell
   get -s /test-node
   ```

6. 修改数据带上版本号，版本不对会修改失败

   ```shell
   set -v 0 /test-node change test
   ```

7. 查看子节点信息

   ```shell
   ls / # 查看/下面的所有节点
   ls -R / #递归查看 / 下面所有的节点
   ```

8. 事件监听

   ```shell
    get -w /path # 注册监听的同时获取数据
    stat -w /path # 对节点进行监听，且获取元数据信息
    ls -w /path #监听目录
    ls ‐R ‐w /path ： ‐R 区分大小写，一定用大写
   ```

   zookeeper 时间类型：

   * None: 连接建立事件
   * NodeCreated： 节点创建 
   * NodeDeleted： 节点删除 
   * NodeDataChanged：节点数据变化 
   * NodeChildrenChanged：子节点列表变化 
   * DataWatchRemoved：节点监听被移除 
   * ChildWatchRemoved：子节点监听被移除

注意：

* zookeeper是以节点组织数据的，没有相对路径这么一说，所以，所 有的节点一定是以 / 开头
* create 后跟一个 -e 创建临时节点 ， 临时节点不能创建子节点 
* 容器节点主要用来容纳子节点，如果没有给其创建子节点，容器节点表现和持久化节点一样，如 果给容器节点创建了子节点，后续又把子节点清空，容器节点也会被zookeeper删除。
* 针对节点的监听：一旦事件触发，对应的注册立刻被移除，所以事件监听是一次性的 
* Zookeeper数据的组织形式为一个类似文件系统的数据结构，而这些数据都是存储在内存中的， 所以我们可以认为，Zookeeper是一个基于内存的小型数据库 