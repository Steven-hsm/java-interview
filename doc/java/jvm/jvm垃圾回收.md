## 1. 简介

垃圾收集器虽然从Serial -> Parallel->ParNew->CMS->G1->ZGC一步步演化，但是新的垃圾收集器不一定就适合所有场景，从垃圾收集的发展进程的可以看出，垃圾收集器的演化无非因为一下几点：

* 贴合当下的硬件，能够更友好的使用硬件资源
* 减少STW（Stop The World）的时间,提高用户体验

## 2. 基本概念

### 2.1 jvm内存模型

![image-20211028114100527](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211028114100527.png)

