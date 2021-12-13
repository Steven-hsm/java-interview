##  Explain

* 模拟优化器执行SQL语句，分析你的查询语句或是结构的性能瓶颈
* 在 select 语句之前增加 explain 关键字，MySQL 会在查询上设置一个标记，执行查询会返回执行计划的信息，而不是 执行这条SQL
* 如果 from 中包含子查询，仍会执行该子查询，将结果放入临时表中 

**explain 两个变种** 

* **explain extended**:

  * 会在 explain 的基础上额外提供一些查询优化的信息。紧随其后通过 show warnings 命令可以得到优化后的查询语句，从而看出优化器优化了什么
  * 额外还有 filtered 列，是一个半分比的值，rows *filtered/100 可以**估算**出将要和 explain 中前一个表进行连接的行数

* **explain partitions**

  * 相比 explain 多了个 partitions 字段，如果查询是基于分区表的话，会显示查询将访问的分区。

  ![image-20211125100056963](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211125100056963.png)

### 3.1 **. id列** 

id列的编号是 select 的序列号，有几个 select 就有几个id，并且id的顺序是按 select 出现的顺序增长的。 

id列越大执行优先级越高，id相同则从上往下执行，id为NULL最后执行

### 3.2  select_type列

select_type 表示对应行是简单还是复杂的查询

* simple：简单查询。查询不包含子查询和union 
* primary：复杂查询中最外层的 select 
* subquery：包含在 select 中的子查询（不在 from 子句中）
* derived：包含在 from 子句中的子查询。MySQL会将结果存放在一个临时表中，也称为派生表
* union：在 union 中的第二个和随后的 select

### 3.3 **table列** 

这一列表示 explain 的一行正在访问哪个表。

当 from 子句中有子查询时，table列是 <derivenN> 格式，表示当前查询依赖 id=N 的查询，于是先执行 id=N 的查 询。

当有 union 时，UNION RESULT 的 table 列的值为<union1,2>，1和2表示参与 union 的 select 行id

### 3.4 type列

**关联类型或访问类型**，即MySQL决定如何查找表中的行

依次从最优到最差分别为：**system > const > eq_ref > ref > range > index > ALL** 一般来说，**得保证查询达到range级别，最好达到ref** 

**NULL**：mysql能够在优化阶段分解查询语句，在执行阶段用不着再访问表或索引。例如：在索引列中选取最小值，可 以单独查找索引来完成，不需要在执行时访问表

**const, system**：mysql能对查询的某部分进行优化并将其转化成一个常量（可以看show warnings 的结果）。用于 primary key 或 unique key 的所有列与常数比较时，所以表最多有一个匹配行，读取1次，速度比较快.**system是** **const的特例**，表里只有一条元组匹配时为system 

**eq_ref**：primary key 或 unique key 索引的所有部分被连接使用 ，最多只会返回一条符合条件的记录。这可能是在 const 之外最好的联接类型了，简单的 select 查询不会出现这种 type

**ref**：相比 eq_ref，不使用唯一索引，而是使用普通索引或者唯一性索引的部分前缀，索引要和某个值相比较，可能会 找到多个符合条件的行。 

**range**：范围扫描通常出现在 in(), between ,> ,<, >= 等操作中。使用一个索引来检索给定范围的行。

**index**：扫描全索引就能拿到结果，一般是扫描某个二级索引，这种扫描不会从索引树根节点开始快速查找，而是直接 对二级索引的叶子节点遍历和扫描，速度还是比较慢的，这种查询一般为使用覆盖索引，二级索引一般比较小，所以这 种通常比ALL快一些

**ALL**：即全表扫描，扫描你的聚簇索引的所有叶子节点。通常情况下这需要增加索引来进行优化了

### 3.5  **possible_keys列** 

查询可能使用哪些索引来查找

出现 possible_keys 有列，而 key 显示 NULL 的情况，这种情况是因为表中数据不多，mysql认为索引 对此查询帮助不大，选择了全表查询

如果该列是NULL，则没有相关的索引。在这种情况下，可以通过检查 where 子句看是否可以创造一个适当的索引来提 高查询性能，然后用 explain 查看效果

### 3.6 **key列**

这一列显示mysql实际采用哪个索引来优化对该表的访问。

如果没有使用索引，则该列是 NULL。如果想强制mysql使用或忽视possible_keys列中的索引，在查询中使用 force index、ignore index。

### 3.7 **key_len列** 

显示mysql在索引里使用的字节数,通过这个值可以算出具体使用了索引中的哪些列

key_len计算规则如下：

* 字符串，char(n)和varchar(n),n为字符数
  * char(n)：一个数字或字母占1个字节，一个汉字占3个字节，存汉子就是3n字节
  * 如果存汉字则长度是 3n + 2 字节，加的2字节用来存储字符串长度，因为 varchar是变长字符串  
* 数值类型：
  * tinyint：1字节
  * smallint：2字节 
  * int：4字节 
  * bigint：8字节
* 时间类型
  * date：3字节
  * timestamp：4字节 
  * datetime：8字节 
* 如果字段允许为 NULL，需要1字节记录是否为 NULL 
* 索引最大长度是768字节，当字符串过长时，mysql会做一个类似左前缀索引的处理，将前半部分的字符提取出来做索 引。

### 3.8  **ref列** 

这一列显示了在key列记录的索引中，表查找值所用到的列或常量，常见的有：const（常量），字段名

### 3. 9**rows列** 

是mysql估计要读取并检测的行数，注意这个不是结果集里的行数

### 3.10 **Extra列** 

这一列展示的是额外信息

**Using index**：使用覆盖索引 

**Using where**：使用 where 语句来处理结果，并且查询的列未被索引覆盖

**Using index condition**：查询的列不完全被索引覆盖，where条件中是一个前导列的范围；

**Using temporary**：mysql需要创建一张临时表来处理查询.出现这种情况一般是要进行优化的，首先是想到用索 引来优化

**Using filesort**：将用外部排序而不是索引排序，数据较小时从内存排序，否则需要在磁盘完成排序。

