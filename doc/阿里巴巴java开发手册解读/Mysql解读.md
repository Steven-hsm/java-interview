1. 聚集索引
![image-20220112163022818](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220112163022818.png)
2. 普通索引
![image-20220112163031553](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220112163031553.png)

Mysql规约解读之前，先上图，这里是myql索引存储方式。

### 1. 建表规约

1. <font color=red>强制 </font>表达是与否概念的字段，必须使用 is_xxx 的方式命名，数据类型是 unsigned tinyint（1 表示是，0 表示否）<font color=red>目的是见名知义is_delete</font> 

   > POJO 类中的任何布尔类型的变量，都不要加 is 前缀。有些框架对is前缀的字段会做特殊处理

2. <font color=red>强制</font> 表名、字段名必须使用小写字母或数字，禁止出现数字开头，禁止两个下划线中间只出现数）<font color=red>数据库字段名的修改代价很大，因为无法进行预发布，所以字段名称需要慎重考虑</font> 

   > MySQL 在 Windows 下不区分大小写，但在 Linux 下默认是区分大小写

3. <font color=red>强制</font> 表名不使用复数名词 <font color=red>表名应该仅仅表示表里面的实体内容</font> 

4. <font color=red>强制</font> 禁止使用保留字

5.  <font color=red>强制</font> 主键索引名为 pk_字段名；唯一索引名为 uk_字段名；普通索引名则为 idx_字段名 <font color=red>见名知义</font> 

6. <font color=red>强制 </font> 小数类型为 decimal，禁止使用 float 和 double <font color=red>否则容易丢失精度</font> 

   > 存储的数据范围超过 decimal 的范围，建议将数据拆成整数和小数并分开存储

7. <font color=red>强制 </font> 如果存储的字符串长度几乎相等，使用 char 定长字符串类型 <font color=red>节省空间</font> 

8. <font color=red>强制 </font> varchar 是可变长字符串，不预先分配存储空间，长度不要超过 5000，如果存储长度大于此值，定义字段类型为 text，独立出来一张表，用主键来对应，避免影响其它字段索引效率

   > 聚集索引（一般也是主键索引），叶子节点存储的是完整数据，数据过大会造成每次加载页(16K)到内存的数量减少，查询时会造成产生的更多的磁盘I/O

9. <font color=red>强制 </font> 表必备三字段：id, gmt_create, gmt_modified

   > id 是唯一标识，自己不建，mysql也会帮忙建，自己建好可以节约性能
   >
   > gmt_create, gmt_modified 是为了业务好排查错误

10. <font color=yellow>推荐 </font> 表的命名最好是遵循“业务名称_表的作用”

11. <font color=yellow>推荐 </font> 库名与应用名称尽量一致

12. <font color=yellow>推荐 </font> 如果修改字段含义或对字段表示的状态追加时，需要及时更新字段注释。

13. <font color=yellow>推荐 </font>字段允许适当冗余，以提高查询性能，但必须考虑数据一致。冗余字段应遵循：

    1. 不是频繁修改的字段
    2. 不是唯一索引的字段
    3. 不是 varchar 超长字段，更不能是 text 字段

14. <font color=yellow>推荐</font> 三年内单表行数超过 500 万行或者单表容量超过 2GB，才推荐进行分库分表。

15. <font color=green>参考</font> 合适的字符存储长度

    > 根据业务 定义字段类型

### 2. 索引规约

1. <font color=red>强制</font> 业务上具有唯一特性的字段，即使是组合字段，也必须建成唯一索引。

2. <font color=red>强制</font> 超过三个表禁止 join。关联字段必须要有索引

3. <font color=red>强制</font> 在 varchar 字段上建立索引时，必须指定索引长度，没必要对全字段建立索引，根据实际文本区分度决定索引长度。

   > 1. 索引的长度变短可以是索引页容纳的索引值更多，查询速度更快。
   > 2. 文本有一定长度之后，区分度已经很高了，没必要对全字段建立索引

4. <font color=red>强制</font> 页面搜索严禁左模糊或者全模糊，如果需要请走搜索引擎来解决

5. <font color=yellow>推荐 </font> 如果有 order by 的场景，请注意利用索引的有序性

   > 索引有序是按照索引顺序排序的，组合索引按照建组合索引的顺序order by ，否则会走file sort

6. <font color=yellow>推荐 </font> 利用覆盖索引来进行查询操作，避免回表。

   > 回表是因为，非聚集索引没有完整的记录，只有索引字段以及主 聚集索引值，如果返回结果包含其他值，就需要重新在聚集索引中查询完整记录，也就是回表

7. <font color=yellow>推荐 </font> 利用延迟关联或者子查询优化超多分页场景

   > MySQL 并不是跳过 offset 行，而是取 offset+N 行，然后返回放弃前 offset 行，返回 N 行。 所有数据都加载，必然很慢
   >
   > 优化方案：先快速返回id，然后根据id查询结果：SELECT a.* FROM 表 1 a, (select id from 表 1 where 条件 LIMIT 100000,20 ) b where a.id=b.id

8. <font color=yellow>推荐 </font>SQL 性能优化的目标：至少要达到 range 级别，要求是 ref 级别，如果可以是 consts最好

   > sql写完之后使用explain 查看自己sql的执行效率

