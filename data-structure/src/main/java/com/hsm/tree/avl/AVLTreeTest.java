package com.hsm.tree.avl;

import com.hsm.tree.sorttree.SortTree;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/22
 */
public class AVLTreeTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 4, 6, 8, 10, 2, 3);
        AVLTree avlTree = new AVLTree();
        list.forEach(data -> avlTree.insert(data));

        //先序遍历会将排序二叉树顺序打印
        avlTree.preOrder(avlTree.getRoot());
    }

}
