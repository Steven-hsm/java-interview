## 1. bitmaps

### 1.1 简介

现代计算机用二进制（位） 作为信息的基础单位， 1个字节等于8位， 例如“abc”字符串是由3个字节组成， 但实际在计算机存储时将其用二进制表示， “abc”分别对应的ASCII码分别是97、 98、 99， 对应的二进制分别是01100001、 01100010和01100011，如下图：

![image-20210612163028104](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210612163028104.png)



合理地使用操作位能够有效地提高内存使用率和开发效率。

​	Redis提供了Bitmaps这个“数据类型”可以实现对位的操作：

（1） Bitmaps本身不是一种数据类型， 实际上它就是字符串（key-value） ， 但是它可以对字符串的位进行操作。

（2） Bitmaps单独提供了一套命令， 所以在Redis中使用Bitmaps和使用字符串的方法不太相同。 可以把Bitmaps想象成一个以位为单位的数组， 数组的每个单元只能存储0和1， 数组的下标在Bitmaps中叫做偏移量。

![image-20210612163118902](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210612163118902.png)

### 1.2 常用命令

* setbit 
  * setbit<key><offset><value>设置Bitmaps中某个偏移量的值（0或1）
  * offset:偏移量从0开始
* getbit
  * getbit<key><offset>获取Bitmaps中某个偏移量的值
  * 获取键的第offset位的值（从0开始算）
* bitcount
  * 统计**字符串**被设置为1的bit数
  * bitcount<key>[start end]
* bitop
  * bitop and(or/not/xor) <destkey> [key…]
  * bitop是一个复合操作， 它可以做多个Bitmaps的and（交集） 、 or（并集） 、 not（非） 、 xor（异或） 操作并将结果保存在destkey中。

### 1.3 bitmaps与set对比

bitmaps 最大的优势在于节省内存

## 2. Hyperloglog

## 2.1 简介

在工作当中，我们经常会遇到与统计相关的功能需求，比如统计网站PV（PageView页面访问量）,可以使用Redis的incr、incrby轻松实现。

但像UV（UniqueVisitor，独立访客）、独立IP数、搜索记录数等需要去重和计数的问题如何解决？这种求集合中不重复元素个数的问题称为基数问题。

解决基数问题有很多种方案：

（1）数据存储在MySQL表中，使用distinct count计算不重复个数

（2）使用Redis提供的hash、set、bitmaps等数据结构来处理

以上的方案结果精确，但随着数据不断增加，导致占用空间越来越大，对于非常大的数据集是不切实际的。

能否能够降低一定的精度来平衡存储空间？Redis推出了HyperLogLog

Redis HyperLogLog 是用来做基数统计的算法，HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定的、并且是很小的。

在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基数。这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。

但是，因为 HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，所以 HyperLogLog 不能像集合那样，返回输入的各个元素。

什么是基数?

比如数据集 {1, 3, 5, 7, 5, 7, 8}， 那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。 基数估计就是在误差可接受的范围内，快速计算基数。

### 2.2 常用命令

* pfadd
  * pfadd <key>< element> [element ...]  添加指定元素到 HyperLogLog 中
  * 将所有元素添加到指定HyperLogLog数据结构中。如果执行命令后HLL估计的近似基数发生变化，则返回1，否则返回0
* pfcount
  * pfcount<key> [key ...] 计算HLL的近似基数，可以计算多个HLL，比如用HLL存储每天的UV，计算一周的UV可以使用7天的UV合并计算即可
* pfmerge
  * pfmerge<destkey><sourcekey> [sourcekey ...]将一个或多个HLL合并后的结果存储在另一个HLL中，比如每月活跃用户可以使用每天的活跃用户来合并计算可得

## 3. Geospatial

### 3.1 简介

Redis 3.2 中增加了对GEO类型的支持。GEO，Geographic，地理信息的缩写。该类型，就是元素的2维坐标，在地图上就是经纬度。redis基于该类型，提供了经纬度设置，查询，范围查询，距离查询，经纬度Hash等常见操作。

### 3.2 常用命令

* geoadd 

  * geoadd<key>< longitude><latitude><member> [longitude latitude member...]  添加地理位置（经度，纬度，名称）

  * 两极无法直接添加，一般会下载城市数据，直接通过 Java 程序一次性导入。

    有效的经度从 -180 度到 180 度。有效的纬度从 -85.05112878 度到 85.05112878 度。

    当坐标位置超出指定范围时，该命令将会返回一个错误。

    已经添加的数据，是无法再次往里面添加的。

* geopos  

  * geopos  <key><member> [member...]  获得指定地区的坐标值

* geodist

  * geodist<key><member1><member2>  [m|km|ft|mi ]  获取两个位置之间的直线距离

* georadius

  * georadius<key>< longitude><latitude>radius m|km|ft|mi  以给定的经纬度为中心，找出某一半径内的元素



