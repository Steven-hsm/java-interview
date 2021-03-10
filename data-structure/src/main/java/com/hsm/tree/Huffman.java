package com.hsm.tree;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @Classname HuffmanNode
 * @Description TODO
 * @Date 2021/3/10 16:33
 * @Created by senming.huang
 */
public class Huffman{
    private HuffmanNode mRoot;  // 根结点

    public Huffman(List<Integer> dataList) {
        while(dataList.size() > 1){
            dataList.stream().min(Comparator.comparing(Function.identity())).get();
        }

    }
}

class HuffmanNode implements Comparable{
    private int data;//权值
    private HuffmanNode leftChild;//左孩子
    private HuffmanNode rightChild;//右孩子
    private HuffmanNode parent;//父结点

    public HuffmanNode(int data, HuffmanNode leftChild, HuffmanNode rightChild, HuffmanNode parent) {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.parent = parent;
    }

    @Override
    public int compareTo(Object obj) {
        return this.data - ((HuffmanNode)obj).data;
    }


}
