抄袭学习自   [博客地址](https://blog.csdn.net/weixin_41190227/article/details/86600821)

### 排序算法

#### 1. 概念

**什么叫排序**

对一序列对象根据某个关键字进行排序。

**术语**

* 稳定：如果a原本在b前面，而a=b，排序之后a仍然在b的前面；
* 不稳定：如果a原本在b的前面，而a=b，排序之后a可能会出现在b的后面；
* 内排序：所有排序操作都在内存中完成；
* 外排序：由于数据太大，因此把数据放在磁盘中，而排序通过磁盘和内存的数据传输才能进行；
* 时间复杂度：一个算法执行所耗费的时间
* 空间复杂度：运行完一个程序所需内存的大小

**算法总结**

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMzMwNDMxNjgtMTg2NzgxNzg2OS5wbmc?x-oss-process=image/format,png)

* n: 数据规模
* k: “桶”的个数
* In-place: 占用常数内存，不占用额外内存
* Out-place: 占用额外内存

**算法分类**

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMzMyMjA2MzctMTA1NTA4ODExOC5wbmc?x-oss-process=image/format,png)

**比较和非比较的区别**

* 常见的**快速排序、归并排序、堆排序、冒泡排序** 等属于**比较排序** 。在排序的最终结果里，元素之间的次序依赖于它们之间的比较。每个数都必须和其他数进行比较，才能确定自己的位置 。
* 在冒泡排序之类的排序中，问题规模为n，又因为需要比较n次，所以平均时间复杂度为O(n²)。在归并排序、快速排序之类的排序中，问题规模通过分治法消减为logN次，所以时间复杂度平均O(nlogn)。
* 比较排序的优势是，适用于各种规模的数据，也不在乎数据的分布，都能进行排序。可以说，比较排序适用于一切需要排序的情况。



* **计数排序、基数排序、桶排序**则属于**非比较排序** 。非比较排序是通过确定每个元素之前，应该有多少个元素来排序。针对数组arr，计算arr[i]之前有多少个元素，则唯一确定了arr[i]在排序后数组中的位置 。
*  非比较排序只要确定每个元素之前的已有的元素个数即可，所有一次遍历即可解决。算法时间复杂度O(n)。
*  非比较排序时间复杂度底，但由于非比较排序需要占用空间来确定唯一位置。所以对数据规模和数据分布有一定的要求



#### 2. 排序算法

##### 2.1 冒泡排序

重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMjMyMzg0NDktMjE0NjE2OTE5Ny5naWY)

```java
public static int[] bubbleSort(int[] array) {
    if (array.length == 0)
        return array;
    for (int i = 0; i < array.length; i++)
        for (int j = 0; j < array.length - 1 - i; j++)
            if (array[j + 1] < array[j]) {
                int temp = array[j + 1];
                array[j + 1] = array[j];
                array[j] = temp;
            }
    return array;
}
```

##### 2.2 选择排序

表现最稳定的排序算法之一 ，**因为无论什么数据进去都是O(n2)的时间复杂度** ，所以用到它的时候，数据规模越小越好。唯一的好处可能就是不占用额外的内存空间了吧。理论上讲，选择排序可能也是平时排序一般人想到的最多的排序方法了吧。

首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMjQ3MTk1OTAtMTQzMzIxOTgyNC5naWY)

```java
public static int[] selectionSort(int[] array) {
    if (array.length == 0)
        return array;
    for (int i = 0; i < array.length; i++) {
        int minIndex = i;
        for (int j = i; j < array.length; j++) {
            if (array[j] < array[minIndex]) //找到最小的数
                minIndex = j; //将最小数的索引保存
        }
        int temp = array[minIndex];
        array[minIndex] = array[i];
        array[i] = temp;
    }
    return array;
}
```

##### 2.3 插入排序

通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入

插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMjU2NDUyNzctMTE1MTEwMDAwMC5naWY)

```java
public static int[] insertionSort(int[] array) {
    if (array.length == 0)
        return array;
    int current;
    for (int i = 0; i < array.length - 1; i++) {
        current = array[i + 1];
        int preIndex = i;
        while (preIndex >= 0 && current < array[preIndex]) {
            array[preIndex + 1] = array[preIndex];
            preIndex--;
        }
        array[preIndex + 1] = current;
    }
    return array;
}
```

