<center><h1>树</h1></center>

* 结点拥有的子树称为结点的度，度为0的结点称为叶子结点
* 树的度是树内所有节点的度的最大值
* 结点的子树的根称为该结点的孩子，该结点称为孩子的双亲
* 结点的层次从根开始算起，根为第一层，根的还子为第二层
* 树中节点的最大层次称为树的深度或高度

## 1. 树的存储结构

### 1.1 双亲表示法

```java
@Data
class PTNode {
    private Object data;//节点数据
    private int parent;//双亲位置
}
@Data
public class PTNodeTree{
    private PTNode nodes[];//结点数组
    private int root;//根节点的位置
    private int num;//结点数
}
```

### 1.2 孩子表示法

```java
@Data
public class CTNodeTree {
    CTBox nodes[];//节点数
    private int root;//根节点的位置
    private int num;//结点数
}
@Data
class CTNode{
    int child;
    CTNode next;
}
@Data
class CTBox{
    Object data;
    CTNode firstChild;
}
```

### 1.3 孩子兄弟表示法

```java
@Data
public class CSNodeTree {
    Object data; //数据元素
    CSNodeTree firstChild;//第一个孩子
    CSNodeTree rightsib;//右兄弟
}
```

## 2. 二叉树

* 每个结点最多有两棵子树
* 左子树和右子树是有顺序的，次序不能任意颠倒
* 树种某个节点只有一棵树也要区分是左子树还是右子树

二叉树的性质

1. 在二叉树的第 i 层至多有 2^(i －1)个结点。(i>=1)
2. 深度为 k 的二叉树至多有 2^(k-1)个结点(k >=1)
3. 对任何一棵二叉树T, 如果其叶结点数为n0, 度为2的结点数为 n2,则n0＝n2＋1。
4. 具有 n (n>=0) 个结点的完全二叉树的深度为![img](https://img-blog.csdn.net/20160529154127355)＋1

#### 二叉树的存储结构

顺序存储

二叉链表 lchild data rchild

#### 二叉树的遍历方式

前序遍历

```java
public void preOrder(int[] arr, int index) {
    if (arr == null && arr.length == 0) {
        System.out.println("数组为空");
        return;
    }
    System.out.print(arr[index] + "\t");
    if ((index * 2 + 1) < arr.length) {
        preOrder(arr, index * 2 + 1);
    }
    if ((index * 2 + 2) < arr.length) {
        preOrder(arr, index * 2 + 2);
    }
}
```

中序遍历

```java
public void infixOrder(int[] arr, int index) {
    if (arr == null && arr.length == 0) {
        System.out.println("数组为空");
        return;
    }
    //先遍历左子树 （index*2+1）
    if ((index * 2 + 1) < arr.length) {
        infixOrder(arr, index * 2 + 1);
    }
    if (index < arr.length) {
        System.out.print(arr[index] + "\t");
    }

    if ((index * 2 + 2) < arr.length) {
        infixOrder(arr, index * 2 + 2);
    }
}
```

后续遍历

```java
public void postOrder(int[] arr, int index) {
    if (arr == null && arr.length == 0) {
        System.out.println("数组为空");
        return;
    }
    if ((index * 2 + 1) < arr.length) {
        postOrder(arr, index * 2 + 1);
    }
    if ((index * 2 + 2) < arr.length) {
        postOrder(arr, index * 2 + 2);
    }
    if (index < arr.length) {
        System.out.print(arr[index] + "\t");
    }
}
```

层序遍历

```java
public void levelOrder(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        System.out.print(arr[i] + "\t");
    }
}
```

### 2.1 平衡二叉树

​    平衡二叉树是基于二分法的策略提高数据的查找速度的二叉树的数据结构；

1. 非叶子节点只能允许最多两个子节点存在
2. 每一个非叶子节点数据分布规则为左边的子节点小当前节点的值，右边的子节点大于当前节点的值
3. 树的左右两边的层级数相差不会大于1;
4. 没有值相等重复的节点;
5. 平衡二叉树查询性能和树的层级成反比，高度越小，查询越快

### 2.2 线索二叉树

1. 指向前驱和后继的指针称为线索，加上线索的二叉链表称为线索链表，相应的二叉树就称为线索二叉树
2. 二叉树以某种次序遍历使其变为线索二叉树的过程称为线索化

### B树（B-树）
​    B树属于多叉树又名平衡多路查找树，数据库索引技术里大量使用者B树和B+树的数据结构
​    1. 所有节点关键字是按递增次序排列，并遵循左小右大原则
​        2. 非叶节点的子节点数>1，且<=M ，且M>=2，空树除外（注：M阶代表一个树节点最多有多少个查找路径，M=M路,当M=2则是2叉树,M=3则是3叉）
​        3. 关键字数：枝节点的关键字数量大于等于ceil(m/2)-1个且小于等于M-1个（注：ceil()是个朝正无穷方向取整的函数 如ceil(1.1)结果为2);
​        4. 所有叶子节点均在同一层、叶子节点除了包含了关键字和关键字记录的指针外也有指向其子节点的指针只不过其指针地址都为null对应下图最后一层节点的空格子;

### B+树
    1. B+跟B树不同B+树的非叶子节点不保存关键字记录的指针，只进行数据索引，这样使得B+树每个非叶子节点所能保存的关键字大大增加；
        2. B+树叶子节点保存了父节点的所有关键字记录的指针，所有数据地址必须要到叶子节点才能获取到。所以每次数据查询的次数都一样；
        3. B+树叶子节点的关键字从小到大有序排列，左边结尾数据都会保存右边节点开始数据的指针。
        4. 非叶子节点的子节点数=关键字数（来源百度百科）（根据各种资料 这里有两种算法的实现方式，另一种为非叶节点的关键字数=子节点数-1（来源维基百科)，虽然他们数据排列结构不一样，但其原理还是一样的Mysql 的B+树是用第一种方式实现）;

### B*树

​    B*树是B+树的变种
​    1. 首先是关键字个数限制问题，B+树初始化的关键字初始化个数是cei(m/2)，b*树的初始化个数为（cei(2/3*m)）
​        2. B+树节点满时就会分裂，而B*树节点满时会检查兄弟节点是否满（因为每个节点都有指向兄弟的指针），如果兄弟节点未满则向兄弟节点转移关键字，如果兄弟节点已满，则从当前节点和兄弟节点各拿出1/3的数据创建一个新的节点出来；

