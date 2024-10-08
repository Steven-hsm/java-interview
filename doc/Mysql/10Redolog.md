### 1. redo日志是什么

InnoDB存储引擎是***\*以页为单位来管理存储空间的\****，我们进行的增删改查操作其实本质上都是在访问页面（包括读页面、写页面、创建新页面等操作）。在真正访问页面之前，需要把在磁盘上的页缓存到内存中的Buffer Pool之后才可以访问，对于事务的要求，我们必须保证**持久性**。

假如我们只在内存的Buffer Pool中修改了页面，在事务提交后突然发生了某个故障，导致内存中的数据都失效了，那么这个已经提交的事务对数据库所做的更改也会丢失。那么可以有有以下方案：

* 在事务提交完成之前把该事务所修改的所有页面都刷新到磁盘

> 刷新一个完整的数据页太浪费了。可能造成修改一个字节就需要刷新16kb的缓存页到磁盘
>
> 随机IO刷起来比较慢。一个事务包含多条语句，而且一般修改的页面并不相邻，需要进行很多的随机I/O.

我们只是想让已经提交了的事务对数据库中数据所做的修改永久生效，即使后来系统崩溃，在重启后也能把这种修改恢复出来.所以我们其实没有必要在每次事务提交时就把该事务在内存中修改过的全部页面刷新到磁盘，只需要把修改了哪些东西记录一下就好.系统崩溃后，在重启时只要按照上述内容所记录的步骤重新更新一下数据页，那么该事务对数据库中所做的修改又可以被恢复出来，也就意味着满足持久性的要求。这个记录就叫做redo日志

redo日志刷新到磁盘的好处如下:

* redo日志占用的空间非常小:
* redo日志是顺序写入磁盘的

### 2. redo日志格式

redo日志本质上只是记录了一下事务对数据库做了哪些修改，格式如下。

![image-20211130145105105](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211130145105105.png)

* type：该条redo日志的类型。

* space ID：表空间ID。

* page number：页号。

* data：该条redo日志的具体内容。

  

