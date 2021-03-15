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

### 2.3 B树（B-树）

B树（英语：B-tree）是一种自平衡的树，能够保持数据有序.这种数据结构能够让查找数据、顺序访问、插入数据及删除的动作，都在对数时间内完成。

B树属于多叉树又名平衡多路查找树，数据库索引技术里大量使用者B树和B+树的数据结构.(内容存盘，会因为树的深度过大而造成磁盘IO读写过于频繁)
1. 所有节点关键字是按递增次序排列，并遵循左小右大原则
2. 树中每个结点最多含有m个孩子（m>=2）；
3. 除根结点和叶子结点外，其它每个结点至少有[ceil(m / 2)]个孩子（其中ceil(x)是一个取上限的函数）；
4. 若根结点不是叶子结点，则至少有2个孩子
5. 所有叶子结点都出现在同一层(最底层)，**叶子结点为外部结点，保存内容，即key和value**。
6. **其他结点为内部结点，保存索引，即key和next**。

### B+树

1. B+跟B树不同B+树的非叶子节点不保存关键字记录的指针，只进行数据索引，这样使得B+树每个非叶子节点所能保存的关键字大大增加；
2. B+树叶子节点保存了父节点的所有关键字记录的指针，所有数据地址必须要到叶子节点才能获取到。所以每次数据查询的次数都一样；
3. B+树叶子节点的关键字从小到大有序排列，左边结尾数据都会保存右边节点开始数据的指针。
4. 非叶子节点的子节点数=关键字数（来源百度百科）（根据各种资料 这里有两种算法的实现方式，另一种为非叶节点的关键字数=子节点数-1（来源维基百科)，虽然他们数据排列结构不一样，但其原理还是一样的Mysql 的B+树是用第一种方式实现）;

### B*树

B*树是B+树的变种
1. 首先是关键字个数限制问题，B+树初始化的关键字初始化个数是cei(m/2)，b*树的初始化个数为（cei(2/3*m)）
2. B+树节点满时就会分裂，而B*树节点满时会检查兄弟节点是否满（因为每个节点都有指向兄弟的指针），如果兄弟节点未满则向兄弟节点转移关键字，如果兄弟节点已满，则从当前节点和兄弟节点各拿出1/3的数据创建一个新的节点出来；

### 2.4 哈夫曼树
带权路径长度：设二叉树具有n个带权值得叶结点，那么从根结点到各个叶结点的路径长度与相应结点权值的乘积的和，叫做二叉树的带权路径长度

具有最小带权路径长度的二叉树称为**哈夫曼树**

构造哈夫曼树的原则：

* 权值越大的结点越靠近根结点
* 权值越小的结点越远离根节点

哈夫曼树构造的过程：
1. 给定的n个权值{W1,W2,...,Wn}构造n棵只有一个叶结点的二叉树，从而得到一个二叉树的集合F={T1,T2,...,Tn}.
2. 在F中选取根据点的权值最小和次小的两棵树作为左右子树构成一棵新的二叉树，这棵新树的二叉树根节点的权值为其左右子树根节点权值之和。
3. 在集合F中删除作为左、右子树的两棵二叉树，并将新建立的二叉树加入到集合F中。
4. 重复2,3步，当F中只剩下一棵二叉树时，这棵树就是要建立的哈夫曼树

哈夫曼树结点个数：n=2N0-1

哈夫曼树主要应用在哈夫曼编码中。左分支为0，右分支为1



```java
/**
 * 哈夫曼树
 */
public class HuffmanTree {
    public Node generateTree(Map<String, Integer> frequencyForData) {
        //1.优先队列，会根据泛型对象的compareTo方法进行排序
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        //2.将叶子结点放入到优先级队列中
        for (String str : frequencyForData.keySet()) {
            priorityQueue.add(new Node(str, frequencyForData.get(str)));
        }

        //将数据放入树种，最终的那棵树就是哈夫曼树
        while (priorityQueue.size() != 1) {
            Node minNode1 = priorityQueue.poll();
            Node minNode2 = priorityQueue.poll();
            priorityQueue.add(new Node(minNode1, minNode2, minNode1.freq + minNode2.freq));
        }
        //队列中最后一个就是生成的哈夫曼树
        return priorityQueue.poll();
    }

    public Map<String, String> encodeReal(Node root) {
        Map<String, String> encodingResult = new HashMap<>();
        encodeChar(root, "", encodingResult);
        return encodingResult;
    }

    private void encodeChar(Node node, String encoding, Map<String, String> encodingResult) {
        if (node.isLeaf) {
            encodingResult.put(node.data, encoding);
            return;
        }
        encodeChar(node.left, encoding + "0", encodingResult);
        encodeChar(node.right, encoding + "1", encodingResult);
    }

    public static void main(String[] args) {
        HuffmanTree huffmanTree = new HuffmanTree();

        Map<String, Integer> map = new HashMap<>();
        map.put("A", 27);
        map.put("B", 8);
        map.put("C", 15);
        map.put("D", 15);
        map.put("E", 30);
        map.put("F", 5);
        Node node = huffmanTree.generateTree(map);
        Map<String, String> encodeResult = huffmanTree.encodeReal(node);
        encodeResult.forEach((key,value)->{
            System.out.println(key + ":\t" + value);
        });
    }
}

/**
 * 哈夫曼树的结点
 */
class Node implements Comparable<Node> {
    String data;         //实际数据
    int freq;           //频数
    boolean isLeaf;     //是否叶子结点
    Node left, right;    //左右结点

    public Node(String data, int freq) {
        this.data = data;
        this.freq = freq;
        this.isLeaf = true;
    }

    public Node(Node left, Node right, int freq) {
        this.freq = freq;
        this.left = left;
        this.right = right;
        this.isLeaf = false;
    }

    @Override
    public int compareTo(Node o) {
        return this.freq - o.freq;
    }
}
```

### 2.5 红黑树

1. 每个节点要么是黑色，要么是红色
2. 根节点是黑色
3. 每个叶子节点（NIL）是黑色
4. 每个红色结点的两个子结点一定都是黑色。
5. **任意一结点到每个叶子结点的路径都包含数量相同的黑结点**

红黑树的自平衡：左旋、右旋和变色

* **左旋**:以某个结点作为支点(旋转结点)，其右子结点变为旋转结点的父结点，右子结点的左子结点变为旋转结点的右子结点，左子结点保持不变
* **右旋**：以某个结点作为支点(旋转结点)，其左子结点变为旋转结点的父结点，左子结点的右子结点变为旋转结点的左子结点，右子结点保持不变
* **变色**：结点的颜色由红变黑或由黑变红

