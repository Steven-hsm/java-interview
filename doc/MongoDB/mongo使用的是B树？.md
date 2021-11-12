最近在看Mysql的Innodb存储引擎的存储结构，采用B+树，然后一个同事和我说现在磁盘都变成固态硬盘了，B+树的作用不大了，而且MongoDb使用的是B树，然后我开始想MongoDb为什么使用B树。

于是开启了百度之旅，结果发现**MongoDb底层使用的也是B+树**。而且很多博客说的都是错的

MongoDb底层使用WiredTiger存储引擎，官网地址 http://source.wiredtiger.com/3.2.1/tune_page_size_and_comp.html
里面有一句话写着:

```shell
WiredTiger maintains a table's data in memory using a data structure called a B-Tree ( B+ Tree to be specific), referring to the nodes of a B-Tree as pages. Internal pages carry only keys. The leaf pages store both keys and values.

WiredTiger使用名为B-Tree(具体来说是B+ Tree)的数据结构在内存中维护表的数据，该结构将B-Tree的节点作为页面引用。内部页面只携带关键字。叶页同时存储键和值。
```

接下来来分下一下底层数据结构

**B+Tree(B-Tree变种)**

* 非叶子节点不存储data，只存储索引(冗余)，可以放更多的索引
* 叶子节点包含所有索引字段
* 叶子节点用指针连接，提高区间访问的性能

### ## 聚簇索引

![image-20211112185155720](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211112185155720.png)

Mysql一次磁盘I/0就读取一个page,默认的pageSize为16k,非叶子节点只存储索引，按照一页可以存储1000个索引，第二页可以存储1000个索引，第三页为叶子节点，保存了数据信息，按照存储10条数据结算，三次磁盘I/O就可以从 1000 * 1000 * 10 找到精确的那条数据

## 非聚簇索引

![image-20211112185727925](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211112185727925.png)

与聚簇索引的区别就是，叶子节点并没有存储完整信息，而是索引列+聚簇索引值，所以查询很容易造成回表（通过聚簇索引找到完整的数据）

## B 树无法解决的问题

**B树的特点：**

* 叶节点具有相同的深度，叶节点的指针为空
* 所有索引元素不重复
* 节点中的数据索引从左到右递增排列

1. B树过深，过深造成的问题就是磁盘I/O时间会很长