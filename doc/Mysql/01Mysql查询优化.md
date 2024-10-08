Mysql的优化，一般是对**索引**优化,这里主要以innodb为主

**索引**是帮助MySQL高效获取数据的**排好序**的数据结构,**排好序**是索引数据结构的特点，也是索引优化的前提。

## 1. 索引数据结构

索引底层使用B+树作为其底层存储结构。相较于二叉树、红黑树、hash表、B树而言，B+树有自己独特的优势。

*B+树特点*

* 非叶子节点不存储data，只存储索引(冗余)，可以放更多的索引
* 叶子节点包含所有索引字段
* 叶子节点用指针连接，提高区间访问的性能

### 1.1 聚集索引（主键索引）

聚集索引指索引包含了完整的数据记录,一般主键索引是聚集索引

![image-20211103171034299](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211103171034299.png)

mysql使用B+树存储索引，页大小默认为16K,mysql在查询时会一次性将一个页加载到内存中（一次磁盘I/O）,在内存中可以快速定位下一次需要加载的页（有一次磁盘I/O），所以一般精准查询只需要进行三次磁盘I/O即可找到对应的数据。

### 1.2联合索引

也称非聚集索引，非聚集索引的data存储的是聚集索引的值，如果返回的数据包含了非索引值，那么就需要通过聚集索引找到完整的记录，这里就会产生**回表**，一般建议使用**覆盖索引**，也就是查询返回值都在索引列中，例如下图，只返回name，age，position值的话，就不需要再去聚集索引查找其他数据，速度会快很多。

![image-20211103172035985](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211103172035985.png)

## 2. 优化建议

* 建表时尽量建主键（不建主键，mysql内部也会自己创建一个主键）
* 主键尽量使用整型的自增主键（自增主键会减少插入）
* 索引最佳实践
  * **全值匹配**
  * **最左前缀法则** 
  * **不在索引列上做任何操作（计算、函数、（自动or手动）类型转换），会导致索引失效而转向全表扫描**
  * **存储引擎不能使用索引中范围条件右边的列** 
  * **尽量使用覆盖索引（只访问索引的查询（索引列包含查询列）），减少 select \* 语句**
  * **mysql在使用不等于（！=或者<>），not in，not exists的时候无法使用索引会导致全表扫描<小于、>大于、<=、>=这些，mysql内部优化器会根据检索比例、表大小等多个因素整体评估是否使用索引**
  * **is null,is not null 一般情况下也无法使用索引**
  * **like以通配符开头（%abc）mysql索引失效会变成全表扫描操作**
  * **字符串不加单引号索引失效**
  * **少用or或in，用它查询时，mysql不一定使用索引，mysql内部优化器会根据检索比例、表大小等多个因素整体评估是否使用索引**



