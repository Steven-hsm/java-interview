package com.hsm.sort;

import java.util.Arrays;

/**
 * @Classname SortTestMain
 * @Description 排序测试
 * @Date 2021/4/19 20:22
 * @Created by huangsm
 */
public class SortTestMain {

    public static void main(String[] args) {
        int[] arr = null;
        //冒泡排序
        arr = new int[]{10,9,1,8,5,2,3,4,6,7};
        Sort.bubbleSort(arr);

        //选择排序
        arr = new int[]{10,9,1,8,5,2,3,4,6,7};
        Sort.selectionSort(arr);

        //插入排序
        arr = new int[]{10,9,1,8,5,2,3,4,6,7};
        Sort.insertionSort(arr);

        for (int i : arr) {
            System.out.print(i);
        }


    }
}
