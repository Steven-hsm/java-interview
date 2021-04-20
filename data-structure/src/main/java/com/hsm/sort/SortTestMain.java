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
        arr = new int[]{10, 9, 1, 8, 5, 2, 3, 4, 6, 7};
        Sort.bubbleSort(arr);

        //选择排序
        arr = new int[]{10, 9, 1, 8, 5, 2, 3, 4, 6, 7};
        Sort.selectionSort(arr);

        //插入排序
        arr = new int[]{10, 9, 1, 8, 5, 2, 3, 4, 6, 7};
        Sort.insertionSort(arr);

        //希尔排序
        arr = new int[]{10, 9, 1, 8, 5, 2, 3, 4, 6, 7};
        Sort.shellSort(arr);

        //归并
        arr = new int[]{10, 9, 1, 8, 5, 2, 3, 4, 6, 7};
        Sort.mergeSort(arr);

        //快速排序
        arr = new int[]{10, 9, 1, 8, 5, 2, 3, 4, 6, 7};
        Sort.quickSort(arr);

        //堆排序
        arr = new int[]{10, 9, 1, 8, 5, 2, 3, 4, 6, 7};
        Sort.heapSort(arr);

        //基数排序
        arr = new int[]{10, 9, 1, 8, 5, 2, 3, 4, 6, 7};
        Sort.radixSort(arr);




        for (int j : arr) {
            System.out.print(j + "\t");
        }


    }
}
