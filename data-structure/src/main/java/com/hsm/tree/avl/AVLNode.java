package com.hsm.tree.avl;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/22
 */
public class AVLNode {
    public int data;//保存节点数据
    public int depth;//保存节点深度
    public int balance;//是否平衡
    public AVLNode parent;//指向父节点
    public AVLNode left;//指向左子树
    public AVLNode right;//指向右子树

    public AVLNode(int data){
        this.data = data;
        depth = 1;
        balance = 0;
        left = null;
        right = null;
    }
}
