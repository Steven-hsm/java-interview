mongodb是一个文档数据库

文档存在于集合中

**主要功能**

1. 高性能
   * 嵌入式数据模型的支持减少了数据库系统的I/O活动
   * 索引支持更快的查询，并且可以包含来自嵌入式文档的和数组的键
2. 丰富的查询语言
3. 高可用性（副本集）
   * 自动故障转移
   * 数据冗余
4. 水平伸缩性（分片）
   * 在一组计算机集群分布数据
5. 支持多种存储引擎
   * [WiredTiger存储引擎](https://mongodb.net.cn/manual/core/wiredtiger/)
   * [内存中存储引擎](https://mongodb.net.cn/manual/core/inmemory/)
   * MongoDB提供可插拔的存储引擎API，允许第三方为MongoDB开发存储引擎

**视图**

MongoDB不会将视图内容持久化到磁盘上，不支持对视图的写入操作，只能进行读写操作。

**上限集合**

[上限集合](https://mongodb.net.cn/manual/reference/glossary/#term-capped-collection)是固定大小的集合，它们支持高吞吐量操作，这些操作根据插入顺序插入和检索文档。一旦集合填充了其分配的空间，它就会通过覆盖集合中最旧的文档为新文档腾出空间。