##### 2.4 希尔排序

希尔排序是希尔（Donald Shell） 于1959年提出的一种排序算法。希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序，同时该算法是冲破O(n2）的第一批算法之一。它与插入排序的不同之处在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序。

希尔排序是把记录按下表的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTE5MjY5OS8yMDE4MDMvMTE5MjY5OS0yMDE4MDMxOTA5NDExNjA0MC0xNjM4NzY2MjcxLnBuZw?x-oss-process=image/format,png)

##### 2.5 归并排序（Merge Sort）

和选择排序一样，归并排序的性能不受输入数据的影响，但表现比选择排序好的多，因为始终都是O(n log n）的时间复杂度。代价是需要额外的内存空间。

建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。归并排序是一种稳定的排序方法。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMzA1NTcwNDMtMzczNzUwMTAuZ2lm)

```java
public static int[] MergeSort(int[] array) {
    if (array.length < 2) return array;
    int mid = array.length / 2;
    int[] left = Arrays.copyOfRange(array, 0, mid);
    int[] right = Arrays.copyOfRange(array, mid, array.length);
    return merge(MergeSort(left), MergeSort(right));
}
public static int[] merge(int[] left, int[] right) {
    int[] result = new int[left.length + right.length];
    for (int index = 0, i = 0, j = 0; index < result.length; index++) {
        if (i >= left.length)
            result[index] = right[j++];
        else if (j >= right.length)
            result[index] = left[i++];
        else if (left[i] > right[j])
            result[index] = right[j++];
        else
            result[index] = left[i++];
    }
    return result;
}
```

##### 2.6 快速排序（Quick Sort）

过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMzA5MzYzNzEtMTQxMzUyMzQxMi5naWY)

```java
public static int[] QuickSort(int[] array, int start, int end) {
    if (array.length < 1 || start < 0 || end >= array.length || start > end) return null;
    int smallIndex = partition(array, start, end);
    if (smallIndex > start)
        QuickSort(array, start, smallIndex - 1);
    if (smallIndex < end)
        QuickSort(array, smallIndex + 1, end);
    return array;
}
public static int partition(int[] array, int start, int end) {
    int pivot = (int) (start + Math.random() * (end - start + 1));
    int smallIndex = start - 1;
    swap(array, pivot, end);
    for (int i = start; i <= end; i++)
        if (array[i] <= array[end]) {
            smallIndex++;
            if (i > smallIndex)
                swap(array, i, smallIndex);
        }
    return smallIndex;
}
public static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}
```

##### 2.7 堆排序（Heap Sort）

利用堆这种数据结构所设计的一种排序算法。堆积是一个近似完全二叉树的结构，并同时满足堆积的性质：即子结点的键值或索引总是小于（或者大于）它的父节点。

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMzEzMDg2OTktMzU2MTM0MjM3LmdpZg)

```java
static int len;
public static int[] HeapSort(int[] array) {
    len = array.length;
    if (len < 1) return array;
    //1.构建一个最大堆
    buildMaxHeap(array);
    //2.循环将堆首位（最大值）与末位交换，然后在重新调整最大堆
    while (len > 0) {
        swap(array, 0, len - 1);
        len--;
        adjustHeap(array, 0);
    }
    return array;
}
public static void buildMaxHeap(int[] array) {
    //从最后一个非叶子节点开始向上构造最大堆
    //for循环这样写会更好一点：i的左子树和右子树分别2i+1和2(i+1)
    for (int i = (len/2- 1); i >= 0; i--) {
        adjustHeap(array, i);
    }
}
public static void adjustHeap(int[] array, int i) {
    int maxIndex = i;
    //如果有左子树，且左子树大于父节点，则将最大指针指向左子树
    if (i * 2 < len && array[i * 2] > array[maxIndex])
        maxIndex = i * 2 + 1;
    //如果有右子树，且右子树大于父节点，则将最大指针指向右子树
    if (i * 2 + 1 < len && array[i * 2 + 1] > array[maxIndex])
        maxIndex = i * 2 + 2;
    //如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。
    if (maxIndex != i) {
        swap(array, maxIndex, i);
        adjustHeap(array, maxIndex);
    }
}
```

