<center><h1>线性表</h1></center>

## 介绍
线性表是零个或多个数据元素的有限序列
线性表的基本数据抽象数据类型，可查看Java的list方法。详情可看List接口源码，List是对线性表的一种抽象，Java其他线性表都依赖于List.

```java
List
    add
    addAll
    clear
    contains
    containsAll
    get
    indexOf
    isEmpty
    iterator
    lastIndexOf
    listIterator
    remove
    removeAll
    replaceAll
    retainAll
    set
    size
    sort
    spliterator
    subList
    toArray
```

## 线性表的顺序存储结构
线性表的顺序存储结构，指的是用一段地址连续的存储单元一次存储线性表的数据元素
线性表的顺序存储使用的主要数据结构：数组

查看Java中线性表的实现类型ArrayList
```java
public class ArrayList<E> extends AbstractList<E>
	implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
	transient Object[] elementData;
	private int size;
}
```
使用数组存储线性表，可想而知具有以下优缺点
优点

* 无需为表中元素之间的逻辑关系而增加额外的存储空间 
* 可以快速的存取表中的任一位置的元素

缺点：

* 插入和删除操作需要移动大量的元素
* 当线性表长度变化比较大时，难以确定存储空间的容量
* 容易造成存储空间的碎片

## 线性表的链式存储结构

线性表的链式存储机构，将n个节点链接成一个链表
链式存储结构需要储存元素之间的逻辑关系，所以需要增加一个指针域（确定下一个，或者上一个的位置）

查看Java中链式存储结构LinkedList实现
```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
{
	transient int size = 0;
	transient Node<E> first;
	transient Node<E> last;
	private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
```

LinkedList中，定义了一个Node节点的内部类来表是一个节点，一个节点上有数据元素，前一个元素指针，后一个元素指针。Node显然是一个双向链表，LinkedList用两个属性来分别存储头指针和尾指针。

优点

* 不需要提前预分配空间
* 链表插入和删除元素不需要移位，速度较快

缺点

* 需要额外的空间来存储元素的位置逻辑关系
* 查找速度慢，需要遍历链表查询

## 静态链表

使用两个数据域data和cur，数组的每个下表对应一个data和一个cur。数据域data用来存放数据元素，游标cur用来存储该元素的后续元素在数组中的下表

优点：

* 插入和删除元素时，不需要移动元素位置

缺点：

* 表长难以确定
* 失去了顺序存储结构随机存储的特性