9. <font color=yellow>推荐 </font>建组合索引的时候，区分度最高的在最左边。

10. <font color=yellow>推荐 </font>防止因字段类型不同造成的隐式转换，导致索引失效

11. <font color=green>参考</font> 创建索引时避免有如下极端误解：

    1. 索引宁滥勿缺。认为一个查询就需要建一个索引
    2. 吝啬索引的创建。认为索引会消耗空间、严重拖慢记录的更新以及行的新增速度。
    3. 抵制惟一索引。认为惟一索引一律需要在应用层通过“先查后插”方式解决。

### 3. Sql 语句

1. <font color=red>强制</font> 不要使用 count(列名)或 count(常量)来替代 count(*)

   > count(*)是 SQL92 定义的标准统计行数的语法，跟数据库无关，跟 NULL 和非 NULL 无关。
   >
   > count(*)会统计值为 NULL 的行，而 count(列名)不会统计此列为 NULL 值的行。

2. <font color=red>强制</font> count(distinct col) 计算该列除 NULL 之外的不重复行数

   > count(distinct col1, col2) 如果其中一列全为 NULL，那么即使另一列有不同的值，也返回为 0。

3. <font color=red>强制</font> 当某一列的值全是 NULL 时，count(col)的返回结果为 0，但 sum(col)的返回结果为NULL，因此使用 sum()时需注意 NPE 问题。

   >  IFNULL(SUM(column), 0) 

4. <font color=red>强制</font> 使用 ISNULL()来判断是否为 NULL 值

5. <font color=red>强制</font> 代码中写分页查询逻辑时，若 count 为 0 应直接返回，避免执行后面的分页语句

6. <font color=red>强制</font> 不得使用外键与级联，一切外键概念必须在应用层解决。

   > 外键与级联更新适用于单机低并发，不适合分布式、高并发集群
   >
   > 级联更新是强阻塞，存在数据库更新风暴的风险；外键影响数据库的插入速度。

7. <font color=red>强制</font> 禁止使用存储过程，存储过程难以调试和扩展，更没有移植性。

8. <font color=red>强制</font> 数据订正（特别是删除或修改记录操作）时，要先 select，避免出现误删除，确认无误才能执行更新语句

9. <font color=red>强制</font> 对于数据库中表记录的查询和变更，只要涉及多个表，都需要在列名前加表的别名（或表名）进行限定

   > 不限定，存在两个表都存在的字段返回就会报错

10. <font color=yellow>推荐 </font>SQL 语句中表的别名前加 as，并且以 t1、t2、t3、...的顺序依次命名

11. <font color=yellow>推荐 </font>in 操作能避免则避免，若实在避免不了，需要仔细评估 in 后边的集合元素数量，控制在 1000 个之内

    > in里面的数据过大时，mysql引擎会根据估算，全表扫描可能比走索引更快，会不走索引
    >
    > 使用EXISTS  或者NOT EXISTS代替
    >
    > 用JOIN 代替	

12. <font color=green>参考</font> 因国际化需要，所有的字符存储与表示，均采用 utf8 字符集，那么字符计数方法需 要注意。

13. <font color=green>参考</font> TRUNCATE TABLE 比 DELETE 速度快，且使用的系统和事务日志资源少，但 TRUNCATE无事务且不触发 trigger，有可能造成事故，故不建议在开发代码中使用此语句。

### 4. ORM 映射

1. <font color=red>强制</font> 在表查询中，一律不要使用 * 作为查询的字段列表，需要哪些字段必须明确写明。<font color=red>速度、效率</font>

2. <font color=red>强制</font> POJO 类的布尔属性不能加 is，而数据库字段必须加 is_，要求在 resultMap 中进行字段与属性之间的映射

3. <font color=red>强制</font> 不要用 resultClass 当返回参数，即使所有类属性名与数据库字段一一对应，也需要定义<resultMap>；反过来，每一个表也必然有一个<resultMap>与之对应

4. <font color=red>强制</font> sql.xml 配置参数使用：#{}，#param# 不要使用${} 此种方式容易出现 SQL 注入。

5. <font color=red>强制</font> iBATIS 自带的 queryForList(String statementName,int start,int size)不推荐使用。

   > ​	实现方式是在数据库取到 statementName 对应的 SQL 语句的所有记录，再通过 subList 取start,size 的子集合

6. <font color=red>强制</font> 不允许直接拿 HashMap 与 Hashtable 作为查询结果集的输出。

7. <font color=red>强制</font> 更新数据表记录时，必须同时更新记录对应的 gmt_modified 字段值为当前时间

8. <font color=yellow>推荐 </font>不要写一个大而全的数据更新接口

9. <font color=green>参考 </font>@Transactional 事务不要滥用。事务会影响数据库的 QPS，另外使用事务的地方需要考虑各方面的回滚方案，包括缓存回滚、搜索引擎回滚、消息补偿、统计修正等

10. <font color=green>参考 </font><isEqual>中的 compareValue 是与属性值对比的常量，一般是数字，表示相等时带上此条件；<isNotEmpty>表示不为空且不为 null 时执行；<isNotNull>表示不为 null 值时执行