##### 2.8 计数排序（Counting Sort）

将输入的数据值转化为键存储在额外开辟的数组空间中。 作为一种线性时间复杂度的排序，计数排序要求输入的数据必须是有确定范围的整数。

计数排序使用一个额外的数组C，其中第i个元素是待排序数组A中值等于i的元素的个数。然后根据数组C来将A中的元素排到正确的位置。它只能对整数进行排序。

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMzE3NDA4NDAtNjk2ODE4MS5naWY)

```java
public static int[] CountingSort(int[] array) {
        if (array.length == 0) return array;
        int bias, min = array[0], max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max)
                max = array[i];
            if (array[i] < min)
                min = array[i];
        }
        bias = 0 - min;
        int[] bucket = new int[max - min + 1];
        Arrays.fill(bucket, 0);
        for (int i = 0; i < array.length; i++) {
            bucket[array[i] + bias]++;
        }
        int index = 0, i = 0;
        while (index < array.length) {
            if (bucket[i] != 0) {
                array[index] = i - bias;
                bucket[i]--;
                index++;
            } else
                i++;
        }
        return array;
    }
```

##### 2.9 桶排序（Bucket Sort）

是计数排序的升级版。它利用了函数的映射关系，高效与否的关键就在于这个映射函数的确定

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMzIxMDcwOTAtMTkyMDcwMjAxMS5wbmc?x-oss-process=image/format,png)

```java
public static ArrayList<Integer> BucketSort(ArrayList<Integer> array, int bucketSize) {
    if (array == null || array.size() < 2)
        return array;
    int max = array.get(0), min = array.get(0);
    // 找到最大值最小值
    for (int i = 0; i < array.size(); i++) {
        if (array.get(i) > max)
            max = array.get(i);
        if (array.get(i) < min)
            min = array.get(i);
    }
    int bucketCount = (max - min) / bucketSize + 1;
    ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketCount);
    ArrayList<Integer> resultArr = new ArrayList<>();
    for (int i = 0; i < bucketCount; i++) {
        bucketArr.add(new ArrayList<Integer>());
    }
    for (int i = 0; i < array.size(); i++) {
        bucketArr.get((array.get(i) - min) / bucketSize).add(array.get(i));
    }
    for (int i = 0; i < bucketCount; i++) {
        if (bucketSize == 1) { // 如果带排序数组中有重复数字时
            for (int j = 0; j < bucketArr.get(i).size(); j++)
                resultArr.add(bucketArr.get(i).get(j));
        } else {
            if (bucketCount == 1)
                bucketSize--;
            ArrayList<Integer> temp = BucketSort(bucketArr.get(i), bucketSize);
            for (int j = 0; j < temp.size(); j++)
                resultArr.add(temp.get(j));
        }
    }
    return resultArr;
}
```

##### 2.10 基数排序（Radix Sort）

 **基数排序**也是非比较的排序算法，对每一位进行排序，从最低位开始排序，复杂度为O(kn),为数组长度，k为数组中的数的最大的位数；

![image](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODQ5NTg5LzIwMTcxMC84NDk1ODktMjAxNzEwMTUyMzI0NTM2NjgtMTM5NzY2MjUyNy5naWY)

```java
public static int[] RadixSort(int[] array) {
    if (array == null || array.length < 2)
        return array;
    // 1.先算出最大数的位数；
    int max = array[0];
    for (int i = 1; i < array.length; i++) {
        max = Math.max(max, array[i]);
    }
    int maxDigit = 0;
    while (max != 0) {
        max /= 10;
        maxDigit++;
    }
    int mod = 10, div = 1;
    ArrayList<ArrayList<Integer>> bucketList = new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < 10; i++)
        bucketList.add(new ArrayList<Integer>());
    for (int i = 0; i < maxDigit; i++, mod *= 10, div *= 10) {
        for (int j = 0; j < array.length; j++) {
            int num = (array[j] % mod) / div;
            bucketList.get(num).add(array[j]);
        }
        int index = 0;
        for (int j = 0; j < bucketList.size(); j++) {
            for (int k = 0; k < bucketList.get(j).size(); k++)
                array[index++] = bucketList.get(j).get(k);
            bucketList.get(j).clear();
        }
    }
    return array;
}
```

