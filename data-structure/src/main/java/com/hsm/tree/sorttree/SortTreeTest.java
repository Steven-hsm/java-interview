package com.hsm.tree.sorttree;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/22
 */
public class SortTreeTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 4, 6, 8, 10, 2, 3);
        SortTree sortTree = new SortTree();
        list.forEach(data -> sortTree.insert(data));

        //先序遍历会将排序二叉树顺序打印
        sortTree.preOrder(sortTree.getRoot());
    }
}
