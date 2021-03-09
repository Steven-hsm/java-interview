package com.hsm.tree;

/**
 * @Classname BinaryTreeSearch
 * @Description 二叉树遍历
 * @Date 2021/3/8 14:14
 * @Created by senming.huang
 */
public class BinaryTreeSearch {
    public static void main(String[] args) {
        BinaryTreeSearch binaryTreeSearch = new BinaryTreeSearch();
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        binaryTreeSearch.preOrder(arr, 0);
        System.out.println();
        binaryTreeSearch.infixOrder(arr, 0);
        System.out.println();
        binaryTreeSearch.postOrder(arr, 0);

    }

    /**
     * 前序遍历数组
     *
     * @param arr
     * @param index
     */
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

    /**
     * 功能描述:中序遍历
     *
     * @auther: senming.huang
     * @date: 2021/3/8 14:23
     */
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


    /**
     * 功能描述:后序遍历
     *
     * @auther: senming.huang
     * @date: 2021/3/8 14:23
     */
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

    /**
     * 功能描述:中序遍历
     *
     * @auther: senming.huang
     * @date: 2021/3/8 14:23
     */
    public void levelOrder(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }
}
